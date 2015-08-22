<%-- 
    Created on : Aug 20, 2015, 9:02:49 AM
    Author     : nguyentritin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>This is your information</h1>
        
        <c:forEach var="user" items="${users}">
            <b>ID:</b> ${user.id}<br/>
            <b>Username:</b> ${user.username}<br/>
            <b>First Name:</b> ${user.firstname}<br/>
            <b>Last Name:</b> ${user.lastname}<br/>
            <br/><br/>
        </c:forEach>
        
    </body>
</html>
