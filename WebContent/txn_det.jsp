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
	
	<table class="table table-striped my-table">
		<thead>
			<tr>
				<th>Action</th>
				<th class="ctr">Symbol</th>
				<th>Stock Name</th>
				<th class="ctr">Date</th>
				<th class="ctr">Quantity</th>
				<th class="ctr">Price</th>
				<th class="ctr">Amount</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty requestScope.holdingList}">
					<tr>
						<td class="no-record ctr" colspan="7">You have no records.</td>
					</tr>
				</c:when>
				<c:when test="${!empty requestScope.holdingList}">
					<c:forEach var="holding" items="${requestScope.holdingList}">
					<tr>
						<td><span class="label <c:out value="${holding.action eq 'buy' ? 'label-success' : holding.action eq 'sell' ? 'label-primary' : 'label-info'}"/> text-capitalize"><c:out value="${holding.action}" /></span></td>
						<td class="ctr"><c:out value="${holding.stock.symbol}" /></td>
						<td><c:out value="${holding.stock.name}" /></td>
						<td class="ctr"><c:out value="${holding.date}" /></td>
						<td class="ctr"><c:out value="${holding.qty}"/></td>
						<td class="ctr"><fmt:formatNumber value="${holding.stock.price}" type="currency"/></td>
						<td class="ctr"><fmt:formatNumber value="${holding.amount}" type="currency"/></td>
					</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>