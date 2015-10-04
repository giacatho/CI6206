<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ci6206.model.Stock" %>


<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<script>
	function deleteRole(nbr) {
		bootbox.confirm("Are you confirm to delete this Role? ", function(result) {
			if (result) {
				document.location.href="role?action=delete&&nbr="+nbr;
			}
		})
	}
</script>

<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>

	<div class="well well-sm page-head">
		<h1>Role List</h1>
	</div>
	
	<table class="table table-striped my-table">
	    <thead>
	      <tr>
	        <th>Role Name</th>
	        <th>Role Desc</th>
	        <th>Role Permission</th>
	        <th>Action</th>
	      </tr>
	    </thead>
	    <tbody>
	    	<c:if test="${!empty requestScope.message}">
	    		<div class="alert alert-info">
				  <strong><c:out value="${requestScope.message_level}"/></strong> <c:out value="${requestScope.message}"/>
				</div>
	    	</c:if>
    		<c:if test="${empty requestScope.RoleList}">
				<tr>
					<td class="no-record ctr" colspan="4">No Role.</td>
				</tr>
			</c:if>
    		<c:if test="${!empty requestScope.RoleList}">
				<c:forEach var="role" items="${requestScope.RoleList}">
			      <tr>
			        <td><c:out value="${role.roleName}"/></td>
			        <td><c:out value="${role.roleDesc}"/></td>
			        <td>
			        	<c:forEach items="${role.permissionList}" var="permission" varStatus ="loop"> 
			        		<c:if test="${(loop.index+1) %3 == 0}">
			        			<br/>
			        		</c:if>	
			        		${permission.actionDesc}  ${permission.permissionName},    		
			        	</c:forEach>
			        </td>
			        <td>
			        	<c:if test="${( not empty sessionScope.UserPermissionMap) && (not empty sessionScope.UserPermissionMap['EDIT ROLE'])}">
			        		<a href="role?action=to_update&&nbr=<c:out value="${role.nbr}"/>"><span class="label label-primary">Update</span></a>
			        	</c:if>
			        	
			        	<c:if test="${( not empty sessionScope.UserPermissionMap) && (not empty sessionScope.UserPermissionMap['DELETE ROLE'])}">
			        		<a onclick="deleteRole('${role.nbr}')"><span class="label label-primary">Delete</span></a>	
			        	</c:if>		        	
			        </td>
			      </tr>
				</c:forEach>
			</c:if>
	    </tbody>		    
	</table>  
	
	<c:if test="${( not empty sessionScope.UserPermissionMap) && (not empty sessionScope.UserPermissionMap['CREATE ROLE'])}">
		<a href="role?action=to_create"><span class="label label-primary">Create</span></a>
	</c:if>
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>