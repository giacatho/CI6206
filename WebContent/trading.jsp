<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ci6206.model.Stock" %>


<!DOCTYPE html>
<html>
<script>
function getStock()
{
	var value =  "trading?srch="+document.getElementById("srch").value;
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
			<h2 class="panel-title">Select Stocks</h2>
		</div>
		<div class="panel-body">
			<div>
				<span class="label label-info">By Alphabet</span>			
				<a href="trading?srch=A"><span class="badge">A</span></a>
				<a href="trading?srch=B"><span class="badge">B</span></a>		
				<a href="trading?srch=C"><span class="badge">C</span></a>		
				<a href="trading?srch=D"><span class="badge">D</span></a>
				<a href="trading?srch=E"><span class="badge">E</span></a>
				<a href="trading?srch=F"><span class="badge">F</span></a>
				<a href="trading?srch=G"><span class="badge">G</span></a>
				<a href="trading?srch=H"><span class="badge">H</span></a>
				<a href="trading?srch=I"><span class="badge">I</span></a>
				<a href="trading?srch=J"><span class="badge">J</span></a>
				<a href="trading?srch=K"><span class="badge">K</span></a>
				<a href="trading?srch=L"><span class="badge">L</span></a>
				<a href="trading?srch=M"><span class="badge">N</span></a>
				<a href="trading?srch=O"><span class="badge">O</span></a>
				<a href="trading?srch=P"><span class="badge">P</span></a>
				<a href="trading?srch=Q"><span class="badge">Q</span></a>
				<a href="trading?srch=R"><span class="badge">R</span></a>
				<a href="trading?srch=S"><span class="badge">S</span></a>
				<a href="trading?srch=T"><span class="badge">T</span></a>
				<a href="trading?srch=U"><span class="badge">U</span></a>
				<a href="trading?srch=V"><span class="badge">V</span></a>
				<a href="trading?srch=W"><span class="badge">W</span></a>
				<a href="trading?srch=X"><span class="badge">X</span></a>
				<a href="trading?srch=Y"><span class="badge">Y</span></a>
				<a href="trading?srch=Z"><span class="badge">Z</span></a>
			</div>
			<br/>
			<div>
				<table>
			    <td>
  			        <span class="label label-info">Search by name:</span>
			    </td>
			    <td>
			    	<input type="text" class="form-control" id="srch" name="srch"/>
			    </td>
			    <td>
					<a href="#" class="btn btn-info" onclick="getStock()">Search</a>
				</td>			    
				</table>
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