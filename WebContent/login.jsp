<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<body>
<div id="main" class="container">
	
	<%@ include file="WEB-INF/includes/main-nav.jsp" %>
	
	<div class="well well-sm page-head">
		<h1><%= request.getAttribute("title") %></h1>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-body">
			<form class="my-form form-signin" action="login" method="post">
				<% if(request.getAttribute("message")!=null) { %>
				<div class="alert alert-danger" role="alert">${ message }</div>
				<% } %>
				
				<label for ="username" class="sr-only">Username</label>
				<input class="form-control" name="username" id="username" type="text" placeholder="Username" required autofocus>
				<label for="password" class="sr-only">Password</label>
				<input class="form-control" name="password" id="password" type="password" placeholder="Password" required>
				<button class="btn btn-primary" type="submit">Login</button>
				
				<a href="register">Register a new account</a>
			</form>
		</div>
	</div>
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</html>