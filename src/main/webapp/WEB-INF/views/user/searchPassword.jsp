<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>비밀번호 찾기</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        let emailMailNumber = "";

        let emailCheck = "N";

        function emailExists(f) {

            if (f.email.value === "") {
                alert("이메일을 입력하세요.");
                f.email.focus();
                return;
            }

            $.ajax({
                    url: "/user/getEmailSend",
                    type: "post",
                    dataType: "JSON",
                    data: $("#f").serialize(),
                    success: function (json) {
                        alert("이메일로 인증번호가 발송되었습니다. \n받은 메일의 인증번호를 입력하기 바랍니다.");
                        emailMailNumber = json.mailNumber;
                    }
                }
            )
        }

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            // 로그인
            $("#btnLogin").on("click", function () {
                location.href = "/user/login";
            })

            $("#btnEmail").on("click", function () {
                emailExists(f)
            })

            $("#btnSearchPassword").on("click", function () {
                let f = document.getElementById("f");

                if (f.userId.value === "") {
                    alert("아이디를 입력하세요");
                    f.userId.focus();
                    return;
                }

                if (f.userName.value === "") {
                    alert("이름을 입력하세요");
                    f.userName.focus();
                    return;
                }

                if (f.email.value === "") {
                    alert("이메일 주소를 입력하세요");
                    f.email.focus();
                    return;
                }

                if (f.mailNumber.value === "") {
                    alert("이메일 인증번호를 입력하세요.");
                    f.mailNumber.focus();
                    return;
                }

                if (emailCheck === "N") {
                    alert("이메일 인증을 해주세요.");
                    f.mailNumber.focus();
                    return;
                }

                f.method = "post";
                f.action = "/user/searchPasswordProc";

                f.submit();
            })

            $("#btnNumber").on("click", function () {

                if (f.mailNumber.value === "") {
                    alert("이메일 인증번호를 입력하세요.");
                    f.mailNumber.focus();
                    return;
                }

                if (f.mailNumber.value !== emailMailNumber.toString()) {
                    alert("이메일 인증번호가 일치하지 않습니다.");
                    f.mailNumber.focus();
                    return;
                } else {
                    alert("이메일 인증이 성공했습니다!");
                    emailCheck = "Y";
                }
            })


        })
    </script>
</head>
<body>
<h2>비밀번호 찾기</h2>
<hr/>
<br/>
<form id="f">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">아이디
                </div>
                <div class="divTableCell">
                    <input type="text" name="userId" id="userId" style="width:95%;"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">이름
                </div>
                <div class="divTableCell">
                    <input type="text" name="userName" id="userName" style="width:95%;"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">이메일
                </div>
                <div class="divTableCell">
                    <input type="email" name="email" id="email" style="width:80%;"/>
                    <button id="btnEmail" type="button">인증번호 발송</button>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">인증번호
                </div>
                <div class="divTableCell">
                    <input type="text" name="mailNumber" id="mailNumber" style="width:80%;"/>
                    <button id="btnNumber" type="button">인증번호 확인</button>
                </div>
            </div>
        </div>
    </div>
    <div>
        <button id="btnSearchPassword" type="button">비밀번호 찾기</button>
        <button id="btnLogin" type="button">로그인</button>
    </div>
</form>
</body>
</html>

