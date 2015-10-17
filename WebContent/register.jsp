<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<style>
   .required > label:after {
	content: '*';
	color: #f00;
	margin-right: 3px;
	margin-left: 1px;
	}
</style>
<body>
<div id="main" class="container">
	
	<%@ include file="WEB-INF/includes/main-nav.jsp" %>
	
	<div class="well well-sm page-head">
		<h1><%= request.getAttribute("title") %></h1>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-body">
			<form class="form-signin" action="register" method="post">
		
				<% if(request.getAttribute("errorMessage")!=null) { %>
					<div class="alert alert-danger">${errorMessage}</div>
				<% } %>
				
				<table class="table table-striped my-table">
					<tr>
						<td nowrap="nowrap" class="required">
							<label for="username">User Name</label>
						</td>
						<td>
							<input class="form-control" type="text" name="username" id="username" placeholder="Username" required autofocus maxlength="25">
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="required">
							<label for="firstname">First Name</label>
						</td>
						<td>
							<input class="form-control" type="text" name="firstname" id="firstname" placeholder="First Name" required maxlength="20">
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="required">
							<label for="lastname">Last Name</label>
						</td>
						<td>
							<input class="form-control" type="text" name="lastname" id="lastname" placeholder="Last Name" required maxlength="20">
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="required">
							<label for="password">Password</label>
						</td>
						<td>
							<input class="form-control" type="password" name="password" id="password" placeholder="Password" required maxlength="8">
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="required">
							<label for="lastname">Email</label>
						</td>
						<td>
							<input class="form-control" type="text" name="email" id="email" placeholder="Email" required maxlength="100">
							<input class="form-control" type="hidden" name="status" id="status" value="A">
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="required">
							<label for="lastname">Initial Balance</label>
						</td>
						<td>
							<input class="form-control" type="text" name="initialBalance" id="initialBalance" placeholder="100000.00" required maxlength="10">
						</td>
					</tr>
					<tr>
						<td>
							<button class="btn btn-primary" type="submit">Register</button>
						</td>
					</tr>
				</table>
				
				<a href="login">Have account? Please login</a>
			</form>
		</div>
	</div>
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>