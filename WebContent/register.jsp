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
			<form class="form-horizontal my-form form-register" action="register" method="post">
		
				<% if(request.getAttribute("errorMessage")!=null) { %>
					<div class="alert alert-danger">${errorMessage}</div>
				<% } %>
				
				<div class="form-group">
					  <label for="firstname" class="col-sm-3 control-label required">First Name</label>
					  <div class="col-sm-9">
					    	<input class="form-control" type="text" name="firstname" id="firstname" placeholder="First Name" required maxlength="20">
					  </div>
				</div>
				
				<div class="form-group">
					  <label for="lastname" class="col-sm-3 control-label required">Last Name</label>
					  <div class="col-sm-9">
					    	<input class="form-control" type="text" name="lastname" id="lastname" placeholder="Last Name" required maxlength="20">
					  </div>
				</div>
  	
  				<div class="form-group">
					  <label for="password" class="col-sm-3 control-label required">Password</label>
					  <div class="col-sm-9">
					    	<input class="form-control" type="password" name="password" id="password" placeholder="Password" required maxlength="8">
					  </div>
				</div>
  				
  				<div class="form-group">
					  <label for="email" class="col-sm-3 control-label required">Email</label>
					  <div class="col-sm-9">
					    	<input class="form-control" type="text" name="email" id="email" placeholder="Email" required maxlength="100">
							<input class="form-control" type="hidden" name="status" id="status" value="A">
					  </div>
				</div>
				
				<div class="form-group">
					  <label for="initialBalance" class="col-sm-3 control-label required">Initial Balance</label>
					  <div class="col-sm-9">
					    	<input class="form-control" type="text" name="initialBalance" id="initialBalance" placeholder="100000.00" required maxlength="10">
							<input class="form-control" type="hidden" name="status" id="status" value="A">
					  </div>
				</div>
				
				<div class="form-group">
				    <div class="col-sm-offset-3 col-sm-9">
				      	<button class="btn btn-primary" type="submit">Register</button> <a href="login">Have account? Please login</a>
				    </div>
 				</div>
				
			</form>
		</div>
	</div>
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>