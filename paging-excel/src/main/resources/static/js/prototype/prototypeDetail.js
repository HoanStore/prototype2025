const ex = (function () {

    const popupInit = function () {
        const _data = {
            message1 : "현재 선택한 수출사례를",
            message2 : "삭제하시겠습니까?"
        };

        $(".btn_gray").click(function(){
            exprt.popup(_data);
            $(".pop, pop_check").show();
            $(".btn_close").click(function(){
                $(".pop, pop_check").hide();
            });
            deleteEx();
        });
    }

    const modifyEx = function () {
        $("#btn_modify").click(function () {
            debugger;

            const bno = $(".view_box").data("bno");
            window.location.href = '/modify/'+bno;
        })
    }

    const deleteEx = function () {
        $(".pop .pop_box .btn").click(function () {
            const id = $(".view_box").data("bno");
            const attflId = $(".view_box").data("attflid");

            fetch(`/prototype1-1/${id}?attflId=${attflId}`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        window.location.href = '/';
                    } else {
                        console.error('Delete request failed');
                    }
                })
                .catch(error => console.error('Error:', error));
        })
    }

    const toExList = function () {
        $("#btn_list").click(function () {

            window.location.href = '/prototype1-1';
        });
    }

    return {
        popupInit : popupInit,
        modify : modifyEx,
        delete : deleteEx,
        toList : toExList
    }
})();

$(document).ready(function () {
    ex.popupInit();
    ex.modify();
    ex.delete();
    ex.toList();
});
