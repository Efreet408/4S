<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ru'}" scope="session" />

<fmt:setLocale value="${language}"  scope="session"/>
<fmt:setBundle basename="text" />
<!DOCTYPE html>
<html lang="${language}">
<head>
    <%--<title>Show Students</title>--%>
        <title>${language}</title>
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="/jsp/header.jsp"/>
    <form>
        <select id="language" name="language" onchange="submit()">
            <option value="en" ${language == 'en' ? 'selected' : ''}><fmt:message key="locale.label.en"/></option>
            <option value="ru" ${language == 'ru' ? 'selected' : ''}><fmt:message key="locale.label.ru"/></option>
        </select>
    </form>
    <div class="content">
        <jsp:useBean id="wrapper" class="bean.Wrapper"/>

        <table>
            <tr class="tr-h">
                <th class="td1"><fmt:message key="table.label.id"/></th>
                <th><fmt:message key="table.label.fullName"/></th>
                <th><fmt:message key="table.label.year"/></th>
                <th><fmt:message key="table.label.group"/></th>
                <th><fmt:message key="table.label.avarageRating"/></th>
            </tr>
            <c:forEach var="student" items="${wrapper.students}">
                <tr class="tr1">
                    <td class="td1">${student.id}</td>
                    <td>${student.fullName}</td>
                    <td>${student.year}</td>
                    <td>${student.group}</td>
                    <td>${student.avarageRating}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <jsp:include page="/jsp/footer.jsp"/>
</div>

</body>
</html>
