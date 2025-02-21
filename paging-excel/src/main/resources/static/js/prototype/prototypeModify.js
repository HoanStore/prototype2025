const dataTransfer = new DataTransfer();

const exRgsr = (function () {

    const getDatepicker = function (){
        $("#datepicker").datepicker({
            numberOfMonths: 1,
            dateFormat: "yy-mm-dd",
            changeYear: true
        });
    }

    let initial_field = 1;
    const addImgInfo = function (){
        const max_fields = 2;

        $("#add_field_btn").on("click", function(e){
            e.preventDefault();

            if(initial_field >= max_fields){
                alert("2개 이상은 등록할 수 없습니다.");
                return false;
            }else {
                let input_field = '';
                input_field += "<li><input id='imageinfo2' maxlength='30' name='attflIstc' type='text' title='이미지 설명' class='w_90per' value=''/>  ";
                input_field	+="<div class='btn'><a href='#' id='remove_field_btn'><img src='/admin/images/common/btn_out.gif' alt='설명빼기'></a>  ";
                input_field	+="</div></li>";
                $("#input_field_wrap").append(input_field);
                initial_field++;
            }
        });
    }

    const removeImgInfo = function (){

        $(document).on("click", "#remove_field_btn", function(e){
            e.preventDefault();

            $(this).parent("div").parent("li").remove(); //부모위에 상위 부모
            initial_field--;
        });
    }

    const preview = function () {
        $("#btn_preview").on("click", function(){

            const title = $("#title").val();
            const businessname = $("#businessname").val();
            const content = $("#ordercontent").val();
            const datepicker = $("#datepicker").val();
            const ordercompany = $("#ordercompany").val();

            $("#popup_title").html(title);
            $("#popup_ordercontent").html(content);

            $(".pop, pop_view").show();

            // 이미지 추가
            const popup_file = '<li>';
            file_add(popup_file, dataTransfer.files, $('#popup_attachment'));

            async function file_add(popup_file, files, path) {
                const imageinfo1 = $("#imageinfo1").val();
                const imageinfo2 = $("#imageinfo2") ? $("#imageinfo2").val() : false;

                for (let i=0;i<files.length;i++) {
                    const file = files[i];
                    const imageinfo = (i === 0) ? imageinfo1 : imageinfo2;

                    await new Promise((resolve) => {
                        const reader = new FileReader();

                        reader.onload = function (event) {
                            const dataURL = event.target.result;

                            popup_file += '<div class="img_box img">';
                            popup_file += '<img src="' + dataURL + '" alt="첨부이미지"/>';
                            popup_file += '<p>' + (imageinfo ? imageinfo : '-') + '</p>';
                            popup_file += '</div>';
                            resolve();
                        };
                        reader.readAsDataURL(file);
                    });
                }
                popup_file += '</li>';
                path.html(popup_file);
            }

            //닫기 버튼시
            $(".btn_close").on("click", function(){
                $(".pop, pop_view").hide();
            });

            //확인 버튼시
            $("#btn_check").on("click", function(){
                $(".pop, pop_view").hide();
            });

        });
    }

    const register = function () {
        $("#btn_register").click(function () {

            if(isValid() === false) {
                return;
            }

            const formData = new FormData($("#form")[0]);

            // file list 추가
            const fileLists = $("#file")[0].files;
            for (let i = 0; i < fileLists.length; i++) {
                formData.append("fileLists", fileLists[i]);
            }

            $.ajax({
                url: '/register',
                method: 'POST',
                data: formData,
                processData: false,    // FormData 객체는 jQuery가 처리하지 않도록 설정
                contentType: false,    // `multipart/form-data` 헤더는 자동으로 처리되도록 설정
                success: function(_res) {
                    window.location.href = '/';
                },
                error: function(xhr, status, error) {
                    console.error('Error:', error);
                    window.location.href = '/';
                }
            });


        })
    }

    const cancelRegister = function () {
        $("#btn_list").on("click", function(){
            window.location.href = '/';
        });
    }


    const isValid = function () {

        const title = $("#title").val();
        const businessname = $("#businessname").val();
        const ordercontent = $("#ordercontent").val();
        const datepicker = $("#datepicker").val();
        const ordercompany = $("#ordercompany").val();
        const regName = $("#reg_name").val();

        if(title == '') {
            alert("제목을 입력해주세요.");
            return false;
        }

        if(businessname == '') {
            alert("사업명을 입력해주세요.");
            return false;
        }

        if(ordercontent == '') {
            alert("사업내용을 입력해주세요.");
            return false;
        }

        if(datepicker == '') {
            alert("수주시기를 입력해주세요.");
            return false;
        }

        if(ordercompany == '') {
            alert("수주기업을 입력해주세요.");
            return false;
        }

        if(regName == '') {
            alert("작성자명을 입력해주세요.");
            return false;
        }

        if(regName == '') {
            alert("작성자명을 입력해주세요.");
            return false;
        }
        return true;
    }

    return {
        getDatepicker : getDatepicker,
        addImgInfo : addImgInfo,
        removeImgInfo : removeImgInfo,
        preview : preview,
        register : register,
        cancelRegister : cancelRegister
    }
})();

$(document).ready(function () {
    exprt.inputFile();
    exRgsr.getDatepicker();
    exRgsr.addImgInfo();
    exRgsr.removeImgInfo();
    exRgsr.preview();
    exRgsr.register();
    exRgsr.cancelRegister();
});
