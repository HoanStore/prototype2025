let map, markerLayer;

$(document).ready(function () {

    initMap(drawLine);

});

function drawLine() {
    const start = [127.0276, 37.4979];  // 출발지 (예: 강남역)
    const end = [126.9784, 37.5665];    // 도착지 (예: 서울역)

// GraphHopper API 호출
    fetch(`https://graphhopper.com/api/1/route?point=${start[1]},${start[0]}&point=${end[1]},${end[0]}&profile=car&locale=ko&points_encoded=false&key=ec535555-da4a-41a7-b2d4-ba935453a7e1`)
        .then(response => response.json())
        .then(data => {
            const coordinates = data.paths[0].points.coordinates.map(coord => ol.proj.fromLonLat([coord[0], coord[1]]));

            // 실제 도로를 따라 선(LineString) 생성
            const route = new ol.geom.LineString(coordinates);

            const routeFeature = new ol.Feature({
                geometry: route,
            });

            routeFeature.setStyle(new ol.style.Style({
                stroke: new ol.style.Stroke({
                    color: 'blue',
                    width: 4
                })
            }));

            const vectorSource = new ol.source.Vector({
                features: [routeFeature]
            });

            const vectorLayer = new ol.layer.Vector({
                source: vectorSource
            });

            map.addLayer(vectorLayer);
        });

}

function initMap(drawLine) {
    map = new ol.Map({
        target: 'map',
        layers: [new ol.layer.Tile({ source: new ol.source.OSM() })],
        view: new ol.View({ center: ol.proj.fromLonLat([126.9784, 37.5665]), zoom: 12 }) // 서울 중심
    });

    let markerLayer = new ol.layer.Vector({ source: new ol.source.Vector() });
    map.addLayer(markerLayer);

    function addMarker(lon, lat) {
        markerLayer.getSource().clear();
        let feature = new ol.Feature({
            geometry: new ol.geom.Point(ol.proj.fromLonLat([lon, lat]))
        });
        feature.setStyle(new ol.style.Style({
            image: new ol.style.Icon({ src: 'https://maps.google.com/mapfiles/ms/icons/red-dot.png' })
        }));
        markerLayer.getSource().addFeature(feature);
    }

    function initAutocomplete() {
        let input = document.getElementById('searchBox');
        let autocomplete = new google.maps.places.Autocomplete(input);

        autocomplete.addListener('place_changed', function () {
            let place = autocomplete.getPlace();
            if (!place.geometry) {
                alert("장소를 찾을 수 없습니다.");
                return;
            }
            let loc = place.geometry.location;
            addMarker(loc.lng(), loc.lat());
            map.getView().setCenter(ol.proj.fromLonLat([loc.lng(), loc.lat()]));
            map.getView().setZoom(15);
        });
    }

    window.onload = initAutocomplete;

    drawLine();
}