<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<script>
	function submitForm() {
    	var message = "";

    	var actionDesc = $('#actionDesc').val().trim();
    	if(actionDesc=='')
    	{
    		message = message +"The Action Desc cannot be null. ";
    	}
    	
    	var permissionName = $('#permissionName').val().trim();
    	if(permissionName=='')
    	{
    		message = message +"The Permission Name cannot be null.";
    	}
    	
    	if(message!='')
    	{
    		
    		document.getElementById("warnMessage").innerHTML = message;
    	}
    	else
    	{
    		//alert('success');
    		$('#permissionFrm').submit();
    	}
	}
</script>



<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Update Permission</h2>
		</div>
		<form role="form" action="permission" method="POST" id="permissionFrm">
		    <div>
	  	        <span class="label label-warning" id ="warnMessage"><c:out value="${requestScope.message_level}"/><c:out value="${requestScope.message}"/></span>
		    </div>
		    <input type=hidden value="update" name="action"/>
		    <input type=hidden value="<c:out value="${requestScope.permission.nbr}"/>" name="nbr"/>
		    
		    <table class="table admin-model">
			    <tr>
				    <td>Action Desc:</td>
				    <td>
				        <input type="text" class="form-control" id="actionDesc" name="actionDesc" 
				        	value="<c:out value="${requestScope.permission.actionDesc}"/>" />
				    </td>
			    </tr>
	
			    <tr>
				    <td>Permission Name:</td>
				    <td>
				        <input type="text" class="form-control" id="permissionName" name="permissionName"
				        	value="<c:out value="${requestScope.permission.permissionName}"/>"/>
				    </td>
			    </tr>
			    
			    <tr>
				    <td>Permission Desc:</td>
				    <td>
				        <input type="text" class="form-control" id="permissionDesc" name="permissionDesc"
				        	value="<c:out value="${requestScope.permission.permissionDesc}"/>"/>
				    </td>
			    </tr>
			    
			    <tr>
			        <td>
			        </td>
				    <td>
			    	    <button type="button" class="btn btn-primary" onclick="submitForm()">Confirm</button>
			    	    <button type="button" class="btn btn-primary" onclick="location.href='permission?action=list'" id="cancel">Cancel</button>
				    </td>
			    </tr>
			    
		    </table>
		</form>
	</div>
</div>
<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>