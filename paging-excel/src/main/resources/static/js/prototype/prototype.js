const stats = (function () {
    const getStatistics = function(_pageNo = 1){
        const _searchType = $("select[name='keyfield']").val();
        const _pageSize = $('#perPageNum').val();
        let _keyword = '';



        if(_searchType === 'its_system'){
            _keyword = $("#its_system").val();
        }else {
            _keyword = $("#keywordInput").val();
        }

        $.ajax({
            url: '/getPrototypeList',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({curPage: _pageNo, pageSize: _pageSize, searchType: _searchType, keyword : _keyword}),
            success: function(_res) {

                $("#perPageNum option:first").val(_res.page.listCnt);
                $(".tb_txt strong").text(_res.page.listCnt);

                exprt.drawList(_res.data, 'statistics_template');
                exprt.drawPagination(_res.page, 'stats.getStatistics');            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });
    }

    const changePageSize = function () {
        $('#perPageNum').on('change', function(){
            getStatistics();
        });
    }


    const downloadExcel = function () {
        $(".btn_excel_download").click(function () {
            location.href = `/getPrototypeList/excel`;
        });
    }

    const onclickUploaBtn = function () {
        $(".btn_excel_upload").click(function () {
            $("#excelFileUpload").click();
        })

    }

    const changeUploadExcel = function () {
        $("#excelFileUpload").change(function() {
            uploadExcel();
        });
    }

    const uploadExcel = function () {

        const formData = new FormData($('#form')[0]);

        common.ajaxFormRequest({
            type: 'post',
            url: '/export/statistics/uploadExcel',
            data: formData,
            processData: false,
            contentType: false,
            dataType: 'json',
            callBack: function (_res) {
                console.log(_res)
                if (_res.isSuccess) {
                    alert("엑셀 업로드를 완료하였습니다.");
                    window.location.href = '/admin/export/statistics';
                } else {
                    alert("엑셀 업로드중 에러가 발생하였습니다.");
                }
            }
        });
    }

    return {
        getStatistics : getStatistics,
        changePageSize : changePageSize,
        downloadExcel : downloadExcel,
        onclickUploaBtn : onclickUploaBtn,
        changeUploadExcel : changeUploadExcel
    }
})();

$(document).ready(function () {
    exprt.keyPressListener(stats.getStatistics);
    exprt.initializeSelectBox();
    exprt.getRegisterPage("/admin/export/statistics/register");
    stats.getStatistics();
    stats.changePageSize();
    stats.downloadExcel();
    stats.changeUploadExcel();
    stats.onclickUploaBtn();
});

