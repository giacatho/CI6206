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
		<div class="panel-heading">
			<h2 class="panel-title">Update Particulars</h2>
		</div>
		<div class="panel-body">
			<form class="form-horizontal my-form form-profile" action="profile" method="post">
			<% if(request.getAttribute("errorMessage")!=null) { %>
				<div class="alert alert-danger">${errorMessage}</div>
			<% } %>
		    	<c:choose>
		    		<c:when test="${!empty requestScope.userDetails}">
		    			<div class="form-group">
							  <label for="username" class="col-sm-4 control-label required">User Name</label>
							  <div class="col-sm-8">
							    	<input type="text" class="form-control" id="username" placeholder="Username" value="${requestScope.userDetails.username}" readonly disabled>
							  </div>
						</div>
						
						<div class="form-group">
							  <label for="firstname" class="col-sm-4 control-label required">First Name</label>
							  <div class="col-sm-8">
							    	<input class="form-control" type="text" id="firstname" name="firstname" placeholder="First Name" value="${requestScope.userDetails.firstname}" required>
							  </div>
						</div>
						
						<div class="form-group">
							  <label for="lastname" class="col-sm-4 control-label required">Last Name</label>
							  <div class="col-sm-8">
							    	<input class="form-control" type="text" id="lastname" name="lastname" placeholder="Last Name" value="${requestScope.userDetails.lastname}" required>
							  </div>
						</div>
						
						<div class="form-group">
							  <label for="email" class="col-sm-4 control-label required">Email</label>
							  <div class="col-sm-8">
							    	<input class="form-control" type="text" id="email" name="email" placeholder="Email" value="${requestScope.userDetails.email}" required>
							  </div>
						</div>
		
					    <div class="form-group">
							  <label for="current-password" class="col-sm-4 control-label required">Current Password</label>
							  <div class="col-sm-8">
							    	<input class="form-control" type="password" id="current-password" name="current-password" value="${requestScope.userDetails.password}" placeholder="Current Password" readonly>
							  </div>
						</div>  
					      
					    <div class="form-group">
							  <label for="new-password" class="col-sm-4 control-label">New Password</label>
							  <div class="col-sm-8">
							    	<input class="form-control" type="password" id="new-password" name="new-password" placeholder="New Password" title="If you want to update your password, please enter new Password">
							  </div>
						</div>  
					      
					    <div class="form-group">
							  <label for="confirm-password" class="col-sm-4 control-label">Confirm Password</label>
							  <div class="col-sm-8">
							    	<input class="form-control" type="password" id="confirm-password" name="confirm-password" placeholder="Confirm New Password">
							  </div>
						</div>    
					      
					    <div class="form-group">
						    <div class="col-sm-offset-4 col-sm-8">
						      	<button class="btn btn-primary" type="submit" id="updateProfile" name="updateProfile" value="updateProfile">Update Profile</button>
						    </div>
		 				</div>
					      
					</c:when>
				</c:choose>
			</form>
		</div>
	</div>
	
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>