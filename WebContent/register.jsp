<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<link href="style.css" rel="stylesheet" type="text/css">
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
	<div style="text-align: left;">
		<input id="backbtn" value="Back" class="bt-mid btn_blu" onClick="javascript: history.go(-1)" type="button">
		<input type="submit" class="bt-mid btn_blu"  value="Register">
	</div>
	
	${ message }
</form>

</body>
</html>