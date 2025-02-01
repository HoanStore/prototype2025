const stat = (function () {

    const popupInit = function () {
        const _data = {
            message1 : "현재 선택한 수출통계 및 현황을",
            message2 : "삭제하시겠습니까?"
        };

        $(".btn_gray").click(function(){
            exprt.popup(_data);
            $(".pop, pop_check").show();
            $(".btn_close").click(function(){
                $(".pop, pop_check").hide();
            });
            deleteStat();
        });
    }

    const modifyStat = function () {
        $("#btn_modify").click(function () {
            const bno = $(".view_box").data("bno");
            window.location.href = '/admin/export/statistics/modify/'+bno;
        })
    }

    const deleteStat = function () {
        $(".pop .pop_box .btn").click(function () {
            const bno = $(".view_box").data("bno");

            common.ajaxRequest({
                url: '/export/statistics/'+bno,
                type: 'delete',
                callBack: function (_res) {
                    window.location.href = '/admin/export/statistics';
                }
            });
        })
    }

    const toStatList = function () {
        $("#btn_list").click(function () {
            window.location.href = '/admin/export/statistics';
        });
    }

    return {
        popupInit : popupInit,
        modify : modifyStat,
        delete : deleteStat,
        toList : toStatList
    }
})();

$(document).ready(function () {
    stat.popupInit();
    stat.modify();
    stat.delete();
    stat.toList();
});
