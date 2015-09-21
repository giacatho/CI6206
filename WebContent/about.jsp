<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>

	<div class="well well-sm page-head">
		<h1><%= request.getAttribute("title") %></h1>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Product</h2>
		</div>
		<div class="panel-body">
			<p>This is our assignment two of CI6202 semester 1, academic year 2015 in NTU.</p>
			<p>This project is a stock simulator.</p>
			<p>Features:</p>
			<ul>
				<li>Register new users.</li>
				<li>Buy/sell stocks.</li>
				<li>View porfolios.</li>
			</ul>
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Team</h2>
		</div>
		<div class="panel-body">
			<p>Lokenath Mukherjee.</p>
			<p>Michael Tan.</p>
			<p>Nguyen Tri Tin.</p>
			<p>Qiao Guo Jun.</p>
		</div>
	</div>
	
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>