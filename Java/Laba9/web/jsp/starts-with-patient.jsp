<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<html>
<head>
    <title>Insert Patient</title>
    <link href="../css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="/jsp/header.jsp"/>

    <div class="content">
        <jsp:useBean id="myPatients" class="bean.Wrapper"/>

        <form name="insert-patient-form" action="${pageContext.request.contextPath}/controller" method="GET">
            <input type="hidden" name="command" value="starts-with"/>
            First Name Starts With<input type="text" name="starts-with-value" value=${sessionScope.starts-with-value != null ? sessionScope.starts-with-value : ''}>
            <br><br>
            <p>
                <input class="btn1" type="submit" name="buttonSW" value="Submit"/>
            </p>
        </form>

        <br>Patients
        <br>
        <table>
            <tr class="tr-h">
                <th class="td1">ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Ward's number</th>
                <th>Diagnosis</th>
            </tr>
            <c:forEach var="patient" items="${myPatients.patients}">
                <tr class="tr1">
                    <td class="td1">${patient.id}</td>
                    <td>${patient.firstName}</td>
                    <td>${patient.lastName}</td>
                    <td>${patient.wardsNumber}</td>
                    <td>${patient.diagnosis}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <jsp:include page="/jsp/footer.jsp"/>
</div>

</body>
</html>
