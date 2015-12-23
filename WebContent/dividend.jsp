<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="investWeb.model.Stock" %>


<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css"/>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<script>
$(document).ready(function(){
	$("#datepicker").datepicker();
    $("#submit").click(function(event){
    	var error = "";
    	var amt = $('#amt').val();
    	if(amt=='' || isNaN(amt) || amt==0 )
    	{
    		error = error +"\nThe Amount is INVALID."  
    	}
    	var date = $("#datepicker").val();
    	if(date=='')
    	{
    		error = error +"\nThe Date is INVALID."
    	}
    	
    	if(error!='')
    	{
    		bootbox.alert(error);
    		event.preventDefault();
    	}
    	else
    	{
    		//alert('success');
    		$('#tradeFrm').submit();
    	}
    	
    });
});

</script>



<body>
<div id="main" class="container">

	<%@ include file="WEB-INF/includes/main-nav.jsp" %>

	<div class="well well-sm page-head">
		<h1>Dividend</h1>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Enter Dividend Amount</h2>
		</div>
		<form role="form" action="trading" method="POST" id="tradeFrm">
		    <div>
	  	        <span class="label label-warning"><c:out value="${requestScope.Error}"/></span>
		    </div>
		    <input type=hidden value="<c:out value="${requestScope.Stock.symbol}"/>" name="symbol"/>
		    <input type=hidden value="<c:out value="${requestScope.Stock.name}"/>" name="stockName"/>
		    
		    <table class="table stock-trade">
			    <tr>
				    <td>Stock Name:</td>
				    <td>
					   <c:out value="${requestScope.Stock.name}"/>
				    </td>
			    </tr>
			    <tr>
				    <td>Symbol:</td>
				    <td>
					   <c:out value="${requestScope.Stock.symbol}"/>
				    </td>
			    </tr>
			    <tr>
				    <td>Date of Transaction:</td>
				    <td>
				        <input type="text" class="form-control" id="datepicker" name="date"/>
				    </td>
			    </tr>
			    
			    <tr>
				    <td>Amount:</td>
				    <td>
				        <input type="text" class="form-control" id="amt" name="amt"/>
				        <input type="hidden" id="optradio" name="optradio" value="div"/>
				        <input type="hidden" name="price" value="0"/>
				        <input type="hidden" name="qty" value="0"/>
				        
				    </td>
			    </tr>
			    <tr>
			        <td>
			        </td>
				    <td>
			    	    <button type="submit" class="btn btn-primary" id="submit">Confirm</button>
				    </td>
			    </tr>
			    
		    </table>
		</form>
	</div>
</div>
<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>