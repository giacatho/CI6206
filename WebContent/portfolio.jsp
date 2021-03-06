<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="investWeb.model.Stock" %>
<%@ page import="investWeb.model.Transaction" %>
<%@ page import="java.util.HashMap" %>


<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>

	<div class="well well-sm page-head">
		<h1><%= request.getAttribute("title") %></h1>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Summary</h2>
		</div>
		<div class="panel-body">
			<div class="col-sm-4">
				<h4><span class="label label-primary" data-toggle="tooltip" data-placement="left" title="Available Cash">Cash</span> <fmt:formatNumber value="${sessionScope.User.cashBal}" type="currency"/></h4>
				<h4><span class="label label-primary" data-toggle="tooltip" data-placement="left" title="Total Dividend">Total Dividend</span> <fmt:formatNumber value="${requestScope.DIV}" type="currency"/></h4>
			</div>
			<div class="col-sm-4">
				<h4><span class="label label-primary" data-toggle="tooltip" data-placement="left" title="Total market value of current holding">Shares Value</span> <fmt:formatNumber value="${requestScope.shares}" type="currency"/></h4>
				<h4><span class="label label-primary" data-toggle="tooltip" data-placement="left" title="Simple return over initial investment">Return(%)</span> <fmt:formatNumber value="${(requestScope.shares+sessionScope.User.cashBal-sessionScope.User.initialBalance)/sessionScope.User.initialBalance}" type="percent"  maxFractionDigits="1"/></h4>
			</div>
			<div class="col-sm-4">
				<h4><span class="label label-primary" data-toggle="tooltip" data-placement="left" title="Cash+Shares Value">Total Asset</span> <fmt:formatNumber value="${requestScope.shares+sessionScope.User.cashBal}" type="currency"/></h4> 
				<h4><span class="label label-primary" data-toggle="tooltip" data-placement="left" title="Percentage of return since 01 Jan of the current year.">Year to Date Return(%)</span> <fmt:formatNumber value="${(requestScope.shares+sessionScope.User.cashBal-sessionScope.User.yrStartBal)/sessionScope.User.yrStartBal}" type="percent" maxFractionDigits="1"/></h4>
			</div>
		</div>
	</div>
    
    <div class="panel panel-default">
      <div class="panel-heading">
        <h2 class="panel-title">
          <a data-toggle="collapse" href="#unreal">Unrealised Stock</a>&nbsp;<i class="glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="top" title="Current Holding"></i>
        </h2>
      </div>
      <div id="unreal" class="panel-collapse collapse in panel-contain-table">
		<table class="table table-striped my-table">
			<thead>
				<tr>
					<th>Symbol</th>
					<th>Stock Name</th>
					<th class="ctr">Quantity</th>
					<th class="ctr">Average Unit Cost</th>
					<th class="ctr">Total Cost</th>
					<th class="ctr">Unit Market Price</th>
					<th class="ctr">Profit/Loss</th>
					<th class="ctr">Details</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty requestScope.holdingList}">
						<tr>
							<td class="no-record ctr" colspan="8">You are not holding any stock.</td>
						</tr>
					</c:when>
					<c:when test="${!empty requestScope.holdingList}">
						<c:set var="sum" value="0" scope="page" />						
						<c:forEach var="holding" items="${requestScope.holdingList}">
							<c:if test="${holding.value.qty gt 0}">
								<tr>
									<td><c:out value="${holding.key}" /></td>
									<td><c:out value="${holding.value.stock.name}" /></td>
									<td class="ctr"><c:out value="${holding.value.qty}" /></td>
									<td class="ctr"><fmt:formatNumber value="${holding.value.stock.price}" type="currency"/></td>
									<td class="ctr"><fmt:formatNumber value="${holding.value.amount}" type="currency"/></td>
									<td class="ctr"><fmt:formatNumber value="${holding.value.stock.mktPrice}" type="currency" maxFractionDigits="3"/></td>
									<c:set var="mktVal" value="${(holding.value.qty*holding.value.stock.mktPrice-holding.value.amount)}" scope="page" />
									<td class="ctr"><fmt:formatNumber value="${mktVal}" type="currency"/></td>
									<td class="ctr"><a href="TransactionDet?symbol=${holding.key}">Details</a></td>
									<c:set var="sum" value="${sum+mktVal}" scope="page" />						
								</tr>
						    </c:if>
						</c:forEach>
							    <tr>
							    	<td colspan="6">
							    	<b>Total Profit/Loss</b>
							    	</td>
									<td class="ctr"><fmt:formatNumber value="${sum}" type="currency"/></td>								
									<td></td>
								</tr>
						
					</c:when>
				</c:choose>
			</tbody>
		</table>
		</div>
	</div>
	
    <div class="panel panel-default">
      <div class="panel-heading">
        <h2 class="panel-title">
          <a data-toggle="collapse" href="#real">Realised Stock</a>&nbsp;<i class="glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="top" title="Sold Holding"></i>
        </h2>
      </div>
      <div id="real" class="panel-collapse collapse in panel-contain-table">
		<table class="table table-striped my-table">
			<thead>
				<tr>
					<th>Symbol</th>
					<th>Stock Name</th>
					<th class="ctr">Quantity</th>
					<th class="ctr">Average Unit Cost</th>
					<th class="ctr">Total Cost</th>
					<th class="ctr">Unit Market Price</th>
					<th class="ctr">Profit/Loss</th>
					<th class="ctr">Details</th>
					
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty requestScope.holdingList}">
						<tr>
							<td class="no-record ctr" colspan="8">You are not holding any stock.</td>
						</tr>
					</c:when>
					<c:when test="${!empty requestScope.holdingList}">
						<c:set var="sum" value="0" scope="page" />						
						<c:forEach var="holding" items="${requestScope.holdingList}">
							<c:if test="${holding.value.qty == 0}">
								<tr>
									<td><c:out value="${holding.key}" /></td>
									<td><c:out value="${holding.value.stock.name}" /></td>
									<td class="ctr"><c:out value="${holding.value.qty}" /></td>
									<td class="ctr"><fmt:formatNumber value="${holding.value.stock.price}" type="currency"/></td>
									<td class="ctr"><fmt:formatNumber value="0" type="currency"/></td>
									<td class="ctr"><fmt:formatNumber value="${holding.value.stock.mktPrice}" type="currency" maxFractionDigits="3"/></td>
									<c:set var="mktVal" value="${(holding.value.qty*holding.value.stock.mktPrice-holding.value.amount)}" scope="page" />
									<td class="ctr"><fmt:formatNumber value="${mktVal}" type="currency"/></td>
									<td class="ctr"><a href="TransactionDet?symbol=${holding.key}">Details</a></td>
									<c:set var="sum" value="${sum+mktVal}" scope="page" />						
								</tr>
						    </c:if>
						</c:forEach>
							    <tr>
							    	<td colspan="6">
							    	<b>Total Profit/Loss</b>
							    	</td>
									<td class="ctr"><fmt:formatNumber value="${sum}" type="currency"/></td>								
									<td></td>
								</tr>
						
					</c:when>
				</c:choose>
			</tbody>
		</table>
      </div>
	</div>
</div>
<%@ include file="WEB-INF/includes/footer.jsp" %>
<script>
$(function () {
  $('[data-toggle="tooltip"]').tooltip();
});
</script>
</body>
</html>