<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="ci6206.model.Stock" %>
<%@ page import="ci6206.model.Transaction" %>
<%@ page import="java.util.HashMap" %>


<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>

	<div class="well well-sm page-head">
		<h1><%= request.getAttribute("title") %></h1>
		<h3>
		<table class="table">
		  <tr>
		     <td>
        		<span class="label label-info">Cash: </span>
        	 </td>
        	 <td>	
        		<fmt:formatNumber value="${sessionScope.User.cashBal}" type="currency"/>
		     </td>
		     <td>
        		<span class="label label-info">Inception Date: </span>
        	 </td>
        	 <td>	
        		<c:out value="${sessionScope.User.inception}"/>
		     </td>
		     
		  </tr>
		  <tr>   
		     <td>
        		<span class="label label-info">Shares Value: </span>
        	</td>
        	<td>
        		<fmt:formatNumber value="${requestScope.shares}" type="currency"/>
		    </td>
		     <td>
        		<span class="label label-info">Annualised Return(%): </span>
        	</td>
        	<td>
        		<fmt:formatNumber value="${(requestScope.shares+sessionScope.User.cashBal-100000)/100000}" type="percent"/>
		    </td>
		    
		  </tr>
		  <tr>   
		     <td>
        		<span class="label label-info">Total Asset: </span>
        	</td>
        	<td>
        		<fmt:formatNumber value="${requestScope.shares+sessionScope.User.cashBal}" type="currency"/>
		    </td>
		     <td>
        		<span class="label label-info">Year to Date Return(%): </span>
        	</td>
        	<td>
        		<fmt:formatNumber value="${(requestScope.shares+sessionScope.User.cashBal-sessionScope.User.yrStartBal)/sessionScope.User.yrStartBal}" type="percent"/>
		    </td>
		    
		  </tr>
		  
		</table>
		</h3>
	</div>
	
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
						<td class="no-record ctr" colspan="5">You have no records.</td>
					</tr>
				</c:when>
				<c:when test="${!empty requestScope.holdingList}">
					<c:forEach var="holding" items="${requestScope.holdingList}">
					<tr>
						<td><c:out value="${holding.key}" /></td>
						<td><c:out value="${holding.value.stock.name}" /></td>
						<td class="ctr"><c:out value="${holding.value.qty}" /></td>
						<td class="ctr"><fmt:formatNumber value="${holding.value.stock.price}" type="currency"/></td>
						<td class="ctr"><fmt:formatNumber value="${holding.value.amount}" type="currency"/></td>
						<td class="ctr"><fmt:formatNumber value="${holding.value.stock.mktPrice}" type="currency"/></td>
						<c:set var="mktVal" value="${(holding.value.qty*holding.value.stock.mktPrice-holding.value.amount)}" scope="page" />
						<td class="ctr"><fmt:formatNumber value="${mktVal}" type="currency"/></td>
						<td class="ctr"><a href="TransactionDet?symbol=${holding.key}">Details</a></td>
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