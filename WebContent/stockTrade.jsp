<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="ci6206.model.Stock" %>


<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/includes/head.jsp" %>
<script>
$(document).ready(function(){
    $("#submit").click(function(event){
    	var error = "";
    	if(
    	  ($('input:radio[name=optradio]')[0].checked == false)
    	&&($('input:radio[name=optradio]')[1].checked == false)
    	  )
    	{
    		error = error+'Please select EITHER BUY OR SELL.';
    		//alert(error);
    	}
    	var price = $('#price').val();
    	if(price=='' || isNaN(price) || price<=0 )
    	{
    		error = error +"\nThe PRICE is INVALID."  
    	}

    	var qty =  $('#qty').val();
    	
    	if(qty != parseInt(qty,10)
    	|| qty <100 || (qty % 100) != 0		
    	   )
    	{
    		error = error +"\nThe QUANTITY is INVALID."  
    	}
    	
    	if(error!='')
    	{
    		alert(error);
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
		<h1>Trading</h1>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Trade Stocks</h2>
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
				    <td>Price:</td>
				    <td>
				        <input type="text" class="form-control" id="price" name="price" value="<c:out value="${requestScope.Stock.price}"/>" disabled/>
				    </td>
			    </tr>
	
			    <tr>
				    <td>Quantity:</td>
				    <td>
				        <input type="text" class="form-control" id="qty" name="qty"/>
				    </td>
			    </tr>
			    <tr>
				    <td>Action:</td>
				    <td>
						<label class="radio-inline"><input type="radio" name="optradio" value="buy">Buy</label>
						<label class="radio-inline"><input type="radio" name="optradio" value="sell">Sell</label>
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