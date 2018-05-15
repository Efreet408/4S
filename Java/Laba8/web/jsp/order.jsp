<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" uri="tag"%>
<%--<%@page errorPage="/jsp/error.jsp" %>--%>
<html>
<head>
    <title>Third page</title>
    <link href="css/styles.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>

Your order
    <br>Pizza: ${sessionScope.pizza}
    <br>First Name: ${sessionScope.firstName}
    <br>Last Name: ${sessionScope.lastName}
    <t:mytag address= "${empty sessionScope.address ? '-' : sessionScope.address}">Address</t:mytag>
    <form name="3" action="/2" method="GET">
        <br><br><input class="button" type="submit" value="Pizza"/>
    </form>
</body>
</html>
