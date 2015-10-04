<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>

<!-- Start: Google Dynamic Feed -->
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script src="http://www.google.com/uds/solutions/dynamicfeed/gfdynamicfeedcontrol.js" type="text/javascript"></script>

<style type="text/css">
	@import url("http://www.google.com/uds/solutions/dynamicfeed/gfdynamicfeedcontrol.css");
</style>
<script src="js/rssfeed.js"></script>
<!-- End: Google Dynamic Feed -->

<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>

	<div class="well well-sm page-head">
		<h1><%= request.getAttribute("title") %></h1>
	</div>
	<% if(request.getAttribute("successMessage")!=null) { %>
				<div class="alert alert-success">${successMessage}</div>
	<% } %>
	<div class="row">
		<div class="col-sm-6">
			<div class="panel panel-default ranking">
				<div class="panel-heading">
					<h2 class="panel-title">Top Ten Ranking</h2>
				</div>
				<div class="panel-body">
				
					<table class="table table-hover">
					    <thead>
					      <tr>
					        <th>Rank</th>
					        <th>Name</th>
					        <th>Balance</th>
					      </tr>
					    </thead>
					    <tbody>
					    <c:set var="count" value="0"/>
					    	<c:choose>
					    		<c:when test="${empty requestScope.userDetails}">
									<tr>
										<td class="no-record ctr" colspan="4">No records. Please select a character the stock names start with.</td>
									</tr>
								</c:when>
					    		<c:when test="${!empty requestScope.userDetails}">
									<c:forEach var="user" items="${requestScope.userDetails}">
										<c:set var="count" value="${count + 1}"/>
										<c:set var="balance" value="${user.cashBal + user.sharesVal}"/>  
									      <tr>
									        <td><c:out value="${count}"/></td>
									        <td><c:out value="${user.firstname} ${user.lastname} "/></td>
									         <td><c:out value="${balance} "/></td>
									      </tr>
									</c:forEach>
								</c:when>
							</c:choose>
					    </tbody>		    
					</table>  
				</div>
			</div>
		</div>
		
		<div class="col-sm-6">
			<div class="panel panel-default news">
				<div class="panel-heading">
					<h2 class="panel-title">Stock News</h2>
				</div>
				<div class="panel-body">
					<div id="feedControl">Loading...</div>
				</div>
			</div>
		
		</div>
	</div>
	
	
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>