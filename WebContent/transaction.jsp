<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>

	<div class="well well-sm page-head">
		<h1><%= request.getAttribute("title") %></h1>
	</div>
	
	<table class="table table-striped my-table">
		<thead>
			<tr>
				<th>Type</th>
				<th>Stock Name</th>
				<th class="ctr">Symbol</th>
				<th class="ctr">Quantity</th>
				<th class="ctr">Price</th>
				<th class="ctr">Transaction Date</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty requestScope.subTranList}">
					<tr>
						<td class="no-record ctr" colspan="6">You have no records.</td>
					</tr>
				</c:when>
				<c:when test="${!empty requestScope.subTranList}">
					<c:forEach var="subTran" items="${requestScope.subTranList}">
					<tr>
						<td><c:out value="${subTran.type}" /></td>
						<td><c:out value="${subTran.stockName}" /></td>
						<td class="ctr"><c:out value="${subTran.stockSymbol}" /></td>
						<td class="ctr"><c:out value="${subTran.qty}" /></td>
						<td class="ctr"><c:out value="${subTran.price}" /></td>
						<td class="ctr"><c:out value="${subTran.transactionDate}"/></td>
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