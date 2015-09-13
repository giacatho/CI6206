<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/custom.css">
</head>
<body>
	<div class="container">
		<form class="form-signin" action="register" method="post">
			<h2>Please register</h2>
			
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
			<button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
			
			<a href="login.jsp">Have account? Please login</a>
		</form>
	</div>
</body>
</html>