<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<script>
	function deleteUser(userId) {
		bootbox.confirm("Are you confirm to delete this User? ", function(result) {
			if (result) {
				document.location.href="user?action=delete&&userId="+userId;
			}
		})
	}
</script>

<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>

	<div class="well well-sm page-head">
		<h1>User List</h1>
	</div>
	
	<table class="table table-striped my-table">
	    <thead>
	      <tr>
	        <th>User Name</th>
	        <th>First Name</th>
	        <th>Last Name</th>
	        <th>Status</th>
	        <th>Role</th>
	        <th>Action</th>
	      </tr>
	    </thead>
	    <tbody>
	    	<c:if test="${!empty requestScope.message}">
	    		<div class="alert alert-info">
				  <strong><c:out value="${requestScope.message_level}"/></strong> <c:out value="${requestScope.message}"/>
				</div>
	    	</c:if>
    		<c:if test="${empty requestScope.UserList}">
				<tr>
					<td class="no-record ctr" colspan="4">No User.</td>
				</tr>
			</c:if>
    		<c:if test="${!empty requestScope.UserList}">
				<c:forEach var="user" items="${requestScope.UserList}">
			      <tr>
			        <td><c:out value="${user.username}"/></td>
			        <td><c:out value="${user.firstname}"/></td>
			        <td><c:out value="${user.lastname}"/></td>
			        <td><c:out value="${user.statusDesc}"/></td>
			        <td><c:out value="${user.role.roleName}"/></td>
			        <td>
			        	<a href="user?action=to_view&&userId=<c:out value="${user.username}"/>"><span class="label label-primary">View</span></a>
			        	
			        	<c:if test="${( not empty sessionScope.UserPermissionMap) && (not empty sessionScope.UserPermissionMap['EDIT USER'])}">
			        		<a href="user?action=to_update&&userId=<c:out value="${user.username}"/>"><span class="label label-primary">Update</span></a>
			        	</c:if>
			        	
			        	<c:if test="${( not empty sessionScope.UserPermissionMap) && (not empty sessionScope.UserPermissionMap['DELETE USER'])}">
			        		<a onclick="deleteUser('${user.username}')"><span class="label label-primary">Delete</span></a>	
			        	</c:if>		        	
			        </td>
			      </tr>
				</c:forEach>
			</c:if>
	    </tbody>		    
	</table>  
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>