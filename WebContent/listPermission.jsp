<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ci6206.model.Stock" %>


<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<script>
	function deletePermission(nbr) {
		bootbox.confirm("Are you confirm to delete this Permission? ", function(result) {
			if (result) {
				document.location.href="permission?action=delete&&nbr="+nbr;
			}
		})
	}
</script>

<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>

	<div class="well well-sm page-head">
		<h1>Permission List</h1>
	</div>
	
	<table class="table table-striped my-table">
	    <thead>
	      <tr>
	        <th>Action Desc</th>
	        <th>Permission Name</th>
	        <th>Permission Desc</th>
	        <th>Action</th>
	      </tr>
	    </thead>
	    <tbody>
	    	<c:if test="${!empty requestScope.message}">
	    		<div class="alert alert-info">
				  <strong><c:out value="${requestScope.message_level}"/></strong> <c:out value="${requestScope.message}"/>
				</div>
	    	</c:if>
    		<c:if test="${empty requestScope.PermissionList}">
				<tr>
					<td class="no-record ctr" colspan="4">No Permission.</td>
				</tr>
			</c:if>
    		<c:if test="${!empty requestScope.PermissionList}">
				<c:forEach var="permission" items="${requestScope.PermissionList}">
			      <tr>
			        <td><c:out value="${permission.actionDesc}"/></td>
			        <td><c:out value="${permission.permissionName}"/></td>
			        <td><c:out value="${permission.permissionDesc}"/></td>
			        <td>
			        	<c:if test="${( not empty sessionScope.UserPermissionMap) && (not empty sessionScope.UserPermissionMap['EDIT PERMISSION'])}">
			        		<a href="permission?action=to_update&&nbr=<c:out value="${permission.nbr}"/>"><span class="label label-primary">Update</span></a>
			        	</c:if>
			        	
			        	<c:if test="${( not empty sessionScope.UserPermissionMap) && (not empty sessionScope.UserPermissionMap['DELETE PERMISSION'])}">
			        		<a onclick="deletePermission('${permission.nbr}')"><span class="label label-primary">Delete</span></a>	
			        	</c:if>		        	
			        </td>
			      </tr>
				</c:forEach>
			</c:if>
	    </tbody>		    
	</table>  
	<c:if test="${( not empty sessionScope.UserPermissionMap) && (not empty sessionScope.UserPermissionMap['CREATE PERMISSION'])}">
		<a href="permission?action=to_create"><span class="label label-primary">Create</span></a>
	</c:if>
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>