<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 17.04.2018
  Time: 23:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page errorPage="/jsp/error.jsp" %>
<html>
<head>
    <title>Title</title>
    <link href="css/styles.css" rel="stylesheet" type="text/css"/>
    <style></style>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<p>START</p>
<form name="startform" action="/1" method="GET">
    <input class="button" type="submit" name="buttonStart" value="START"/>
</form>
</body>
</html>
