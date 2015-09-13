<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/custom.css">
</head>
<body>
	<div class="container">
		<form class="form-signin" action="login" method="post">
			<h2 class="form-signin-heading">Please login</h2>
			
			<% if(request.getAttribute("message")!=null) { %>
			<div class="alert alert-danger" role="alert">${ message }</div>
			<% } %>
			
			<label for ="username" class="sr-only">Username</label>
			<input class="form-control" name="username" id="username" type="text" placeholder="Username" required autofocus>
			<label for="password" class="sr-only">Password</label>
			<input class="form-control" name="password" id="password" type="password" placeholder="Password" required>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
			
			<a href="register.jsp">Register a new user</a>
		</form>
		
		
	</div>


</body>
</html>