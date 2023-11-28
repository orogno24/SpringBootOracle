<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    //Controller로부터 전달받은 데이터
    String res = CmmUtil.nvl((String) request.getAttribute("res"));

    res = res.replaceAll("\n", " "); //줄바꿈(엔터)를 한칸 공백 " "로 변경
    res = res.replaceAll("\"", " "); // "(큰 따옴표) 특수문자를 한칸 공백 " "으로 변경
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이미지 파일로부터 인식된 문자열 읽어주기</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script>
        const myOcrText = "<%=res%>";

        $(document).ready(function () {
            //버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
            $("#btnTextRead").on("click", function () {
                speak(myOcrText);
            })
        })

        function speak(text) {
            if (typeof SpeechSynthesisUtterance === "undefined" ||
                typeof window.speechSynthesis === "undefined") {
                alert("이 브라우저는 문자읽기 기능을 지원하지 않습니다.");
                return;
            }

            window.speechSynthesis.cancel()

            const speechMsg = new SpeechSynthesisUtterance()
            speechMsg.rate = 1;
            speechMsg.pitch = 1;
            speechMsg.lang = "ko-KR";
            speechMsg.text = text;

            // 문자 읽기
            window.speechSynthesis.speak(speechMsg);
        }
    </script>
</head>
<body>
<h2>이미지 파일로부터 인식된 문자열 읽어주기</h2>
<hr/>
<br/>
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">이미지 파일 업로드</div>
        </div>
    </div>
    <div class="divTableBody">
        <div class="divTableRow">
            <div class="divTableCell"><%=res%>
            </div>
        </div>
    </div>
</div>
<div>
    <button id="btnTextRead" type="button">문자열 읽기</button>
</div>
</body>
</html>