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
            url: '/getPrototypeVer2List',
            method: 'POST',
            data: {
                page: _pageNo - 1,
                size: _pageSize,
                searchType: _searchType,
                keyword: _keyword
            },
            success: function(_res) {

                $("#perPageNum option:first").val(_res.totalElements);
                $(".tb_txt strong").text(_res.totalElements);

                exprt.drawList(_res.content, 'statistics_template');
                /**
                 * [TODO] 1 2 3 4 5 -> 페이지 번호 작업 해야 함
                 */
                exprt.drawPagination(_res, 'stats.getStatistics');
                },
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

    const downloadTemplateExcel = function () {
        $(".btn_excel_template_download").click(function () {
            location.href = `/getPrototypeExcelVer2`;
        });
    }

    const downloadExcel = function () {
        $(".btn_excel_download").click(function () {
            location.href = `/getPrototypeList/excel-ver2`;
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


        $.ajax({
            url: '/getPrototypeList/uploadExcel',
            method: 'POST',
            processData: false,
            contentType: false,
            data: formData,
            dataType: 'json',
            success: function(_res) {
                console.log(_res)
                if (_res.isSuccess) {
                    alert("엑셀 업로드를 완료하였습니다.");
                    window.location.href = '/';
                } else {
                    alert("엑셀 업로드중 에러가 발생하였습니다.");
                }

                },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });
    }

    const getRegisterPage = function (redirectUrl) {
        $("#btn_register").click(function () {
            const lang = $('.lang.on').data('lang');
            window.location.href = redirectUrl+"?lang="+lang;
        });
    }

    return {
        getStatistics : getStatistics,
        changePageSize : changePageSize,
        downloadTemplateExcel : downloadTemplateExcel,
        downloadExcel : downloadExcel,
        onclickUploaBtn : onclickUploaBtn,
        changeUploadExcel : changeUploadExcel,
        getRegisterPage : getRegisterPage,
    }
})();

$(document).ready(function () {
    exprt.keyPressListener(stats.getStatistics);
    exprt.initializeSelectBox();
    exprt.getRegisterPage("/register");
    stats.getStatistics();
    stats.changePageSize();
    stats.downloadTemplateExcel();
    stats.downloadExcel();
    stats.changeUploadExcel();
    stats.onclickUploaBtn();
});

