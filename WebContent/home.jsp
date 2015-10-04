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
							<tr>
								<td>1</td>
								<td>Michael Tan</td>
								<td>900,000</td>
							</tr>
							<tr>
								<td>2</td>
								<td>Nguyen Tri Tin</td>
								<td>800,000</td>
							</tr>
							<tr>
								<td>3</td>
								<td>Lokenath Mukherjee</td>
								<td>700,000</td>
							</tr>
							<tr>
								<td>4</td>
								<td>Qiao Guo Jun.</td>
								<td>600,000</td>
							</tr>
							<tr>
								<td>5</td>
								<td>Michael Tan</td>
								<td>900,000</td>
							</tr>
							<tr>
								<td>6</td>
								<td>Nguyen Tri Tin</td>
								<td>800,000</td>
							</tr>
							<tr>
								<td>7</td>
								<td>Lokenath Mukherjee</td>
								<td>700,000</td>
							</tr>
							<tr>
								<td>8</td>
								<td>Qiao Guo Jun.</td>
								<td>600,000</td>
							</tr>
							<tr>
								<td>9</td>
								<td>Michael Tan</td>
								<td>900,000</td>
							</tr>
							<tr>
								<td>10</td>
								<td>Nguyen Tri Tin</td>
								<td>800,000</td>
							</tr>
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