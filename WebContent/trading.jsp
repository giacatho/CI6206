<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ci6206.model.Stock" %>


<!DOCTYPE html>
<html>
<script>
function getStock(action)
{
	var value =  "trading?action="+action+"&srch="+document.getElementById("srch").value;
	window.location.href = value;
	//alert(value);
}
</script>

<%@ include file="WEB-INF/includes/head.jsp" %>
<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>

	<div class="well well-sm page-head">
		<h1>Trading</h1>
	</div>
	
	<div class="panel panel-default">
	
		<div class="panel-heading">
			<h2 class="panel-title">Retrieve Stocks</h2>
		</div>
		<div class="panel-body">
			<div>
				<label>Select By Alphabet&nbsp;</label>			
				<a href="trading?srch=A&action=${requestScope.action}"><span class="badge">A</span></a>
				<a href="trading?srch=B&action=${requestScope.action}"><span class="badge">B</span></a>		
				<a href="trading?srch=C&action=${requestScope.action}"><span class="badge">C</span></a>		
				<a href="trading?srch=D&action=${requestScope.action}"><span class="badge">D</span></a>
				<a href="trading?srch=E&action=${requestScope.action}"><span class="badge">E</span></a>
				<a href="trading?srch=F&action=${requestScope.action}"><span class="badge">F</span></a>
				<a href="trading?srch=G&action=${requestScope.action}"><span class="badge">G</span></a>
				<a href="trading?srch=H&action=${requestScope.action}"><span class="badge">H</span></a>
				<a href="trading?srch=I&action=${requestScope.action}"><span class="badge">I</span></a>
				<a href="trading?srch=J&action=${requestScope.action}"><span class="badge">J</span></a>
				<a href="trading?srch=K&action=${requestScope.action}"><span class="badge">K</span></a>
				<a href="trading?srch=L&action=${requestScope.action}"><span class="badge">L</span></a>
				<a href="trading?srch=M&action=${requestScope.action}"><span class="badge">N</span></a>
				<a href="trading?srch=O&action=${requestScope.action}"><span class="badge">O</span></a>
				<a href="trading?srch=P&action=${requestScope.action}"><span class="badge">P</span></a>
				<a href="trading?srch=Q&action=${requestScope.action}"><span class="badge">Q</span></a>
				<a href="trading?srch=R&action=${requestScope.action}"><span class="badge">R</span></a>
				<a href="trading?srch=S&action=${requestScope.action}"><span class="badge">S</span></a>
				<a href="trading?srch=T&action=${requestScope.action}"><span class="badge">T</span></a>
				<a href="trading?srch=U&action=${requestScope.action}"><span class="badge">U</span></a>
				<a href="trading?srch=V&action=${requestScope.action}"><span class="badge">V</span></a>
				<a href="trading?srch=W&action=${requestScope.action}"><span class="badge">W</span></a>
				<a href="trading?srch=X&action=${requestScope.action}"><span class="badge">X</span></a>
				<a href="trading?srch=Y&action=${requestScope.action}"><span class="badge">Y</span></a>
				<a href="trading?srch=Z&action=${requestScope.action}"><span class="badge">Z</span></a>
			</div>
			<br/>
			<div>
			<table>
				<tr>
			    	<td><label>Search By Name&nbsp;</label></td>
			    	<td><input type="text" class="form-control" id="srch" name="srch"/></td>
			    	<td>&nbsp;</td>
				    <td><a href="#" class="btn btn-primary" onclick="getStock('${requestScope.action}')">Search</a></td>
				</tr>			    
			</table>
			</div>			
		</div>
	</div>
	<form role="form" action="trading" method="POST" id="trading">
		 <input type=hidden value="<c:out value="${requestScope.action}"/>" name="action"/>
		<table class="table table-striped my-table">
		    <thead>
		      <tr>
		        <th>Stock Name</th>
		        <th>Symbol</th>
		        <th>Price</th>
		        <c:if test="${( not empty requestScope.action) && (requestScope.action=='update')}">
		        	<th>New Price</th>
		        </c:if>
		        <c:if test="${( not empty requestScope.action) && (requestScope.action!='update')}">
		       		<th>Action</th>
		       	 </c:if>
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
					        <c:if test="${( not empty requestScope.action) && (requestScope.action=='update')}">
					        	 <td> <input type="text" class="form-control stock-price-input" name="stock_price_<c:out value="${stock.symbol}"/>"/></td>
					        </c:if>
					        
					        <c:if test="${( not empty requestScope.action) && (requestScope.action!='update')}">
					        	<td>
									<a href="trading?symbol=<c:out value="${stock.symbol}"/> "><span class="label label-primary">Trade</span></a>
								</td>
							</c:if>			        	
					      </tr>
						</c:forEach>
					</c:when>
				</c:choose>
		    </tbody>		    
		</table>  
		<c:if test="${( not empty requestScope.action) && (requestScope.action=='update')}">
			<button class="btn btn-primary" type="submit" id="updateStock" name="updateStock">Update Price</button>
		</c:if>
	</form>
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>