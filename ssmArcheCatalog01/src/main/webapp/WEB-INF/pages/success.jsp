<%--
  Created by IntelliJ IDEA.
  User: tangxinling
  Date: 2022/3/10
  Time: 12:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" %>
<%--添加jstl核心库--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>success</title>
</head>
<body>
<h1>success</h1>
<%--输出所有查询结果--%>
<c:forEach items="${movies}" var="movie">
    ${movie.id}
    ${movie.moviename}
    ${movie.director}
    ${movie.actor}
</c:forEach>
<%--输出一个查询结果--%>
${movie.id}
${movie.moviename}
${movie.director}
${movie.actor}
</body>
</html>
