<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page errorPage="/jsp/error.jsp" %>
<html>
<head>
    <title>First Page</title>
    <link href="css/styles.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<%

    String button = request.getParameter("button1");
    if (button != null) {
        if (button.equals("Start")) {
            request.getRequestDispatcher("/").forward(request, response);
        } else if (button.equals("Pizza")){
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String address = request.getParameter("address");
            if(".".equals(firstName) || ".".equals(lastName)){
                throw new RuntimeException("Wrong Input.");
            }
            if (firstName.length() != 0 && lastName.length() != 0 && address.length()!=0) {
                request.getSession().setAttribute("firstName", firstName);
                request.getSession().setAttribute("lastName", lastName);
                request.getSession().setAttribute("address", address);
                response.sendRedirect(request.getContextPath() + "/2");
            }

        }
    }
%>
    <form name="1"action="/1" method="GET">
        Input:
        <br>First Name:<input type="text" name="firstName" value=${empty sessionScope.firstName ? '.': sessionScope.firstName} />
        <br>Last Name:<input type="text" name="lastName" value=${empty sessionScope.lastName ? '.': sessionScope.lastName} />
        <br>Address:<input type="text" name="address" value=${empty sessionScope.address ? '.': sessionScope.address} />
        <br><br>
        <input class="button"  type="submit" name="button1" value="Start" />
        <input class="button"  type="submit" name="button1" value="Pizza" />
    </form>
</body>
</html>
