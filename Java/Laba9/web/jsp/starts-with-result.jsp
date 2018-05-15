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
            <c:forEach var="patient" items="${sessionScope.patients}">
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
