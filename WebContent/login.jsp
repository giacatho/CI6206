<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link href="style.css" rel="stylesheet" type="text/css">
<style type="text/css">
/* Home page */
.login_home{
	border:double;
	width:300px;
	height:250px;
	margin:-270px 0px 0px 650px ;
	padding:10px;
}

.login_input{
	margin-left:20px;
	font-size:15px;
	}
	
.btn_blu {
	font:18px;	
	height:30px;
	color:#fff;	
	width:auto;	
	background:#29A3CC;
	padding:0px 10px;
	margin-top:5px;
}

</style> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>

<body>

<div class="content-body">
	<div> 
	
		<!-- Login Section and Picture -->
		<div>
			<img src="images/login.jpg" width="642" height="693">
		</div>
		
 		<!-- Login Section -->
 		
		<div id="login" class="login login_home">
			
			<form name="login" id="login" action="login"  method="POST">
				<h4>LOGIN</h4>
				<label class="login_input">User Name</label>
				<input type="text" name="username" id="username" class="login_input" maxlength="50"/>
				<label class="login_input">Password</label>
				<input type="password" name="password" id="password" value="" class="login_input" maxlength="24"/>
			
				<input class="btn_blu login_input" id="login2" name="login2" type="submit" value="Login" /> 
				<span><a href="register.jsp">Register a new user</a></span>
			
			</form>
		</div>
	</div>
</div>


</body>

<%-- 
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

</body> --%>
</html>