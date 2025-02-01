const statmdf = (function () {

    const getConsonantOption = function (){
        // 한글 자음 배열
        const consonants = ['ㄱ', 'ㄴ', 'ㄷ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅅ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ', '기타'];

        // 옵션 추가
        const country = $("#country").val();
        const cho = ["ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"];
        const code = country.charCodeAt(0) - 44032; // 유니코드 값을 리턴
        const selectedCho = cho[Math.floor(code / 588)];
        let option = '';

        consonants.forEach(function(consonant) {
            if(consonant === '기타'){
                if (/^[A-Za-z]/.test(country)) {
                    option += '<option value="' + consonant + '" selected>' + consonant + '</option>';
                }else{
                    option += '<option value="' + consonant + '">' + consonant + '</option>';
                }
            }else if(consonant === selectedCho){
                option += '<option value="' + consonant + '" selected>' + consonant + '</option>';
            }else{
                option += '<option value="' + consonant + '">' + consonant + '</option>';
            }
        });
        $('#consonant').append(option);
    }

    const changeConsonant = function (){
        $("#consonant").on("change", function() {
            exprt.setCountrySelectBoxByConsonant($(this).val(), '');
        });
    }

    const checkSystemCheckBox = function (){
        const stype = $("#stype").val();
        const checkbox = $("input[type=checkbox]");

        for(let i = 0;i < stype.length;i++){
            if(stype[i] === 'Y'){
                $(checkbox[i]).prop('checked', true);
                $(checkbox[i]).val('Y');
            }
        }
    }

    const changeCheckboxVal = function (){
        $("input[type=checkbox]").on("click", function (){
            const value = $(this).prop("checked") ? 'Y' : 'N';
            $(this).val(value);
        });
    }

    const isValid = function () {

        const year = $("[name='year']").val();
        const country = $("select[name='nation']").val();

        if(year === '') {
            alert("년도는 필수 입력값입니다.");
            return false;
        }

        if(country === '') {
            alert("국가를 선택해주세요.");
            return false;
        }
    }

    const modify = function () {
        $("#btn_modify").click(function () {

            if(isValid() === false) {
                return;
            }
            if (!confirm("수정하시겠습니까?")) return;

            const bno = $(".cont_box").data("bno");
            const continent = $("#continent").val();
            const btype = $("select[name='businesstype']").val();
            const year = $("[name='year']").val();
            const country = $("select[name='nation']").val();
            const content = $("[name='content']").val();
            const cname =  $("[name=companyname]").val();
            const money =  $("[name=price]").val();
            const rtype =  $("[name=seperate]").val();
            const bsource =  $("[name=budget]").val();
            const serialNumber =  $("[name=basedata]").val();
            const currencyRate =  $("[name=rate]").val();
            let stype =  '';

            $("input[type=checkbox]").each(function() {
                stype += $(this).val();
            });

            common.ajaxRequest({
                url: '/export/statistics/modify',
                type: 'put',
                data: JSON.stringify({
                    bno: bno,
                    continent: continent,
                    btype: btype,
                    year : year,
                    country : country,
                    cname : cname,
                    money : money,
                    rtype : rtype,
                    stype : stype,
                    bsource : bsource,
                    serialNumber : serialNumber,
                    currencyRate : currencyRate,
                    content : content
                }),
                dataType: 'json',
                callBack: function (_res) {
                    alert("수정되었습니다.");
                    window.location.href = '/admin/export/statistics';
                }
            });
        })
    }

    const cancelModify = function () {
        $("#btn_list").on("click", function(){
            const bno = $(".cont_box").data("bno");
            window.location.href = '/admin/export/statistics';
        });
    }

    const popupInit = function () {

        $("#btn_delte").click(function(){
            $(".pop, pop_check").show();
            $(".btn_close").click(function(){
                $(".pop, pop_check").hide();
            });
            deleteStatistic();
        });
    }

    const deleteStatistic = function () {
        $(".pop .pop_box .btn").click(function () {
            const bno = $(".cont_box").data("bno");

            common.ajaxRequest({
                url: '/export/statistics/' + bno,
                type: 'delete',
                callBack: function (_res) {
                    window.location.href = '/admin/export/statistics';
                }
            });
        })
    }

    return {
        getConsonantOption : getConsonantOption,
        changeConsonant : changeConsonant,
        checkSystemCheckBox : checkSystemCheckBox,
        changeCheckboxVal : changeCheckboxVal,
        modify : modify,
        cancel : cancelModify,
        popupInit : popupInit
    }
})();

$(document).ready(function () {
    statmdf.getConsonantOption();
    statmdf.changeConsonant();
    statmdf.checkSystemCheckBox();
    statmdf.changeCheckboxVal();
    statmdf.modify();
    statmdf.cancel();
    statmdf.popupInit();

    exprt.setCountrySelectBoxByConsonant($("#consonant").val(), $("#countryCd").val());
    exprt.setRTypeOption($("#rtype").val());
});
