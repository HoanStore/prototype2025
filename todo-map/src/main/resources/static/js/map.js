let map, markerLayer;

$(document).on('click', '#taskTable tbody tr', function() {
    const cells = $(this).find('td');
    const rowData = {
        task: $(cells[1]).text(),
        startTime: $(cells[2]).text(),
        endTime: $(cells[3]).text(),
        location: $(cells[4]).text(),
        lon: $(cells[5]).text(),
        lat: $(cells[6]).text()
    };

    addMarkerAndFocus(rowData);
});

$(document).ready(function () {

    initMap();

    // 할 일 추가 기능
    $('#addTask').on('click', function () {
        const task = $('#taskInput').val().trim();
        const startTime = $('#startTime').val();
        const endTime = $('#endTime').val();
        const location = $('#searchBox').val().trim();
        const lon = $("#lon").val().trim();
        const lat = $("#lat").val().trim();


        if (task && startTime && endTime && location) {
            const row = `
                <tr>
                    <td><input type="checkbox" class="task-checkbox"></td>
                    <td>${task}</td>
                    <td>${startTime}</td>
                    <td>${endTime}</td>
                    <td>${location}</td>
                    <td>${lon}</td>
                    <td>${lat}</td>
                </tr>
            `;

            $('#taskTable tbody').append(row);

            // 입력 필드 초기화
            $('#taskInput').val('');
            $('#startTime').val('');
            $('#endTime').val('');
            $('#searchBox').val('');
            $('#lon').val('');
            $('#lat').val('');
        }
    });

    // 체크박스 체크 시 취소선 적용
    $('#taskTable').on('change', '.task-checkbox', function () {
        $(this).closest('tr').toggleClass('completed');
    });
});

function addMarkerAndFocus (rowData) {
    addMarker(rowData.lon, rowData.lat);
    focusMarker(rowData.lon, rowData.lat);
}

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

function initMap() {
    map = new ol.Map({
        target: 'map',
        layers: [new ol.layer.Tile({ source: new ol.source.OSM() })],
        view: new ol.View({ center: ol.proj.fromLonLat([126.9784, 37.5665]), zoom: 12 })
    });

    markerLayer = new ol.layer.Vector({ source: new ol.source.Vector() });
    map.addLayer(markerLayer);

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

            document.getElementById('lon').value = loc.lng();
            document.getElementById('lat').value = loc.lat();

            addMarker(loc.lng(), loc.lat());
            focusMarker(loc.lng(), loc.lat());
        });
    }

    window.onload = initAutocomplete;
}

function focusMarker(lon, lat) {
    map.getView().setCenter(ol.proj.fromLonLat([lon, lat]));
    map.getView().setZoom(15);
}