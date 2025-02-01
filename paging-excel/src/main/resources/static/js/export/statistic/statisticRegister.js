let wholeNation;

const statRgsr = (function () {

    const getConsonantOption = function (){
        // 한글 자음 배열
        const consonants = ['ㄱ', 'ㄴ', 'ㄷ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅅ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ', '기타'];

        // 옵션 추가
        let option = '';
        consonants.forEach(function(consonant) {
            option += '<option value="' + consonant + '">' + consonant + '</option>';
        });
        $('#consonant').append(option);
    }

    const changeConsonant = function (){
        $("#consonant").on("change", function() {
            exprt.setCountrySelectBoxByConsonant($(this).val(), '');
        });
    }

    const changeCheckboxVal = function (){
        $("input[type=checkbox]").on("click", function (){
            const value = $(this).prop("checked") ? 'Y' : 'N';
            $(this).val(value);
        });
    }

    const checkNumericInput = function (){
        $("input[name=price]").on("input", function (){ // 금액
            allowOnlyNumbers(this);
        });
        $("input[name=rate]").on("input", function (){ // 적용환율
            allowOnlyNumbers(this);
        });
    }

    const allowOnlyNumbers = function (inputElement){
        let value = $(inputElement).val();
        // 숫자만 남기고 제거
        const numericValue = value.replace(/[^.0-9]/g, '');
        // 숫자 이외의 문자가 있었다면 input 값 업데이트
        if (value !== numericValue) {
            inputElement.val(numericValue);
        }
    }

    const isValid = function () {

        const year = $("[name='year']").val();
        const country = $("select[name='nation']").val();

        if(year === '') {
            alert("년도는 필수 입력값입니다.");
            $("[name='year']").focus();
            return false;
        }

        if(country === '') {
            alert("국가를 선택해주세요.");
            return false;
        }
    }

    const register = function () {
        $("#btn_register").click(function () {

            if(isValid() === false) {
                return;
            }

            if (!confirm("등록하시겠습니까?")) return;

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
                url: '/export/statistics/register',
                type: 'post',
                data: JSON.stringify({
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
                    alert("등록되었습니다.");
                    window.location.href = '/admin/export/statistics';
                }
            });
        })
    }

    const cancelRegister = function () {
        $("#btn_list").on("click", function(){
            window.location.href = '/admin/export/statistics';
        });


    }

    return {
        getConsonantOption : getConsonantOption,
        changeConsonant : changeConsonant,
        changeCheckboxVal : changeCheckboxVal,
        checkNumericInput : checkNumericInput,
        allowOnlyNumbers : allowOnlyNumbers,
        register : register,
        cancelRegister : cancelRegister
    }
})();

$(document).ready(function () {
    exprt.setRTypeOption();
    statRgsr.getConsonantOption();
    statRgsr.changeConsonant();
    statRgsr.changeCheckboxVal();
    statRgsr.checkNumericInput();
    statRgsr.register();
    statRgsr.cancelRegister();
});
