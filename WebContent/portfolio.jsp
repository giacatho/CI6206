<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>

	<div class="well well-sm page-head">
		<h1><%= request.getAttribute("title") %></h1>
		
		<table class="table">
		  <tr>
		     <td>
        		<span class="label label-info">Cash: </span>
        		<fmt:formatNumber value="${sessionScope.User.cashBal}" type="currency"/>
		     </td>
		     <td>
        		<span class="label label-info">Shares Value: </span>
        		<fmt:formatNumber value="${requestScope.shares}" type="currency"/>
		     </td>
		     
		     
		  </tr>
		</table>
	</div>
	
	<table class="table table-striped my-table">
		<thead>
			<tr>
				<th>Symbol</th>
				<th>Stock Name</th>
				<th class="ctr">Quantity</th>
				<th class="ctr">Market Price</th>
				<th class="ctr">Market Amount</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty requestScope.holdingList}">
					<tr>
						<td class="no-record ctr" colspan="5">You have no records.</td>
					</tr>
				</c:when>
				<c:when test="${!empty requestScope.holdingList}">
					<c:forEach var="holding" items="${requestScope.holdingList}">
					<tr>
						<td><c:out value="${holding.stockSymbol}" /></td>
						<td><c:out value="${holding.stockName}" /></td>
						<td class="ctr"><c:out value="${holding.qty}" /></td>
						<td class="ctr"><c:out value="${holding.marketPrice}" /></td>
						<td class="ctr"><c:out value="${holding.marketValue}"/></td>
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