$(document).ready(function() {
    $.get("/findAll", function(boardList) {
        updateBoardTable(boardList);
    });

    // 검색 폼 제출 시 제목으로 검색
    $("#searchForm").submit(function(event) {
        event.preventDefault();  // 폼 제출 방지

        const title = $("#searchTitle").val();
        $.get("/findByTitle", { title: title }, function(boardList) {
            updateBoardTable(boardList);
        });
    });

    // 테이블 업데이트 함수
    function updateBoardTable(boardList) {
        let tableBody = $("#boardTableBody");
        tableBody.empty();  // 기존 내용 지우기
        boardList.forEach(function(board) {
            tableBody.append(`
                <tr>
                    <td>${board.id}</td>
                    <td>${board.title}</td>
                    <td>${board.content}</td>
                    <td>${board.createdAt}</td>
                </tr>
            `);
        });
    }
});
