<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 17.04.2018
  Time: 22:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page errorPage="/jsp/error.jsp" %>
<html>
<head>
    <title>Error</title>
    <link href="css/styles.css" rel="stylesheet"/>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>

<div id="content">
    <div id="content-header">
        <h2 class="header-text">Error:</h2>
    </div>
    <div id="content-main">
        <div id="form" class="infobox">
            <p>statusCode: ${pageContext.errorData.statusCode}</p>
            <p>requestURI: ${pageContext.errorData.requestURI}</p>
            <p>message: ${pageContext.errorData.throwable.message}</p>
            <a href=${pageContext.request.contextPath}${pageContext.errorData.requestURI}>Input name again</a>
        </div>
    </div>
</div>

</body>
</html>
