<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>

<body>
<div id="main" class="container">
	
	<%@ include file="WEB-INF/includes/main-nav.jsp" %>
	
	<div class="well well-sm page-head">
		<% if(request.getAttribute("successMessage")!=null) { %>
				<div class="alert alert-success">${successMessage}</div>
		<% } %>
		<a style="padding-right: 10px;" href="login">Please proceed to login</a>
	</div>
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>