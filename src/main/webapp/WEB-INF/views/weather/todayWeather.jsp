<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.dto.WeatherDTO" %>

<%
    List<WeatherDTO> rList = (List<WeatherDTO>) request.getAttribute("rList");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>서울강서캠퍼스 날씨</title>
    <link rel="stylesheet" href="/css/table.css"/>
</head>
<body>
<h2>현재 한국폴리텍대학 서울강서캠퍼스 날씨는?</h2>
<hr/>
<br/>
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">날씨</div>
            <div class="divTableHead">현재온도</div>
            <div class="divTableHead">장소</div>
        </div>
    </div>
    <div class="divTableBody">
        <%
            for (WeatherDTO rDTO : rList) {
        %>
        <div class="divTableRow">
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getLocation())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getStatus1())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getStatus2())%>
            </div>
        </div>
        <% } %>
    </div>
</div>
</body>
</html>
