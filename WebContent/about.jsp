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
			<div class="col-sm-6">
				<h3>Introduction</h3>
				<p>This is our assignment two of CI6202, semester one of academic year 2015 in NTU.</p>
				<p>This project is a stock simulator. We give you fake cash to invest in real Singapore companies under real market conditions. The trades will bring you the most authentic experience on the web.</p>
				<p>&nbsp;</p>
				
				<h3>Features</h3>
				<ul>
					<li>Register new users.</li>
					<li>Search stocks using stock name or stock symbol.</li>
					<li>Trade (buy/sell) stocks.</li>
					<li>View your Portfolio.</li>
					<li>View your Transaction History.</li>
					<li>Search for users and reset their passwords (admin only).</li>
				</ul>
			</div>
			<div class="col-sm-6">
				<img alt="Stocks" src="images/stocks.jpg" style="width: 100%; height: 100%;">
			</div>
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Team</h2>
		</div>
		<div class="panel-body">
			<div class="col-sm-3 ctr">
				<h4>Michael Tan</h4>
				<p>G1502589E</p>
				<img alt="Michael" src="images/sg.jpg" style="width:50%;height:50%">
			</div>
			<div class="col-sm-3 ctr">
				<h4>Nguyen Tri Tin</h4>
				<p>G1500675D</p>
				<img alt="Tin" src="images/vn.jpg" style="width:50%;height:50%">
			</div>
			<div class="col-sm-3 ctr">
				<h4>Qiao Guo Jun</h4>
				<p>G1500681C</p>
				<img alt="Guo Jun" src="images/cn.jpg" style="width:50%;height:50%">
			</div>
			<div class="col-sm-3 ctr">
				<h4>Lokenath Mukherjee</h4>
				<p>G1500673K</p>
				<img alt="Loke" src="images/in.jpg" style="width:50%;height:50%">
			</div>
		</div>
	</div>
	
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>