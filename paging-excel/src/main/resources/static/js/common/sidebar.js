const menuId = {
    "" : 5,
    "/" : 5,
    "export": 0,
    "event": 1,
    "promotion": 2,
    "education": 3,
    "communication": 4,
    "homepage": 5,
    "auth": 6,
    "online-promotion": 7
}

$(document).ready(function() {
    getMenuCode(0);
});

// function setMenuHighLight() {
//     const paths = window.location.pathname.split("/admin");
//     const depth1 = "/"+paths[1];
//     const depth2 = "/"+paths[1]+"/"+paths[2];
//
//     if(depth1 === "/") {
//         $(`#tab_box a:first`).addClass('on');
//         $('#sidebar a:first').addClass('on');
//         return;
//     }
//
//     if(paths.length < 3) {
//         $(`#tab_box a[href="${depth1}"]`).addClass('on');
//         $('#sidebar a:first').addClass('on');
//         return;
//     }
//
//     $(`#tab_box a[href="${depth1}"]`).addClass('on');
//     $(`#sidebar a[href="${depth2}"]`).addClass('on');
// }

function getMenuCode(_idx) {

    return $.ajax({
        url: '/cmm/getMenuCode',
        method: 'POST',
        data: JSON.stringify({language: "kr"}),
        contentType: 'application/json',
        success: function (_res) {
            const menuList = [];
            _res.forEach(function (arr) {
                menuList.push(arr.menu);
            });
            drawsidebar(menuList);
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });

}

function drawsidebar(menuList) {
    console.log(menuList);
    const paths = window.location.pathname;
    const currentMenu = menuList.find(menu => paths.startsWith(menu.pathPrefix)) || menuList[5];
    let html = '<ul>';

    html += `<li class="tit"><strong>${currentMenu.menuTitle}</strong></li>`;
    currentMenu.children.forEach(menu => {
        html += `<li>
                    <p>
                        <a class="${paths.startsWith(menu.url) ? 'on' : ''}" href=${menu.url} >${menu.menuTitle}</a>
                    </p>
                </li>`;
    })
    html += '</ul>';
    $("#sidebar").html(html);
    $("#header-gnb .tab a").removeClass("on")
    $(`#header-gnb .tab a`).eq(currentMenu.menuIdx).addClass('on');
}


