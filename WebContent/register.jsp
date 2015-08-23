<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration</title>
</head>
<body>
<h2>Please input your registration form</h2>
<form action="register" method="post">
	<b>Username: </b><input name="username" type="text" placeholder="Username" /><br/><br/>
	<b>Password: </b><input name="password" type="password" placeholder="Password" /><br/><br/>
	<b>First Name: </b><input name="firstname" type="text" placeholder="First Name" /><br/><br/>
	<b>Last Name: </b><input name="lastname" type="text" placeholder="Last Name" /><br/><br/>
	<input type="submit" value="Register"><br/>
	${ message }
</form>

</body>
</html>