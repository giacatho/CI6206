<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ci6206.model.Stock" %>


<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>

	<div class="well well-sm page-head">
		<h1>Trading</h1>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Select Stocks</h2>
		</div>
		<div class="panel-body">
			<div>
				<span class="label label-info">By Name</span>			
				<a href="trading?srch=A&type=name"><span class="badge">A</span></a>
				<a href="trading?srch=B&type=name"><span class="badge">B</span></a>		
				<a href="trading?srch=C&type=name"><span class="badge">C</span></a>		
				<a href="trading?srch=D&type=name"><span class="badge">D</span></a>
				<a href="trading?srch=E&type=name"><span class="badge">E</span></a>
				<a href="trading?srch=F&type=name"><span class="badge">F</span></a>
				<a href="trading?srch=G&type=name"><span class="badge">G</span></a>
				<a href="trading?srch=H&type=name"><span class="badge">H</span></a>
				<a href="trading?srch=I&type=name"><span class="badge">I</span></a>
				<a href="trading?srch=J&type=name"><span class="badge">J</span></a>
				<a href="trading?srch=K&type=name"><span class="badge">K</span></a>
				<a href="trading?srch=L&type=name"><span class="badge">L</span></a>
				<a href="trading?srch=M&type=name"><span class="badge">N</span></a>
				<a href="trading?srch=O&type=name"><span class="badge">O</span></a>
				<a href="trading?srch=P&type=name"><span class="badge">P</span></a>
				<a href="trading?srch=Q&type=name"><span class="badge">Q</span></a>
				<a href="trading?srch=R&type=name"><span class="badge">R</span></a>
				<a href="trading?srch=S&type=name"><span class="badge">S</span></a>
				<a href="trading?srch=T&type=name"><span class="badge">T</span></a>
				<a href="trading?srch=U&type=name"><span class="badge">U</span></a>
				<a href="trading?srch=V&type=name"><span class="badge">V</span></a>
				<a href="trading?srch=W&type=name"><span class="badge">W</span></a>
				<a href="trading?srch=X&type=name"><span class="badge">X</span></a>
				<a href="trading?srch=Y&type=name"><span class="badge">Y</span></a>
				<a href="trading?srch=Z&type=name"><span class="badge">Z</span></a>
			</div>
			<div style="margin-top: 10px;">
				<span class="label label-info">By Symbol</span>			
				<a href="trading?srch=A&type=symbol"><span class="badge">A</span></a>
				<a href="trading?srch=B&type=symbol"><span class="badge">B</span></a>		
				<a href="trading?srch=C&type=symbol"><span class="badge">C</span></a>		
				<a href="trading?srch=D&type=symbol"><span class="badge">D</span></a>
				<a href="trading?srch=E&type=symbol"><span class="badge">E</span></a>
				<a href="trading?srch=F&type=symbol"><span class="badge">F</span></a>
				<a href="trading?srch=G&type=symbol"><span class="badge">G</span></a>
				<a href="trading?srch=H&type=symbol"><span class="badge">H</span></a>
				<a href="trading?srch=I&type=symbol"><span class="badge">I</span></a>
				<a href="trading?srch=J&type=symbol"><span class="badge">J</span></a>
				<a href="trading?srch=K&type=symbol"><span class="badge">K</span></a>
				<a href="trading?srch=L&type=symbol"><span class="badge">L</span></a>
				<a href="trading?srch=M&type=symbol"><span class="badge">N</span></a>
				<a href="trading?srch=O&type=symbol"><span class="badge">O</span></a>
				<a href="trading?srch=P&type=symbol"><span class="badge">P</span></a>
				<a href="trading?srch=Q&type=symbol"><span class="badge">Q</span></a>
				<a href="trading?srch=R&type=symbol"><span class="badge">R</span></a>
				<a href="trading?srch=S&type=symbol"><span class="badge">S</span></a>
				<a href="trading?srch=T&type=symbol"><span class="badge">T</span></a>
				<a href="trading?srch=U&type=symbol"><span class="badge">U</span></a>
				<a href="trading?srch=V&type=symbol"><span class="badge">V</span></a>
				<a href="trading?srch=W&type=symbol"><span class="badge">W</span></a>
				<a href="trading?srch=X&type=symbol"><span class="badge">X</span></a>
				<a href="trading?srch=Y&type=symbol"><span class="badge">Y</span></a>
				<a href="trading?srch=Z&type=symbol"><span class="badge">Z</span></a>
			</div>
		</div>
	</div>
	
	<table class="table table-striped my-table">
	    <thead>
	      <tr>
	        <th>Stock Name</th>
	        <th>Symbol</th>
	        <th>Price</th>
	        <th>Action</th>
	      </tr>
	    </thead>
	    <tbody>
	    	<c:choose>
	    		<c:when test="${empty requestScope.StockList}">
					<tr>
						<td class="no-record ctr" colspan="4">No records. Please select a character the stock names start with.</td>
					</tr>
				</c:when>
	    		<c:when test="${!empty requestScope.StockList}">
					<c:forEach var="stock" items="${requestScope.StockList}">
				      <tr>
				        <td><c:out value="${stock.name}"/></td>
				        <td><c:out value="${stock.symbol}"/></td>
				        <td><c:out value="${stock.price}"/></td>
				        <td>
							<a href="trading?symbol=<c:out value="${stock.symbol}"/> "><span class="label label-primary">Trade</span></a>			        	
				        </td>
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