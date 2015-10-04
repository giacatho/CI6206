<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<script>
function submitForm() {
	var message = "";

	var roleName = $('#roleName').val();
	if(roleName=='')
	{
		message = message +"The Role Name cannot be null. ";
	}

	var selected = getSelectedChbox();
	if (selected.length <= 0) {
		message = message +"At least select one Permission. ";
	}

	if(message!='')
	{
		
		document.getElementById("warnMessage").innerHTML = message;
	}
	else
	{
		//alert('success');
		$('#roleFrm').submit();
	}
}

// get selected checkboxes
function getSelectedChbox() {
	var selchbox = [];// array that will store the value of selected checkboxes
	// gets all the input tags in frm, and their number
	var inpfields = document.getElementsByName('permissionCxb');
	var nr_inpfields = inpfields.length;
	// traverse the inpfields elements, and adds the value of selected (checked) checkbox in selchbox
	for (var i = 0; i < nr_inpfields; i++) {
		if (inpfields[i].type == 'checkbox' && inpfields[i].checked == true)
			selchbox.push(inpfields[i].value);
	}

	return selchbox;
}
</script>



<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Update Role</h2>
		</div>
		<form role="form" action="role" method="POST" id="roleFrm">
		    <div>
	  	        <span class="label label-warning" id ="warnMessage"><c:out value="${requestScope.message_level}"/><c:out value="${requestScope.message}"/></span>
		    </div>
		    
		    <input type=hidden value="update" name="action"/>
		    <input type=hidden value="<c:out value="${requestScope.role.nbr}"/>" name="nbr"/>
		    
		    <table class="table stock-trade">
			    <tr>
				    <td>Role Name:</td>
				    <td>
				        <input type="text" class="form-control" id="roleName" name="roleName" 
				        	value="<c:out value="${requestScope.role.roleName}"/>" />
				    </td>
			    </tr>
	
			    <tr>
				    <td>Role Desc:</td>
				    <td>
				        <input type="text" class="form-control" id="roleDesc" name="roleDesc"
				        	value="<c:out value="${requestScope.role.roleDesc}"/>"/>
				    </td>
			    </tr>
			    
			    <tr>
				    <td>Role Permission:</td>
				    <td>
				        <c:forEach items="${PermissionList}" var="permission" varStatus ="loop"> 
			        		<c:if test="${(loop.index+1) %3 == 0}">
			        			<br/>
			        		</c:if>	
			        		<c:if test="${permission.checked == true}"> 
			        			<input type="checkbox" value="${permission.nbr }" name="permissionCxb" checked> 
			        			${permission.actionDesc}  ${permission.permissionName}   
			        		</c:if> 
			        		<c:if test="${permission.checked != true}"> 
			        			<input type="checkbox" value="${permission.nbr }" name="permissionCxb"> 
			        			${permission.actionDesc}  ${permission.permissionName}   
			        		</c:if> 
			        		 		
			        	</c:forEach>
				    </td>
			    </tr>
			    
			    <tr>
			        <td>
			        </td>
				    <td>
			    	    <button type="button" class="btn btn-primary" onclick="submitForm()">Confirm</button>
			    	    <button type="button" class="btn btn-primary" onclick="location.href='role?action=list'" id="cancel">Cancel</button>
				    </td>
			    </tr>
			    
		    </table>
		</form>
	</div>
</div>
<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>