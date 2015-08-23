<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
<h2>Please enter your credentials</h2>
<form action="login" method="POST">
	<b>Username: </b><input name="username" type="text" /><br/><br/>
	<b>Password: </b><input name="password" type="password" /><br/>
	<input type="submit" value = "Login" /><br/>
	
	${ message }
</form>

<br/><br/>
<a href="register.jsp">Register a new user</a>

</body>
</html>