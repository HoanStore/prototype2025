$(document).ready(function() {
    $('#favoriteBtn').on('click', function () {
        $('#favoriteMenu').toggleClass('hidden');
    });

    // 햄버거 메뉴 버튼 클릭 시
    $("#toggleMenu").click(function() {
        $("#total-menu").toggle(); // 메뉴의 표시 여부를 토글
    });

    // X 버튼 클릭 시
    $("#closeMenu").click(function() {
        $("#total-menu").hide(); // 메뉴 숨기기
    });



    getHeaderMenuCode(0);
});


function getHeaderMenuCode(_idx) {
    return $.ajax({
        url: '/cmm/getMenuCodeByDB',
        method: 'POST',
        data: JSON.stringify({language: "kr"}),
        contentType: 'application/json',
        success: function (_res) {

            const menuTree = buildMenuTree(_res);
            drawAllMenu(menuTree);
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

function drawAllMenu(menuTree) {
    let html = '';

    menuTree.forEach(bigMenus => {
        html += '<ul class="main-menu">';
        html += '<li class="mainmenu-item">';
        html += `<a href="#">${bigMenus.menuName}</a>`

        bigMenus.children.forEach(middleMenus => {
            html += '<ul class="sub-menu">';
            html += `<li class="submenu-item">`;
            html += `<a href="#">${middleMenus.menuName}</a>`;

            middleMenus.children.forEach(smallMenus => {
                html += '<ul class="small-menu">';
                html += '<li class="smallmenu-item">';
                html += `<a href="#">${smallMenus.menuName}</a>`

                html += '<ul class="detail-menu">';
                smallMenus.children.forEach(realMenus => {
                    html += `<li><a href=${realMenus.url}>-${realMenus.menuName}</a></li>`
                })
                html += '</ul>';
                html += '</ul>';
            })
            html += '</li>';
            html += '</ul>';
        })

        html += '</li>';
        html += '</ul>';
    })

    $("#menu-map").html(html);
}


function buildMenuTree(data) {
    const map = new Map();
    const root = [];

    // 모든 항목을 Map에 저장하고 children 속성 추가
    data.forEach(item => {
        map.set(item.menuId, { ...item, children: [] });
    });

    // 부모-자식 관계 설정
    data.forEach(item => {
        if (item.parentId === null) {
            root.push(map.get(item.menuId)); // 최상위 항목이면 루트에 추가
        } else {
            const parent = map.get(item.parentId);
            if (parent) {
                parent.children.push(map.get(item.menuId)); // 부모의 children 배열에 추가
            }
        }
    });

    return root;
}
