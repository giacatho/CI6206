<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Profile</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/custom.css">
</head>
<body>
<div class="container">
	<!-- Navigator -->
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">SSM</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="#a">My Porfolio</a>
					<li><a href="#about">About</a>
					<li><a href="#contact">Contact</a>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button">Hi, howryou? <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="profile.jsp">My Profile</a>
							<li role="separator" class="divider"></li>
							<li><a href="login.jsp">Logout</a>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="well well-sm page-head">
		<h1>Profile</h1>
	</div>
	
	<!-- Main  -->
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Update Particulars</h2>
		</div>
		<div class="panel-body">
			<form class="form-profile">
				<label for="username" class="control-label">Username</label>
				<input type="text" class="form-control" id="username" placeholder="Username" value="${user.username}" readonly disabled>
				<label for="firstname" class="control-label">First Name</label>
				<input class="form-control" type="text" id="firstname" name="firstname" placeholder="First Name" value="${user.firstname}">
				<label for="lastname" class="control-label">Last Name</label>
				<input class="form-control" type="text" id="lastname" name="lastname" placeholder="Last Name" value="${user.lastname}">
			</form>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Update Password</h2>
		</div>
		<div class="panel-body">
			<form class="form-profile">
				<label for="current-password" class="control-label">Current Password</label>
				<input class="form-control" type="password" id="current-password" name="current-password" placeholder="Current Password">
				<label for="new-password" class="control-label">New Password</label>
				<input class="form-control" type="password" id="new-password" name="new-password" placeholder="New Password">
			</form>
		</div>
	</div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>