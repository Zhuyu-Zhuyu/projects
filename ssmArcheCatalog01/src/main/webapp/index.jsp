<%--
  Created by IntelliJ IDEA.
  User: tangxinling
  Date: 2022/3/10
  Time: 12:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>index</title>
</head>
<body>
<a href="movieController/findAll">findAll</a>
根据id查询
<form action="/movieController/findOne">
    id:<input type="text" name="id"><br/>
    <input type="submit" value="提交"/>
</form><br/>
添加电影
<form action="/movieController/addMovie">
    name:<input type="text" name="moviename"/><br/>
    director:<input type="text" name="director"/><br/>
    actor:<input type="text" name="actor"/><br/>
    <input type="submit" value="提交"/>
</form><br/>
修改电影
<form action="/movieController/updataMovie">
    id:<input type="text" name="id"><br/>
    name:<input type="text" name="moviename"/><br/>
    director:<input type="text" name="director"/><br/>
    actor:<input type="text" name="actor"/><br/>
    <input type="submit" value="提交"/>
</form><br/>
删除电影
<form action="/movieController/deleteMovie">
    id:<input type="text" name="id"><br/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
