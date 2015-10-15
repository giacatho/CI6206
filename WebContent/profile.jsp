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
		<div class="panel-heading">
			<h2 class="panel-title">Update Particulars</h2>
		</div>
		<div class="panel-body">
			<form class="form-profile" action="profile" method="post">
			<% if(request.getAttribute("errorMessage")!=null) { %>
				<div class="alert alert-danger">${errorMessage}</div>
			<% } %>
				<table class="table table-striped my-table">
				    <tbody>
				    	<c:choose>
				    		<c:when test="${!empty requestScope.userDetails}">
								<h1> Profile Update</h1>
							      <tr>
								      <td class="required">
								      	<label for="username" class="control-label">User Name</label>
								      </td>
								      <td>
								     	 <input type="text" class="form-control" id="username" placeholder="Username" value="${requestScope.userDetails.username}" readonly disabled>
								      </td>
							      </tr>
							      <tr>
								      <td class="required">
								      	<label for="firstname" class="control-label">First Name</label>
								      </td>
								      <td>
								      	<input class="form-control" type="text" id="firstname" name="firstname" placeholder="First Name" value="${requestScope.userDetails.firstname}" required>
								      </td>
							      </tr>
							      <tr>
								      <td class="required">
								      	<label for="lastname" class="control-label">Last Name</label>
								      </td>
								      <td>
								      	<input class="form-control" type="text" id="lastname" name="lastname" placeholder="Last Name" value="${requestScope.userDetails.lastname}" required>
								      </td>
							      </tr>
							      <tr>
								      <td class="required"> 
								      	<label for="lastname" class="control-label">Email</label>
								      </td>
								      <td>
									      <input class="form-control" type="text" id="email" name="email" placeholder="Email" value="${requestScope.userDetails.email}" required>
								      </td>
							      </tr>
							      
							      <tr>
								      <td>
								      	<label for="username" class="control-label">Current Password</label>
								      </td>
								      <td>
								     	 <input class="form-control" type="password" id="current-password" name="current-password" placeholder="Current Password">
								      </td>
							      </tr>
							      <tr>
								      <td>
								      	<label for="firstname" class="control-label">New Password</label>
								      </td>
								      <td>
								      	<input class="form-control" type="password" id="new-password" name="new-password">
								      </td>
							      </tr>
							      <tr>
								      <td>
								      	<label for="lastname" class="control-label">Confirm Your Password</label>
								      </td>
								      <td>
								      	<input class="form-control" type="password" id="confirm-password" name="confirm-password" placeholder="Confirm Password">
								      </td>
							      </tr>
							      
							      
							      
							      <tr>
								      <td>
								      		<button class="btn btn-primary" type="submit" id="updateProfile" name="updateProfile" value="updateProfile">Update Profile</button>
								      </td>
							      </tr>
							</c:when>
						</c:choose>
				    </tbody>		    
				</table> 
				<%-- <br></br>
				<% if(request.getAttribute("passwordErrorMessage")!=null) { %>
					<div class="alert alert-danger">${passwordErrorMessage}</div>
				<% } %>
				<table class="table table-striped my-table">
				    <tbody>
				    	<c:choose>
				    		<c:when test="${!empty requestScope.userDetails}">
									<h1> Password Update</h1>		
							      <tr>
								      <td class="required">
								      	<label for="username" class="control-label">Current Password</label>
								      </td>
								      <td>
								     	 <input class="form-control" type="password" id="current-password" name="current-password" placeholder="Current Password">
								      </td>
							      </tr>
							      <tr>
								      <td class="required">
								      	<label for="firstname" class="control-label">New Password</label>
								      </td>
								      <td>
								      	<input class="form-control" type="password" id="new-password" name="new-password" placeholder="New Password">
								      </td>
							      </tr>
							      <tr>
								      <td class="required">
								      	<label for="lastname" class="control-label">Confirm Your Password</label>
								      </td>
								      <td>
								      	<input class="form-control" type="password" id="confirm-password" name="confirm-password" placeholder="Confirm Password">
								      </td>
							      </tr>
							      
							      <tr>
								      <td>
								      		<button class="btn btn-primary" type="submit" id="changePassword" name="changePassword" value="changePassword">Change Password</button>
								      </td>
							      </tr>
							</c:when>
						</c:choose>
				    </tbody>		    
				</table>  --%>
			</form>
		</div>
	</div>
	
	<!-- <div class="panel panel-default">
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
	</div> -->
	
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>