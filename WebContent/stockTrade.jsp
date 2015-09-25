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
			<h2 class="panel-title">Stocks Trade</h2>
		</div>
	<form role="form" action="trading" method="POST" id="tradeFrm">
	    <div>
  	        <span class="label label-warning"><c:out value="${requestScope.Error}"/></span>
	    </div>
	    <input type=hidden value="<c:out value="${requestScope.Stock.symbol}"/>" name="symbol"/>
		<div class="table-responsive">		
		    <table class="table">
		    <tr>
		    <td>Stock Name:</td>
		    <td>
			    <div class="col-xs-2">
				   <c:out value="${requestScope.Stock.name}"/>
			    </div>
		    </td>
		    </tr>
		    <tr>
			    <td>Price:</td>
			    <td>
			      <div class="col-xs-2">
			        <input type="text" class="form-control" id="price" name="price" value="<c:out value="${requestScope.Stock.price}"/>"/>
			      </div>  
			    </td>
		    </tr>

		    <tr>
			    <td>Quantity:</td>
			    <td>
			      <div class="col-xs-2">
			        <input type="text" class="form-control" id="qty" name="qty"/>
			      </div>  
			    </td>
		    </tr>
		    <tr>
			    <td>Action:</td>
			    <td>
				    <div class="col-xs-2">
						<label class="radio-inline"><input type="radio" name="optradio" value="buy">Buy</label>
						<label class="radio-inline"><input type="radio" name="optradio" value="sell">Sell</label>
					</div>			
			    </td>
		    </tr>
		    
		    <tr>
		        <td>
		        </td>
			    <td>
				   <div class="col-xs-2">			    
			    	    <button type="submit" class="btn btn-primary" id="submit">Confirm</button>
			    	</div>
			    </td>
		    </tr>
		    
		    </table>
		</div>
</form>
</div>
<%@ include file="WEB-INF/includes/footer.jsp" %>
</body>
</html>