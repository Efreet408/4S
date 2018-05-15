<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@page errorPage="/jsp/error.jsp" %>--%>
<html>
<head>
    <title>Second page</title>
    <link href="css/styles.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<%
    String button = request.getParameter("button2");
    if (button != null) {
        if (button.equals("Name")) {
            request.getRequestDispatcher("/1").forward(request, response);
        } else{
            String getRadio = request.getParameter("r");
            if ("a".equals(getRadio)) {
                request.getSession().setAttribute("pizza", "Peperoni");
                request.getRequestDispatcher("/3").forward(request, response);
            }
            else if("b".equals(getRadio)) {
                request.getSession().setAttribute("pizza", "4 cheeses");
                request.getRequestDispatcher("/3").forward(request, response);
            }
        }
    }
%>
    Choose service
    <form name="2" action="/2" method="GET">
        <input type = "radio" name = "r" value="a" ${sessionScope.pizza != 'Peperoni' ? '' : 'checked'}/> Peperoni
        <input type = "radio" name = "r" value="b" ${sessionScope.pizza != '4 cheeses' ? '' : 'checked'}/> 4 cheeses<br>
        <input class="button" type="submit" name="button2" value="Name" />
        <input class="button" type="submit" name="button2" value="Submit" />
    </form>
</form>
</body>
</html>
