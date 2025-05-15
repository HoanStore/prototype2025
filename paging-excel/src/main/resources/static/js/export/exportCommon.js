const oEditors = [];

const exprt = (function () {

    const loadEditor = function () {
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditors,
            elPlaceHolder: "content",
            sSkinURI: "/admin/js/se2/SmartEditor2Skin.html",
            htParams: {
                bUseToolbar: true,
                bUseVerticalResizer: true,
                bUseModeChanger: true,
                fOnBeforeUnload: function () {
                },
                fOnAppLoad: function () {
                },
                fCreator: "createSEditor2"
            }
        });
    }

    const getRegisterPage = function (redirectUrl) {
        $("#btn_register").click(function () {
            window.location.href = redirectUrl;
        });
    }

    const getPbanTypeCode = function (pbanTypeNm) {
        common.ajaxRequest({
            url: '/cmm/getPbanTypeCode',
            type: 'post',
            data: JSON.stringify({}),
            dataType: 'json',
            callBack: function (_res) {
                let html = '';
                html = `<option value="">--</option>`;
                _res.forEach(pban => {
                    if(pbanTypeNm === pban.detlCdNm) {
                        html += `<option value=${pban.detlCd} selected>${pban.detlCdNm}</option>`;
                        return;
                    }else {
                        html += `<option value=${pban.detlCd}>${pban.detlCdNm}</option>`;
                    }
                });
                $("#notitype").html(html);
            }
        });
    }

    const getNationalCode = function (continentCd, countryCd, setSelectBox) {
        common.ajaxRequest({
            url: '/cmm/getNationalCode',
            type: 'post',
            data: JSON.stringify({}),
            dataType: 'json',
            callBack: function (_res) {
                wholeNation = _res;
                setSelectBox(continentCd, countryCd);
            }
        });
    }

    const setCountrySelectBox = function (ntnlCode = '') {
        common.ajaxRequest({
            url: '/cmm/getNationalCode',
            type: 'post',
            data: JSON.stringify({}),
            dataType: 'json',
            callBack: function (_res) {
                wholeNation = _res;
                let nationHtml;
                _res.forEach(nation => {
                    if(nation.ntnlCd === ntnlCode) {
                        nationHtml += `<option value=${nation.ntnlCd} selected>${nation.ntnlNm}</option>`;
                        return;
                    }
                    nationHtml += `<option value=${nation.ntnlCd}>${nation.ntnlNm}</option>`;
                });

                $("#country, #nationName").html(nationHtml);
            }
        });
    }

    const setCountrySelectBoxByConsonant = function (consonant = '', ntnlCode = '') {
        common.ajaxRequest({
            url: '/cmm/getNationalCode',
            type: 'post',
            data: JSON.stringify({}),
            dataType: 'json',
            callBack: function (_res) {
                wholeNation = _res;

                const nations = [];
                const cho = ["ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"];
                let nationHtml = '';

                if(ntnlCode !== '') {
                    if(parseInt(ntnlCode, 10) >= 999){
                        consonant = '기타';
                    }else{
                        wholeNation.forEach(nation => {
                            if(nation.ntnlCd === ntnlCode){
                                const code = nation.ntnlNm.charCodeAt(0) - 44032; // 유니코드 값을 리턴
                                consonant = cho[Math.floor(code / 588)];
                            }
                        })
                    }
                }
                if(consonant !== '') {
                    wholeNation.forEach(nation => {
                        // consonant가 '기타'인 경우 영어인 국가만 선택
                        if (consonant === '기타') {
                            if (/^[A-Za-z]/.test(nation.ntnlNm)) {
                                nations.push(nation);
                            }
                        } else {
                            // consonant가 첫 번째 글자의 자음과 일치하는 국가 선택
                            const code = nation.ntnlNm.charCodeAt(0) - 44032; // 유니코드 값을 리턴
                            const firstConsonant = cho[Math.floor(code / 588)];
                            if (firstConsonant && firstConsonant === consonant) {
                                nations.push(nation);
                            }
                        }
                    })
                }

                nationHtml = `<option value="">국가 선택</option>`;
                nations.forEach(nation => {
                    if(ntnlCode === nation.ntnlCd) {
                        nationHtml += `<option value=${nation.ntnlCd} selected>${nation.ntnlNm}</option>`;
                    }else {
                        nationHtml += `<option value=${nation.ntnlCd}>${nation.ntnlNm}</option>`;
                    }
                });

                $("#nation").html(nationHtml);
            }
        });
    }


    const setContinentSelectBox = function (continentCd = '', countryCd = '', hasBaseOption = true) {
        // 1. 대륙 세팅
        const continents = [];
        let etc = null;

        wholeNation.forEach(obj => {
            if(!continents.some(item => item.cnttNm === obj.cnttNm && item.cnttCd === obj.cnttCd ||obj.cnttNm === '기타')) {
                continents.push({ cnttNm: obj.cnttNm, cnttCd: obj.cnttCd});
            }
            if (obj.cnttNm === '기타') {
                etc = { cnttNm: obj.cnttNm, cnttCd: obj.cnttCd };
            }
        });

        if(etc) continents.push(etc);

        let continentHtml = `<option>--</option>`;
        continents.forEach(cnttCode => {
            if(continentCd === cnttCode.cnttCd) {
                continentHtml += `<option value=${cnttCode.cnttCd} selected>${cnttCode.cnttNm}</option>`;
                return;
            }
            continentHtml += `<option value=${cnttCode.cnttCd}>${cnttCode.cnttNm}</option>`;
        })

        $("#continent").html(continentHtml);

        // 2. 국가 세팅
        const nations = [];

        if(countryCd !== '') {
            wholeNation.forEach(nation => {
                if(nation.cnttCd === continentCd) {
                    nations.push(nation);
                }
            })
        }

        let nationHtml = `<option value="">--</option>`;
        nations.forEach(nation => {
            if(countryCd == nation.ntnlCd) {
                nationHtml += `<option value=${nation.ntnlCd} selected>${nation.ntnlNm}</option>`;
                return;
            }
            nationHtml += `<option value=${nation.ntnlCd}>${nation.ntnlNm}</option>`;
        });
        $("#country, #nationName").html(nationHtml);


        // 3. 이벤트 추가
        $("#continent").on("change", handleOptionSelect);
    }

    // 재원구분 세팅
    const setRTypeOption = function (rtypeNm = null) {
        common.ajaxRequest({
            url: '/cmm/getRTypeCode',
            type: 'post',
            data: JSON.stringify({}),
            dataType: 'json',
            callBack: function (_res) {
                let html = '';
                html = `<option value="">재원구분 선택</option>`;
                _res.forEach(rType => {
                    if(rtypeNm != null && rtypeNm === rType.detlCdNm){
                        html += `<option value=${rType.detlCd} selected>${rType.detlCdNm}</option>`;
                    }else{
                        html += `<option value=${rType.detlCd}>${rType.detlCdNm}</option>`;
                    }
                });
                $("#seperate").html(html);
            }
        });
    }

    const handleOptionSelect = function () {
        const nations = [];
        const selectedContinentCode = $(this).val();

        wholeNation.forEach(nation => {
            if(nation.cnttCd === selectedContinentCode) {
                nations.push(nation);
            }
        })

        let nationHtml = `<option value=''>--</option>`;
        nations.forEach(nation => {
            nationHtml += `<option value=${nation.ntnlCd}>${nation.ntnlNm}</option>`;
        });

        $("#country, #nationName").html(nationHtml);
    }

    const initializeSelectBox = function (){
        $(".search_area select").on('change', function (){
            $("#keywordInput").val('');
        });
    }

    const drawList = function (_data, templateName = 'basic_template') {

        let bodyObj = $("#tmt_body"),
            template = $('#'+templateName).html(),
            emptyTemplate = $('#basic_empty_template').html(),
            rendered;
        // 데이터
        if(!_data || _data?.length === 0) {
            Mustache.parse(emptyTemplate);
            rendered = Mustache.render(emptyTemplate);
            bodyObj.html(rendered);
            return;
        }

        Mustache.parse(template);
        rendered = Mustache.render(template, {
            data: _data
        });
        bodyObj.html(rendered);
    }
    const drawPagination = function (_page, _func) {

        let pageObj = $('#div_page'),
            pageTemplate = $('#paging_template').html(),
            rendered;



        Mustache.parse(pageTemplate);

        _page.pages = [];
        $.range(_page.startPage, (_page.startPage + _page.rangeSize)).forEach(function (i) {
            let pageData = {};
            if(i > _page.lastPage) {
                return false;
            }

            if(i === _page.curPage) {
                pageData.current = 'on';
            }

            pageData.page = i;
            _page.pages.push(pageData);
        });

        rendered = Mustache.render(pageTemplate, {data: _page, func: _func});
        pageObj.html(rendered);
    }

    const popup = function (_data) {

        let bodyObj = $(".popup"),
            template = $('#delete_template').html(),
            rendered;

        Mustache.parse(template);
        rendered = Mustache.render(template, {
            data: _data
        });
        bodyObj.html(rendered);
    }

    const keyPressListener = function(callback) {
        $("#keywordInput").on("keypress", function (event) {
            if (event.key === "Enter") {
                callback();
            }
        });
    }

    // 이미지/파일 첨부
    const inputFile = function (){
        allImageCnt = $('.image li').length;
        allFileCnt = $('.notice li').length;
        const upload_box = $('.file_list');
        const upload_img_box = $('#dragBox');
        const upload_file_box = $('#dragDiv');
        let exceedImageCnt = false;
        let exceedFileCnt = false;

        const checkFileName = function (file_name) {
            // 파일명 특수문자 체크
            const pattern = /[\{\}\/?,;:|*~`!^\+<>@\#$%&\\\=\'\"]/gi;
            if (pattern.test(file_name)) {
                //alert("파일명에 허용된 특수문자는 '-', '_', '(', ')', '[', ']', '.' 입니다.");
                return '파일명에서 특수문자를 제거해주세요.';
            }
        }
        const checkFileExtension = function (file_name) {
            // 파일 확장자 검사
            const regex = new RegExp("(.*?)\.(exe|sh|alz|html|htm|php|php2|php3|php4|php5|phtml|pwml|inc|asp|aspx|ascx|jsp|cfm|cfc|pl|bat|com|dll|vbs|js|reg|cgi|asis|shtml|shtm|phtm)$");
            if (regex.test(file_name)) {
                return '해당 파일은 업로드할 수 없습니다.';
            }
        }
        const checkFileType = function (file_type, type){
            if(type === "image" && !file_type.startsWith('image/')){
                return '이미지 파일을 업로드해주세요.';
            }else if(type === "notice" && file_type.startsWith('image/')){
                return '이미지를 제외한 파일을 업로드해주세요.';
            }
        }
        function checkFileSize(file_size) {
            const maxSize = 100 * 1024 * 1024;

            if (file_size >= maxSize) {
                return '파일 크기는 100MB 이하로 업로드해주세요.';
            }
        }
        function checkFileCnt(curFileCnt, maxAllowedFiles) {
            if (curFileCnt >= maxAllowedFiles) {
                alert('업로드 개수제한 초과입니다.');
                return true;
            }
        }

        const checkValidation = function (files, fileType, alertTexts){
            for (const file of files) {
                // 파일명 특수문자
                if (checkFileName(file.name)) {
                    alertTexts.fileNameText.push(checkFileName(file.name));
                    continue;
                }
                // 파일 확장자 체크
                if (checkFileExtension(file.name)) {
                    alertTexts.fileExtensionText.push(checkFileExtension(file.name));
                    continue;
                }
                // 파일 타입 체크
                if (checkFileType(file.type, fileType)) {
                    alertTexts.fileTypeText.push(checkFileType(file.type, fileType));
                    continue;
                }
                // 파일 크기 제한
                if (checkFileSize(file.size)){ // byte
                    alertTexts.fileSizeText.push(checkFileSize(file.size));
                    continue;
                }
                // 파일 개수 제한
                if(fileType === "image"){
                    if (checkFileCnt(allImageCnt, 4)) {
                        exceedImageCnt = true;
                        break;
                    }
                    allImageCnt++; // 전체 이미지 개수
                }else if(fileType === "notice"){
                    if (checkFileCnt(allFileCnt, 10)) {
                        exceedFileCnt = true;
                        break;
                    }
                    allFileCnt++; // 전체 파일 개수
                }

                // 드롭한 파일 dataTransfer에 추가 (기존의 파일 유지 목적)
                dataTransfer.items.add(file);
                // 선택한 파일명 표시
                let innerHtml = '';
                innerHtml += '<li class="' + fileType + '_list">';
                innerHtml += '<input class="' + (fileType === 'image'? 'addedImg' : 'addedFile') + '" type="checkbox" data-lastModified="' + file.lastModified + '"/>';
                innerHtml += ' <span>' + file.name + '</span>';
                innerHtml += '</li>';
                $('.' + fileType).append(innerHtml);
            }
        }
        const addFiles = function (files, fileType){
            const alertTexts = {
                fileNameText : [],
                fileTypeText : [],
                fileSizeText : [],
                fileExtensionText : []
            }
            // 유효성 체크
            checkValidation(files, fileType, alertTexts);

            // input에 파일 추가
            $("#file")[0].files = dataTransfer.files;

            console.log("input files =>", $("#file")[0].files);

            // 유효하지 않은 파일 알람창 표시
            if(alertTexts.fileNameText.length > 0) {
                alert(alertTexts.fileNameText[0]);
                return;
            }
            if(alertTexts.fileTypeText.length > 0) {
                alert(alertTexts.fileTypeText[0]);
                return;
            }
            if(alertTexts.fileSizeText.length > 0) {
                alert(alertTexts.fileSizeText[0]);
                return;
            }
            if(alertTexts.fileExtensionText.length > 0) {
                alert(alertTexts.fileExtensionText[0]);
                return;
            }

            if(exceedImageCnt === false && exceedFileCnt === false){
                console.log('브라우저에 업로드 완료');
            }
        }


        const addLogoImage = function (files, fileType){
            const alertTexts = {
                fileNameText : [],
                fileTypeText : [],
                fileSizeText : [],
                fileExtensionText : []
            }
            // 유효성 체크
            checkValidation(files, fileType, alertTexts);

            // input에 파일 추가
            $("#file_logo")[0].files = files;

            // dataTransfer에서는 제거
            for(var i=0; i<dataTransfer.files.length; i++) {
                if(dataTransfer.files[i].lastModified === files[0].lastModified) {
                    dataTransfer.items.remove(i);
                    break;
                }
            }

            // 유효하지 않은 파일 알람창 표시
            if(alertTexts.fileNameText.length > 0) {
                alert(alertTexts.fileNameText[0]);
                return;
            }
            if(alertTexts.fileTypeText.length > 0) {
                alert(alertTexts.fileTypeText[0]);
                return;
            }
            if(alertTexts.fileSizeText.length > 0) {
                alert(alertTexts.fileSizeText[0]);
                return;
            }
            if(alertTexts.fileExtensionText.length > 0) {
                alert(alertTexts.fileExtensionText[0]);
                return;
            }

            if(exceedImageCnt === false && exceedFileCnt === false){
                console.log('브라우저에 업로드 완료');
            }
        }

        const deleteFiles = function (saved_files, added_files) {
            const files = Array.from(dataTransfer.files);

            // 새로 업로드한 file 삭제
            if (added_files.length > 0) {

                const updatedFiles = files.filter(file => {
                    return !added_files.toArray().some(added_image => {
                        const lastModified = parseInt($(added_image).attr('data-lastModified'));
                        return file.lastModified === lastModified;
                    });
                });
                dataTransfer.items.clear();
                updatedFiles.forEach(file => dataTransfer.items.add(file));
                $("#file")[0].files = dataTransfer.files;
                console.log("current files =>", $("#file")[0].files);

                added_files.each(function () {$(this).parent().remove();});

                if(added_files[0].classList.contains('addedImg')) allImageCnt -= added_files.length;
                else allFileCnt -= added_files.length;

                if(saved_files.length === 0) {
                    setTimeout(function(){
                    }, 100);
                }
            }

            // 저장된 file 삭제 (수정페이지)
            if (saved_files.length > 0) {
                for (let index = 0; index < saved_files.length; index++) {
                    const element = saved_files[index];

                    $(element).parent().remove();

                    if ($(element).hasClass('savedImg')) allImageCnt--;
                    else if ($(element).hasClass('savedFile')) allFileCnt--;

                    deleteFileList.push($(element).data('seq'));
                }
            }
        }

        //drag and drop
        /* 박스 안에 Drag 들어왔을 때 */
        upload_box.on('dragenter', function (e) {
            console.log('dragenter');
        });
        /* 박스 안에 Drag를 하고 있을 때 */
        upload_box.on('dragover', function (e) {
            e.preventDefault();
        });
        /* 박스 밖으로 Drag가 나갈 때 */
        upload_box.on('dragleave', function (e) {
            console.log('dragleave');
        });
        /* 이미지 박스 안에서 Drag를 Drop했을 때 */
        upload_img_box.on('drop', function (e) {
            e.preventDefault();

            const files = e.originalEvent.dataTransfer.files;
            addFiles(files, "image");
        });
        /* 파일 박스 안에서 Drag를 Drop했을 때 */
        upload_file_box.on('drop', function (e) {
            e.preventDefault();

            const files = e.originalEvent.dataTransfer.files;
            addFiles(files, "notice");
        });

        // logo 삭제 버튼 클릭
        $('#btn_logodel').on('click', async function (e) {
            e.preventDefault();

            const fileInput = $("#logo_img");
            const newFileInput = fileInput.clone();

            fileInput.replaceWith(newFileInput);
            fileInput.off();
            newFileInput.val('');

            if($(".savedLogo:checked").length > 0){
                deleteFileList.push($(".savedLogo").data('seq'));
            }else if($(".addedLogo:checked").length > 0){
                const agent = navigator.userAgent.toLowerCase();
                //파일초기화
                if(agent.indexOf("trident") !== -1 || agent.indexOf("msie") !== -1){
                    $("#logo_img").replaceWith($("#logo_img").clone(true));
                }else{
                    $("#logo_img").val("");
                }
            }
            console.log("delete logo =>", $("#logo_img")[0].files);
            $(this).parents('td').find('li').remove();
        });


        // image 삭제 버튼 클릭
        $('#btn_imagedel').on('click', async function (e) {
            e.preventDefault();

            const saved_images = $("#image_list .savedImg:checked");
            const added_images = $("#image_list .addedImg:checked");

            deleteFiles(saved_images, added_images);
        });

        // file 삭제 버튼 클릭
        $('#btn_noticedel').on('click', async function (e){
            e.preventDefault();

            const saved_files = $(".notice_list .savedFile:checked");
            const added_files = $(".notice_list .addedFile:checked");

            deleteFiles(saved_files, added_files);
        });

        // 로고 업로드 (찾아보기 버튼)
        $("#btn_logo").on("click", function (e) {
            if($("#logo_img")[0].files.length > 0) {
                alert("기업 로고는 한 장 밖에 올리지 못 합니다.");
                return;
            }

            const logo_img = $("#logo_img")[0].files;
            e.preventDefault();

            $("#logo_img").click();
            $("#logo_img").off("change");
            $("#logo_img").change(function (e) {

                addLogoImage(e.target.files, "logo");

                console.log("input logo =>", $("#logo_img")[0].files);
            });
        });

        // 파일 업로드 (찾아보기 버튼)
        $(".btn_file").on("click", function (e) {
            const fileType = $(this).attr('id') === 'btn_image' ? "image" : "notice";

            e.preventDefault();
            $("#file").click();
            $("#file").off("change");
            $("#file").change(function (e) {
                const files = e.target.files;
                if (files.length > 0) {
                    addFiles(files, fileType);
                }
            });
        });
    }


    return {
        loadEditor : loadEditor,
        getRegisterPage : getRegisterPage,
        drawList : drawList,
        drawPagination : drawPagination,
        keyPressListener : keyPressListener,
        popup : popup,
        inputFile : inputFile,
        getPbanTypeCode : getPbanTypeCode,
        getNationalCode : getNationalCode,
        setCountrySelectBox : setCountrySelectBox,
        setCountrySelectBoxByConsonant : setCountrySelectBoxByConsonant,
        setContinentSelectBox : setContinentSelectBox,
        setRTypeOption : setRTypeOption,
        initializeSelectBox : initializeSelectBox
    }
})();
