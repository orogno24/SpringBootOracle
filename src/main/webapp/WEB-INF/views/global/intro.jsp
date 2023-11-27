<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>

<%
    String roomName = CmmUtil.nvl(request.getParameter("roomName"));

    String userName = CmmUtil.nvl(request.getParameter("userName"));
%>
<html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><%=roomName%> 채팅방 입장 </title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        let data = {};
        let ws;
        const roomName = "<%=roomName%>"
        const userName = "<%=userName%>"

        $(document).ready(function () {

            if (ws !== undefined && ws.readyState !== WebSocket.CLOSED) {
                console.log("WebSocket is already opened.")
                return;
            }

            ws = new WebSocket("ws://" + location.host + "/ws/" + roomName + "/" + userName);

            ws.onopen = function (event) {
                if (event.data === undefined)
                    return;

                console.log(event.data)
            };

            ws.onmessage = function (msg) {

                let data = JSON.parse(msg.data);

                if (data.name === userName) {
                    $("#chat").append("<div>");
                    $("#chat").append("<span style='color: blue'><b>[보낸 사람] : </b></span>");
                    $("#chat").append("<span style='color: blue'> 나 </span>");
                    $("#chat").append("<span style='color: blue'><b>[발송 메시지] : </b></span>");
                    $("#chat").append("<span style='color: blue'>" + data.msg + " </span>");
                    $("#chat").append("<span style='color: blue'><b>[발송시간] : </b></span>");
                    $("#chat").append("<span style='color: blue'>" + data.date + " </span>");
                    $("#chat").append("</div>");
                } else if (data.name === "관리자") {
                        $("#chat").append("<div>");
                        $("#chat").append("<span style='color: red'><b>[보낸 사람] : </b></span>");
                        $("#chat").append("<span style='color: red'> 나 </span>");
                        $("#chat").append("<span style='color: red'><b>[발송 메시지] : </b></span>");
                        $("#chat").append("<span style='color: red'>" + data.msg + " </span>");
                        $("#chat").append("<span style='color: red'><b>[발송시간] : </b></span>");
                        $("#chat").append("<span style='color: red'>" + data.date + " </span>");
                        $("#chat").append("</div>");
                } else {
                    $("#chat").append("<div>");
                    $("#chat").append("<span><b>[보낸 사람] : </b></span>");
                    $("#chat").append("<span> 나 </span>");
                    $("#chat").append("<span><b>[발송 메시지] : </b></span>");
                    $("#chat").append("<span>" + data.msg + " </span>");
                    $("#chat").append("<span><b>[발송시간] : </b></span>");
                    $("#chat").append("<span>" + data.date + " </span>");
                    $("#chat").append("</div>");
                }

                $("#btnSend").on("click", function () {
                    data.name = userName;
                    data.msg = $("msg").val();

                    let chatMsg = JSON.stringify(data);

                    ws.send(chatMsg);

                    $("#msg").val("")
                })
            }
        });
    </script>
</head>
<body>
<h1><%=userName%>님! <%=roomName%> 입장을 환영합니다.</h1>
<hr/>
<br/><br/>

<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">대화 내용</div>
        </div>
    </div>
    <div class="divTableBody">
        <div class="divTableRow">
            <div class="divTableCell" id="chat"></div>
        </div>
    </div>
</div>
<br/><br/>
<div class="divTable minimalistBlack">
    <div class="divTableBody">
        <div class="divTableRow">
            <div class="divTableCell">전달할 메시지</div>
            <div class="divTableCell">
                <input type="text" id="msg">
                <button id="btnSend">메시지 전송</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
