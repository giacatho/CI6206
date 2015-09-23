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
		<h1>Trading</h1>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Stocks</h2>
		</div>
		<div class="panel-body">
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
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">List of Stocks</h2>
		</div>
		<div class="panel-body">
		<c:forEach var="stock" items="${pageScope.stockList}">
		    <c:out value="${stock.Name}">
		    </c:out>
		    <br/>
		</c:forEach>
		  <!-- 
		    <h4>This is stock news</h4>
			<h4>This is stock news</h4>
			<h4>This is stock news</h4>
			<h4>This is stock news</h4>
		  -->
		</div>
	</div>
</div>

<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>