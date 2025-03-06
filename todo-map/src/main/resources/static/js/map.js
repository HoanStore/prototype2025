const todos = [];
const baseURL = "https://graphhopper.com/api/1/route";
const apiKey = "ec535555-da4a-41a7-b2d4-ba935453a7e1";

let map, markerLayer;

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


    $('#taskTable').on('change', '.task-checkbox', function () {
        const checkbox = $(this);
        const row = checkbox.closest('tr');
        const rowId = row.data('id');
        const taskLon = parseFloat(row.find('td').eq(5).text());
        const taskLat = parseFloat(row.find('td').eq(6).text());

        if ("geolocation" in navigator) {
            navigator.geolocation.getCurrentPosition(function (position) {
                const userLon = position.coords.longitude;
                const userLat = position.coords.latitude;

                const distance = getDistanceFromLatLonInMeters(userLat, userLon, taskLat, taskLon);
                console.log("distacne : "+distance);

                if (distance <= 500) {
                    row.toggleClass('completed double-strike').removeClass('single-strike');
                } else {
                    row.toggleClass('completed single-strike').removeClass('double-strike');
                }
                changeMarkerColor(rowId, row.hasClass('completed') ? 'blue' : 'red');


            }, function (error) {
                console.error("위치 정보를 가져올 수 없습니다:", error);
                alert('위치 정보를 가져올 수 없습니다.');
                checkbox.prop('checked', false);
            });
        } else {
            alert('Geolocation을 지원하지 않는 브라우저입니다.');
            checkbox.prop('checked', false);
        }
    });


    // 두 좌표 간 거리 계산 함수 (Haversine formula)
    function getDistanceFromLatLonInMeters(lat1, lon1, lat2, lon2) {
        const R = 6371000; // 지구 반지름(m)
        const dLat = deg2rad(lat2 - lat1);
        const dLon = deg2rad(lon2 - lon1);
        const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2);
        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // 거리(m)
    }

    function deg2rad(deg) {
        return deg * (Math.PI / 180);
    }
});


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


function addMarkerAndFocus (rowData) {
    addTodo(rowData);
    removeTempMarkers();

    addMarker(rowData, rowData.id);
    addToolTip(rowData);
    focusMarker(rowData);

    drawLine();
}

function addTodo(rowData) {
    todos.push(rowData);
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
    markerLayer.setZIndex(10);

    map.addLayer(markerLayer);


    // OpenLayers 기본 줌 컨트롤 내부에 버튼 추가
    const zoomControl = $('.ol-zoom'); // 기본 줌 컨트롤 찾기
    const locateBtn = $('<button class="ol-locate" type="button" title="현재 위치로 이동">📍</button>');

    // 스타일 적용 (기본 줌 버튼과 유사하게 만들기)
    locateBtn.css({
        display: 'block',
        width: '22px',
        height: '22px',
        background: '#fff',
        border: '1px solid rgba(0,0,0,0.2)',
        cursor: 'pointer',
        fontSize: '16px',
        textAlign: 'center',
        lineHeight: '24px',
    });

    // 버튼을 기본 줌 컨트롤 내부에 추가
    zoomControl.append(locateBtn);


    // 버튼 클릭 시 현재 위치 이동
    locateBtn.on('click', function () {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                function (position) {
                    const lon = position.coords.longitude;
                    const lat = position.coords.latitude;
                    map.getView().animate({
                        center: ol.proj.fromLonLat([lon, lat]),
                        zoom: 15,
                        duration: 1000,
                    });
                },
                function (error) {
                    console.error('Geolocation error:', error);
                    alert('위치를 가져올 수 없습니다.');
                }
            );
        } else {
            alert('Geolocation이 지원되지 않습니다.');
        }
    });


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

function drawLine() {
    if(todos.length < 2) {
        return;
    }
    const todosSize = todos.length;
    const url = getGraphHopperRouteURL([todos[todosSize-1].lon, todos[todosSize-1].lat], [todos[todosSize-2].lon, todos[todosSize-2].lat]);

    fetch(url)
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
                    color: sizeToColor(todosSize),
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



function getGraphHopperRouteURL(start, end) {
    return `${baseURL}?point=${start[1]},${start[0]}&point=${end[1]},${end[0]}&profile=car&locale=ko&points_encoded=false&key=${apiKey}`;
}

function sizeToColor(size) {
    const modSize = size % 4; // 2, 3, 4, 5 값이 반복되도록 설정
    switch(modSize) {
        case 2:
            return 'rgba(70, 130, 180, 1)';
        case 3:
            return 'rgba(100, 149, 237, 1)'; // 조금 짙은 하늘색
        case 0:
            return 'rgba(70, 130, 180, 1)'; // 짙은 하늘색
        default:
            return 'rgba(70, 130, 180, 0.8)'; // 투명도 적용된 짙은 하늘색
    }
}
