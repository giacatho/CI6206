<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="ci6206.model.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="home">SSM</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
				<% if(session!=null && session.getAttribute(Constants.USER_ATTR)!=null) { %>
					<li><a href="portfolio">My Portfolio</a></li>
					<li><a href="trading">Trading</a></li>
					
					<!-- Display Admin menu if current user has View Admin access right. -->
					<c:if test="${( not empty sessionScope.UserPermissionMap) && (not empty sessionScope.UserPermissionMap['VIEW ADMIN'])}">
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="#">Admin <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<c:if test="${not empty sessionScope.UserPermissionMap['LIST USER']}">
									<li><a href="user?action=list">User</a></li>
								</c:if>
								
								<c:if test="${not empty sessionScope.UserPermissionMap['LIST ROLE']}">
									<li><a href="role?action=list">Role</a></li>
								</c:if>
								
								<c:if test="${not empty sessionScope.UserPermissionMap['LIST PERMISSION']}">
									<li><a href="permission?action=list">Permission</a></li>
								</c:if>
							</ul>
						</li>
					</c:if>
					
				<% } else { %>
					<li><a href="home">Home</a></li>
				<% } %>
					<li><a href="about">About</a></li>
					
				</ul>
				<ul class="nav navbar-nav navbar-right">
				<% if(session!=null && session.getAttribute(Constants.USER_ATTR)!=null) { %>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button">Hi, howryou? <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="profile">My Profile</a>
							<li role="separator" class="divider"></li>
							<li><a href="logout">Logout</a>
						</ul>
					</li>
				<% } else { %>
					<li><a href="login">Login</a></li>
					<li><a href="register">Registration</a></li>
				<% } %>
				</ul>
			</div>
		</div>
	</nav>