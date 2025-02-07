$(document).ready(function () {
    // OpenLayers 지도 초기화
    const map = new ol.Map({
        target: 'map',
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM()
            })
        ],
        view: new ol.View({
            center: ol.proj.fromLonLat([126.9784, 37.5665]), // 서울 좌표
            zoom: 10
        })
    });

    // 할 일 추가 기능
    $('#addTask').on('click', function () {
        const task = $('#taskInput').val().trim();
        const startTime = $('#startTime').val();
        const endTime = $('#endTime').val();
        const location = $('#location').val().trim();

        if (task && startTime && endTime && location) {
            const row = `
                <tr>
                    <td><input type="checkbox" class="task-checkbox"></td>
                    <td>${task}</td>
                    <td>${startTime}</td>
                    <td>${endTime}</td>
                    <td>${location}</td>
                </tr>
            `;

            $('#taskTable tbody').append(row);

            // 입력 필드 초기화
            $('#taskInput').val('');
            $('#startTime').val('');
            $('#endTime').val('');
            $('#location').val('');
        }
    });

    // 체크박스 체크 시 취소선 적용
    $('#taskTable').on('change', '.task-checkbox', function () {
        $(this).closest('tr').toggleClass('completed');
    });
});
