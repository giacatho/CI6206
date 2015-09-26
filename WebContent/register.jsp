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
			<form class="form-signin" action="register" method="post">
		
				<% if(request.getAttribute("message")!=null) { %>
				<div class="alert alert-danger"><%= request.getAttribute("message") %></div>
				<% } %>
				
				<label class="sr-only" for="username">Username</label>
				<input class="form-control" type="text" name="username" id="username" placeholder="Username" required autofocus>
				<label class="sr-only" for="password">Password</label>
				<input class="form-control" type="password" name="password" id="password" placeholder="Password" required>
				<label class="sr-only" for="firstname">First Name</label>
				<input class="form-control" type="text" name="firstname" id="firstname" placeholder="First Name">
				<label class="sr-only" for="lastname">Last Name</label>
				<input class="form-control" type="text" name="lastname" id="lastname" placeholder="Last Name">
				<button class="btn btn-primary" type="submit">Register</button>
				
				<a href="login">Have account? Please login</a>
			</form>
		</div>
	</div>
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>