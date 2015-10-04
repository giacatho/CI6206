<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>

<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">View User</h2>
		</div>

	    <div>
  	        <span class="label label-warning" id ="warnMessage"><c:out value="${requestScope.message_level}"/><c:out value="${requestScope.message}"/></span>
	    </div>
	    <input type=hidden value="update" name="action"/>
	    
	    <table class="table stock-trade">
		    <tr>
			    <td>User Name:</td>
			    <td>
			        <c:out value="${requestScope.user.username}"/>
			    </td>
		    </tr>

		    <tr>
			    <td>First Name:</td>
			    <td>
			        <c:out value="${requestScope.user.firstname}"/>
			    </td>
		    </tr>
		    
		     <tr>
			    <td>Last Name:</td>
			    <td>
			        <c:out value="${requestScope.user.lastname}"/>
			    </td>
		    </tr>
		    
		    <tr>
			    <td>Email:</td>
			    <td>
			        <c:out value="${requestScope.user.email}"/>
			    </td>
		    </tr>
		    
		     <tr>
			    <td>Initial Balance:</td>
			    <td>
			        <c:out value="${requestScope.user.initialBalance}"/>
			    </td>
		    </tr>
		    
		    <tr>
			    <td>Status</td>
			    <td>
			    	<c:if test="${requestScope.user.status == 'A'}">
			    		Active
			    	</c:if>
			    	
			    	<c:if test="${requestScope.user.status != 'A'}">
			    		Inactive
			    	</c:if>
			    </td>
		    </tr>
		    
		     <tr>
			    <td>Role</td>
			    <td>
			    	<c:if test="${!empty requestScope.user.role}">
			    		<c:out value="${requestScope.user.role.roleName}"/>
			    	</c:if>
			    </td>
		    </tr>
		    
		    <tr>
		        <td>
		        </td>
			    <td>
		    	    <button type="button" class="btn btn-primary" onclick="location.href='user?action=list'" id="cancel">Cancel</button>
			    </td>
		    </tr>
		    
	    </table>
	</div>
</div>
<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>