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

        common.ajaxRequest({
            url: '/export/statistics',
            type: 'post',
            data: JSON.stringify({curPage: _pageNo, pageSize: _pageSize, searchType: _searchType, keyword : _keyword}),
            dataType: 'json',
            callBack: function (_res) {
                $("#perPageNum option:first").val(_res.page.listCnt);
                $(".tb_txt strong").text(_res.page.listCnt);

                _res.data = transformStype(_res.data);
                exprt.drawList(_res.data, 'statistics_template');
                exprt.drawPagination(_res.page, 'stats.getStatistics');
            }
        });
    }

    const transformStype = function (data){

        const systems = ['교통관리', '대중교통', '전자지불', '교통정보유통', '지능형차량도로', '화물운송', '여행정보제공'];

        for(let i = 0; i < data.length; i++){
            const stype = data[i].stype;
            let result = '';

            for (let j = 0; j < stype.length; j++) {
                if (stype[j] === 'Y') {
                    if (result !== '') {
                        result += '\n';
                    }
                    result += '- ' + systems[j];
                }
            }
            data[i].stype = result;
        }
        return data;
    }

    const setSystemSelectBox = function (){
        $(".search_area select").on('change', function (){
            if($(".search_area select").val() === 'its_system'){
                $("#keywordInput").hide();
                $("#its_system").show();
            }else {
                $("#keywordInput").show();
                $("#its_system").hide();
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
            location.href = `/admin/export/statistics/excel/template`;
        });
    }
    const downloadExcel = function () {
        $(".btn_excel_download").click(function () {
            const searchType = $("select[name='keyfield']").val();
            const keyWord = $("#keywordInput").val();

            location.href = `/admin/export/statistics/excel?searchType=${searchType}&keyWord=${keyWord}`;
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
        setSystemSelectBox : setSystemSelectBox,
        changePageSize : changePageSize,
        downloadExcel : downloadExcel,
        downloadTemplateExcel : downloadTemplateExcel,
        onclickUploaBtn : onclickUploaBtn,
        changeUploadExcel : changeUploadExcel
    }
})();

$(document).ready(function () {
    exprt.keyPressListener(stats.getStatistics);
    exprt.initializeSelectBox();
    exprt.getRegisterPage("/admin/export/statistics/register");
    stats.getStatistics();
    stats.setSystemSelectBox();
    stats.changePageSize();
    stats.downloadExcel();
    stats.downloadTemplateExcel();
    stats.changeUploadExcel();
    stats.onclickUploaBtn();
});

