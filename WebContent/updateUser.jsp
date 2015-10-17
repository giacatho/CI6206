<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<script>
	function submitForm() {
    	var message = "";

    	var firstName = $('#firstName').val().trim();
    	if(firstName=='')
    	{
    		message = message +"The First Name cannot be null. ";
    	}
    	
    	var lastName = $('#lastName').val().trim();
    	if(lastName=='')
    	{
    		message = message +"The Last Name cannot be null. ";
    	}
    	
    	var email = $('#email').val().trim();
    	if(email=='')
    	{
    		message = message +"The Email cannot be null. ";
    	}
    	
    	if (!validateEmail(email)) {
    		message = message +"You have entered an invalid email address! ";
    	}
    	
    	
    	
    	var initialBalance = $('#initialBalance').val().trim();
    	if(initialBalance=='')
    	{
    		message = message +"The Initial Balance cannot be null. ";
    	}
    	
    	if (isNaN(initialBalance)) {
    		message = message +"The Initial Balance should be a number. ";
    	}
    	
    	var password = $('#password').val().trim();
    	if(password=='')
    	{
    		message = message +"The Password cannot be null. ";
    	}
    	
    	var confirmPassword = $('#confirmPassword').val().trim();
    	if(password != confirmPassword)
    	{
    		message = message +"The Confirm Password should be same as Password. ";
    	}
    	
    	if(message!='')
    	{
    		
    		document.getElementById("warnMessage").innerHTML = message;
    	}
    	else
    	{
    		//alert('success');
    		$('#userFrm').submit();
    	}
	}
	
	
	function validateEmail(mail) {
		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
				.test(mail)) {
			return true;
		}
		return false;
	}
</script>



<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Update User</h2>
		</div>
		<form role="form" action="user" method="POST" id="userFrm">
		    <div>
	  	        <span class="label label-warning" id ="warnMessage"><c:out value="${requestScope.message_level}"/><c:out value="${requestScope.message}"/></span>
		    </div>
		    <input type=hidden value="update" name="action"/>
		    
		    <table class="table admin-model">
			    <tr>
				    <td>User Name:</td>
				    <td>
				        <input type="text" class="form-control" id="userId" readonly="readonly" name="userId" 
				        	value="<c:out value="${requestScope.user.username}"/>" />
				    </td>
			    </tr>
	
			    <tr>
				    <td>First Name:</td>
				    <td>
				        <input type="text" class="form-control" id="firstName" name="firstName"
				        	value="<c:out value="${requestScope.user.firstname}"/>"/>
				    </td>
			    </tr>
			    
			     <tr>
				    <td>Last Name:</td>
				    <td>
				        <input type="text" class="form-control" id="lastName" name="lastName"
				        	value="<c:out value="${requestScope.user.lastname}"/>"/>
				    </td>
			    </tr>
			    
			    <tr>
				    <td>Email:</td>
				    <td>
				        <input type="text" class="form-control" id="email" name="email"
				        	value="<c:out value="${requestScope.user.email}"/>"/>
				    </td>
			    </tr>
			    
			     <tr>
				    <td>Initial Balance:</td>
				    <td>
				        <input type="text" class="form-control" id="initialBalance" name="initialBalance"
				        	value="<c:out value="${requestScope.user.initialBalance}"/>" />
				    </td>
			    </tr>
			    
			     <tr>
				    <td>Password:</td>
				    <td>
				        <input type="password" class="form-control" id="password" name="password"
				        	value="<c:out value="${requestScope.user.password}"/>"/>
				    </td>
			    </tr>
			    
			    <tr>
				    <td>Confirm Password:</td>
				    <td>
				        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
				        	value="<c:out value="${requestScope.user.password}"/>"/>
				    </td>
			    </tr>
			    
			    <tr>
				    <td>Status</td>
				    <td>
					    	<c:if test="${requestScope.user.status == 'A'}">
					    		<label class="radio-inline"><input type="radio" name="status" value="A" checked>Active</label>
	  	 						<label class="radio-inline"><input type="radio" name="status" value="I">Inactive</label>
					    	</c:if>
					    	
					    	<c:if test="${requestScope.user.status != 'A'}">
					    		<label class="radio-inline"><input type="radio" name="status" value="A">Active</label>
	  	 						<label class="radio-inline"><input type="radio" name="status" value="I" checked>Inactive</label>
					    	</c:if>
				    </td>
			    </tr>
			    
			     <tr>
				    <td>Role</td>
				    <td>
				        <select name="roleId" class="form-control">
						   <c:forEach var="role" items="${requestScope.RoleList}" >
						   		<c:if test="${role.selected == true}">
						   		 	<option value="${role.nbr}" selected="selected">${role.roleName}</option>
						   		</c:if>
						   		
						   		<c:if test="${role.selected != true }">
						   		 	<option value="${role.nbr}">${role.roleName}</option>
						   		</c:if>
                           </c:forEach>

						</select> 
				    </td>
			    </tr>
			    
			    <tr>
			        <td>
			        </td>
				    <td>
			    	    <button type="button" class="btn btn-primary" onclick="submitForm()">Confirm</button>
			    	    <button type="button" class="btn btn-primary" onclick="location.href='user?action=list'" id="cancel">Cancel</button>
				    </td>
			    </tr>
			    
		    </table>
		</form>
	</div>
</div>
<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>