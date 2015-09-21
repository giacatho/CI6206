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
			<h2 class="panel-title">Top Ten Ranking</h2>
		</div>
		<div class="panel-body">
			<p>1. Lokenath Mukherjee.</p>
			<p>2. Michael Tan.</p>
			<p>3. Nguyen Tri Tin.</p>
			<p>4. Qiao Guo Jun.</p>
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">News</h2>
		</div>
		<div class="panel-body">
			<h4>This is stock news</h4>
			<h4>This is stock news </h4>
			<h4>This is stock news</h4>
			<h4>This is stock news</h4>
		</div>
	</div>
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>