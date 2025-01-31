$(document).ready(function() {
    findAll();
    initPage();

    function initPage() {
        findAll();
        setSearchEvent();
    }


    function findAll() {
        $('#searchMessage').hide();

        $.get("/findAll", function(boardList) {
            updateBoardTable(boardList);
        });
    }

    function findByTitle(title) {
        $('#searchMessage').hide();

        $.get("/findByTitle", { title: title }, function(response) {
            showOriginAndCorrectKeyWord(response.searchDTO);
            updateBoardTable(response.boardList);
        });
    }

    function findStrict(originalTitle) {
        $('#searchMessage').hide();

        $.get("/findStrict", { title: originalTitle }, function(boardList) {
            updateBoardTable(boardList);
        });
    }

    function setSearchEvent() {
        $("#searchForm").submit(function(event) {
            event.preventDefault();
            const title = $("#searchTitle").val().trim();

            if(title === "") {
                findAll();
            } else {
                findByTitle(title);
            }
        });
    }

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

    function showOriginAndCorrectKeyWord(searchDTO) {
        const correctedTitle = searchDTO.correctedTitle;
        const originalTitle = searchDTO.originalTitle;

        if (correctedTitle !== "") {
            $('#correctTitle').text(correctedTitle);
            $('#originTitle').text(`${originalTitle} 검색결과 보기`).data('original-title', originalTitle);

            $('#originTitle').on('click', function() {
                const originalTitle = $(this).data('original-title');
                findStrict(originalTitle);
            });

            $('#searchMessage').show();
        }
    }



});
