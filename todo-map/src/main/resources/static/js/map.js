let map, markerLayer;

$(document).on('click', '#taskTable tbody tr', function() {
    const cells = $(this).find('td');
    const rowData = {
        id: Date.now(),
        task: $(cells[1]).text(),
        startTime: $(cells[2]).text(),
        endTime: $(cells[3]).text(),
        location: $(cells[4]).text(),
        lon: $(cells[5]).text(),
        lat: $(cells[6]).text()
    };

    focusMarker(rowData);
});

$(document).ready(function () {

    initMap();

    // 할 일 추가 기능
    $('#addTask').on('click', function () {
        const rowData = {
            id: Date.now(),
            task: $('#taskInput').val().trim(),
            startTime: $('#startTime').val(),
            endTime: $('#endTime').val(),
            location: $('#searchBox').val().trim(),
            lon: $("#lon").val().trim(),
            lat: $("#lat").val().trim()
        };

        addMarkerAndFocus(rowData);

        if (rowData.task && rowData.startTime && rowData.endTime && rowData.location) {
            const row = `
                <tr data-id="${rowData.id}">
                    <td><input type="checkbox" class="task-checkbox"></td>
                    <td>${rowData.task}</td>
                    <td>${rowData.startTime}</td>
                    <td>${rowData.endTime}</td>
                    <td>${rowData.location}</td>
                    <td>${rowData.lon}</td>
                    <td>${rowData.lat}</td>
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
        const row = $(this).closest('tr');
        const rowId = row.data('id');
        const markerColor = row.hasClass('completed') ? 'red' : 'blue';

        $(this).closest('tr').toggleClass('completed');
        changeMarkerColor(rowId, markerColor);
    });
});


function addMarkerAndFocus (rowData) {
    removeTempMarkers();

    addMarker(rowData, rowData.id);
    addToolTip(rowData);
    focusMarker(rowData);
}

function addMarker(rowData, id="temp") {

    let feature = new ol.Feature({
        geometry: new ol.geom.Point(ol.proj.fromLonLat([rowData.lon, rowData.lat])),
        id: id
    });
    feature.setStyle(new ol.style.Style({
        image: new ol.style.Icon({ src: 'https://maps.google.com/mapfiles/ms/icons/red-dot.png' })
    }));
    markerLayer.getSource().addFeature(feature);
}

function removeTempMarkers() {
    let features = markerLayer.getSource().getFeatures();
    features.forEach(feature => {
        if (feature.get('id') === 'temp') {
            markerLayer.getSource().removeFeature(feature);
        }
    });
}


function changeMarkerColor(id, color) {
    let features = markerLayer.getSource().getFeatures();
    const newMarkerColor = `https://maps.google.com/mapfiles/ms/icons/${color}-dot.png`;
    console.log(newMarkerColor);

    features.forEach(myFeature => {
        console.log(myFeature.get('id'), id);

        if (myFeature.get('id') === id) {
            myFeature.setStyle(new ol.style.Style({
                image: new ol.style.Icon({
                    src: newMarkerColor
                })
            }));
        }
    });
}

function addToolTip(rowData) {
    // 기존 툴팁 제거
    // if (window.tooltipOverlay) {
    //     map.removeOverlay(window.tooltipOverlay);
    // }

    // 툴팁 생성
    let tooltip = document.createElement("div");
    tooltip.className = "tooltip";
    tooltip.innerHTML = `Task: ${rowData.task} <br>Time: ${rowData.startTime} ~ ${rowData.endTime}`;
    tooltip.style.position = "absolute";
    tooltip.style.background = "white";
    tooltip.style.padding = "5px 10px"; // 좌우 여백 추가
    tooltip.style.border = "1px solid black";
    tooltip.style.borderRadius = "4px";
    tooltip.style.fontSize = "12px";
    tooltip.style.display = "block"; // 항상 표시됨
    tooltip.style.whiteSpace = "nowrap"; // 한 줄로 유지

    let overlay = new ol.Overlay({
        element: tooltip,
        positioning: "bottom-center",
        offset: [5, -55],
        position: ol.proj.fromLonLat([rowData.lon, rowData.lat]) // 마커 위치에 배치
    });

    map.addOverlay(overlay);
    window.tooltipOverlay = overlay;
}


function initMap() {
    map = new ol.Map({
        target: 'map',
        layers: [new ol.layer.Tile({ source: new ol.source.OSM() })],
        view: new ol.View({ center: ol.proj.fromLonLat([126.9784, 37.5665]), zoom: 12 }) // 기본 중심: 서울
    });

    markerLayer = new ol.layer.Vector({ source: new ol.source.Vector() });
    map.addLayer(markerLayer);

    // 현재 위치 가져오기
    if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition(
            function (position) {
                const lon = position.coords.longitude;
                const lat = position.coords.latitude;
                const userLocation = { lon, lat };

                // 지도 중심 이동 및 마커 추가
                focusMarker(userLocation);
                addMarker(userLocation);
            },
            function (error) {
                console.error("위치 정보를 가져올 수 없습니다:", error);
            }
        );
    } else {
        console.error("Geolocation을 지원하지 않는 브라우저입니다.");
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

            document.getElementById('lon').value = loc.lng();
            document.getElementById('lat').value = loc.lat();

            const rowData = {
                lon: loc.lng(),
                lat: loc.lat()
            };

            addMarker(rowData);
            focusMarker(rowData);
        });
    }

    window.onload = initAutocomplete;
}


function focusMarker(rowData) {
    map.getView().setCenter(ol.proj.fromLonLat([rowData.lon, rowData.lat]));
    map.getView().setZoom(15);
}