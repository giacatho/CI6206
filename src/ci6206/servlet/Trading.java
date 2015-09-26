package ci6206.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ci6206.dao.TransactionDAO;
import ci6206.dao.StockDAO;
import ci6206.model.Constants;
import ci6206.model.Stock;
import ci6206.model.Transaction;
import ci6206.model.User;

/**
 * Servlet implementation class Trading
 * @author Michael Tan
 */
@WebServlet(name="trading", urlPatterns={"/trading"})
public class Trading extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Trading() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
    	
    	String page= "/trading.jsp";
    	request.setAttribute(Constants.TITLE, "Trading");
    	
    	String beginStr = request.getParameter(Constants.SEARCH_PARAM);
    	String symbol = request.getParameter(Constants.SYMBOL_PARAM);
    	StockDAO stockDO = new StockDAO();
    	stockDO.OpenConnection();
    	if(beginStr!=null&&!beginStr.isEmpty())
    	{
        	
        	ArrayList<Stock>list = stockDO.GetStocksStartWith(beginStr);
    		request.setAttribute(Constants.STOCK_LIST,list);
    	}
    	else if(symbol!=null&&!symbol.isEmpty())
    	{
    		
    		page = "/stockTrade.jsp";
        	Stock stock = stockDO.GetStock(symbol);
    		request.setAttribute(Constants.STOCK,stock);
    		
    	}
    	stockDO.CloseConnection();
	    //response.sendRedirect(getServletContext().getContextPath() + "/trading.jsp");
    	
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		processRequest(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//processRequest(request, response);
		processTrade(request,response);
		
	}
	
	protected void processTrade(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
	{

    	request.setAttribute(Constants.TITLE, "Trading");
		String page= "/trading.jsp";
		String action = request.getParameter(Constants.OPT_PARAM);
		String symbol = request.getParameter(Constants.SYMBOL_PARAM);
		String stockName = request.getParameter(Constants.STOCK_PARAM);
		String priceStr  = request.getParameter(Constants.PRICE);
		String qtyStr    = request.getParameter(Constants.QTY);
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Constants.USER_ATTR);

		double price = Double.valueOf(priceStr);
		int qty = Integer.valueOf(qtyStr);
		double amount = price*qty;
		
		Stock stock = new Stock();
		stock.setSymbol(symbol);
		stock.setPrice(price);
		stock.setName(stockName);
		
		Transaction trans = new Transaction();
		//trans.setQuantity(qty);
		//trans.setStock(stock);
		trans.setUser(user);
		trans.setAction(action);
		
		TransactionDAO transDAO = new TransactionDAO();
		transDAO.OpenConnection();
		//see whether there's existing stock
		Transaction prevTrans = transDAO.getTransaction(stock.getSymbol(), user.getUsername());
		
		if(action.equals(Constants.BUY))
		{
			if(user.getCashBal()>=amount)
			{	
				double adjustedAmt = 0,adjustedPrice=0;
				if(prevTrans!=null)
				{
					//Add to existing position
					adjustedAmt = prevTrans.getBuyAmount()+amount;
					qty = qty + prevTrans.getBuyQuantity();
					adjustedPrice = adjustedAmt/qty;
					stock.setPrice(adjustedPrice);
					trans.setBuyQuantity(qty);
					trans.setBuyAmount(adjustedAmt);
					trans.setBuyStock(stock);
					trans.setAction(Constants.ADD);
					transDAO.Trade(trans);
				}
				else
				{
					trans.setBuyQuantity(qty);
					trans.setBuyAmount(amount);
					trans.setBuyStock(stock);
					transDAO.Trade(trans);
				}
			}
			else
			{
				request.setAttribute(Constants.ERR, "Not Enough Cash");
				request.setAttribute(Constants.STOCK,stock);
				page="/stockTrade.jsp";
			}
		}
		else if (action.equals(Constants.SELL))
		{
			//check you have enough shares to sell
			if(prevTrans==null || (prevTrans!=null && prevTrans.getBuyQuantity()<qty))
			{
				
				//error
				request.setAttribute(Constants.ERR, "Short Selling is not allowed");
				request.setAttribute(Constants.STOCK,stock);
				page="/stockTrade.jsp";				
			}
			else
			{
				if(prevTrans!=null&&prevTrans.getSellQuantity()>0)
				{
					//reduce
					double adjAmt = amount + prevTrans.getSellAmount();
					trans.setSellAmount(adjAmt);
					double adjustedProfit = adjAmt - prevTrans.getBuyAmount();
					trans.setProfit(adjustedProfit);
					
					int adjQty = qty + prevTrans.getSellQuantity();
					trans.setSellQuantity(adjQty);
					trans.setSellStock(stock);
					trans.setBuyQuantity(prevTrans.getBuyQuantity()-qty);
					trans.setBuyAmount(trans.getBuyQuantity()*prevTrans.getBuyStock().getPrice());
					trans.setAction(Constants.REDUCE);
					transDAO.Trade(trans);
				}
				else
				{
					//calculate profit
					double profit = amount - prevTrans.getBuyStock().getPrice()*qty;
					trans.setProfit(profit);
					trans.setSellAmount(amount);
					trans.setSellQuantity(qty);
					trans.setSellStock(stock);
					trans.setBuyQuantity(prevTrans.getBuyQuantity()-qty);
					trans.setBuyAmount(trans.getBuyQuantity()*prevTrans.getBuyStock().getPrice());
					transDAO.Trade(trans);
				}
			}
		}
		transDAO.CloseConnection();
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);

	}

}
