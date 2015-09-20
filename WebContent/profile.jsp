<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<body>
<div class="container">
	
	<%@ include file="WEB-INF/includes/main-nav.jsp" %>

	<div class="well well-sm page-head">
		<h1><%= request.getAttribute("title") %></h1>
	</div>
	
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
				<label for="confirm-password" class="control-label">Confirm Password</label>
				<input class="form-control" type="password" id="confirm-password" name="confirm-password" placeholder="Confirm Password">
			</form>
		</div>
	</div>
	
</div>
</body>
</html>