<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.dto.MovieDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.util.CmmUtil" %>

<%
    List<MovieDTO> rList = (List<MovieDTO>) request.getAttribute("rList");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>영화 순위 결과</title>
    <link rel="stylesheet" href="/css/table.css"/>
</head>
<body>
<h2>영화 순위 정보</h2>
<hr/>
<br/>

<% if (rList == null || rList.isEmpty()) { %>
게시글을 찾을 수 없습니다.
<% } else { %>

<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">순위</div>
            <div class="divTableHead">제목</div>
            <div class="divTableHead">평점</div>
            <div class="divTableHead">개봉일</div>
        </div>
    </div>
    <div class="divTableBody">
        <%
            for (MovieDTO dto : rList) {
        %>
        <div class="divTableRow">
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getMovieRank())%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getMovieNm())%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getScore())%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getOpenDay())%></div>
        </div>
        <% } %>
    </div>
</div>
<% } %>
</body>
</html>
