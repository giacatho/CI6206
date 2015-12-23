package investWeb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import investWeb.dao.PriceDAO;
import investWeb.dao.StockDAO;
import investWeb.dao.TransactionDAO;
import investWeb.dao.UserDao;
import investWeb.model.Constants;
import investWeb.model.Stock;
import investWeb.model.Transaction;
import investWeb.model.User;
import investWeb.utils.Utils;

@WebServlet(name="trading", urlPatterns={"/trading"})
public class Trading extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(Trading.class);
       
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
    	String action = request.getParameter(Constants.ACTION);
    	String div = request.getParameter(Constants.DIV);
    	request.setAttribute(Constants.ACTION, action);
    	logger.debug("Trading action : "+request.getParameter(Constants.ACTION));
    	StockDAO stockDO = new StockDAO();
    	stockDO.OpenConnection();

    	try
    	{
	    	if(beginStr!=null&&!beginStr.isEmpty())
	    	{
	    		ArrayList<Stock>list=null;
	        	if(beginStr.length()>1)
	        	{
	        		list = stockDO.GetStocksContains(beginStr);
	        	}
	        	else
	        	{
		        	list = stockDO.GetStocksStartWith(beginStr);
	        	}
	    		request.setAttribute(Constants.STOCK_LIST,list);
	    	}
	    	else if(symbol!=null&&!symbol.isEmpty())
	    	{
	    		
	    		page = "/stockTrade.jsp";
	        	Stock stock = stockDO.GetStock(symbol);
	        	if(div==null)
	        	{
		    		TransactionDAO transDAO = new TransactionDAO();
		    		transDAO.OpenConnection();
		    		HttpSession session = request.getSession();
		    		User user = (User)session.getAttribute(Constants.USER_ATTR);
		    		int curQty = transDAO.getTotalQty(symbol, user.getUsername());
		    		request.setAttribute("availQty", curQty);
		    		request.setAttribute(Constants.STOCK,stock);
	        	}
	        	else
	        	{
	        		page ="/dividend.jsp";
		    		request.setAttribute(Constants.STOCK,stock);
	        	}
	    	}
    	}catch(Exception ex)
    	{
    		logger.error(ex.fillInStackTrace());
    	}
    	finally
    	{
    	 stockDO.CloseConnection();
    	}
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
		String page= "/portfolio";
		String action = request.getParameter(Constants.OPT_PARAM);
		String symbol = request.getParameter(Constants.SYMBOL_PARAM);
		String stockName = request.getParameter(Constants.STOCK_PARAM);
		String priceStr  = request.getParameter(Constants.PRICE);
		String qtyStr    = request.getParameter(Constants.QTY);
		String requestAction    = request.getParameter(Constants.ACTION);
		String txnDateStr = request.getParameter("date");
		if (requestAction != null && requestAction.equals(Constants.UPDATE)) {
			updateStockPrice(request, response);
		} 
		else{
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
			trans.setUser(user);
			trans.setAction(action);
			trans.setDate(txnDateStr);
			TransactionDAO transDAO = new TransactionDAO();
			transDAO.OpenConnection();
			double netCash=user.getCashBal();

			try
			{
				
				if(action.equals(Constants.BUY))
				{
					if(user.getCashBal()>=amount)
					{	
						trans.setQty(qty);
						trans.setAmount(amount);
						trans.setStock(stock);
						transDAO.Trade(trans);
						//update the user cashBal
						netCash = netCash - amount;
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
					int existQty = transDAO.getTotalQty(symbol, user.getUsername());
					logger.debug("existing qty: "+existQty);
					//check you have enough shares to sell
					if(qty <= existQty)
					{
							trans.setAmount(amount);
							trans.setQty(qty);
							trans.setStock(stock);
							transDAO.Trade(trans);
							//update the user cashBal
							netCash = user.getCashBal() + amount;
							
					}
					else
					{
						request.setAttribute(Constants.ERR, "Short Selling is not allowed.");
						request.setAttribute(Constants.STOCK,stock);
			    		request.setAttribute("availQty", existQty);

						page="/stockTrade.jsp";
						
					}
				}
				else if (action.equals(Constants.DIV))
				{
					String divAmt = request.getParameter("amt");
					double amt = Double.valueOf(divAmt);
					trans.setAmount(amt);
					trans.setQty(0);
					trans.setStock(stock);
					transDAO.Trade(trans);
					//update the user cashBal
					netCash = user.getCashBal() + amt;
				}

			}catch (Exception ex)
			{
				logger.error(ex.fillInStackTrace());
			}
			finally
			{
				transDAO.CloseConnection();
			}
			user.setCashBal(netCash);
			updateUser(user);
			RequestDispatcher dispatcher = request.getRequestDispatcher(page);
			dispatcher.forward(request, response);
		}
	}
	
	/**
	 * Batch update stock price based on symbols.
	 * 
	 * @param request
	 * @param response
	 */
	private void updateStockPrice(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> stockPriceMap = request.getParameterMap();
		Map<String, Double> stockPrices = new HashMap<String, Double>();
		PriceDAO priceDAO = new PriceDAO();
		
		try {
			Iterator<Entry<String, String[]>> stockPriceIt = stockPriceMap.entrySet().iterator();
			while (stockPriceIt.hasNext()) {
				Entry<String,String[]> stockPriceEntry = stockPriceIt.next();
				String symbol = stockPriceEntry.getKey();
				String price = stockPriceEntry.getValue()[0];
				
				if (symbol != null && symbol.trim().startsWith("stock_price_") &&
						Utils.isPositiveNumeric(price)) {
					symbol = symbol.substring(symbol.lastIndexOf("_")+1);
					logger.debug("Symbol: "+symbol+", price: "+price);
					stockPrices.put(symbol, Double.parseDouble(price));
				}
			}
			
			if (stockPrices.size() > 0) {
				priceDAO.OpenConnection();
				priceDAO.updateStockPrice(stockPrices);
			}
		} catch (Exception ex)
		{
			logger.error(ex.fillInStackTrace());
		}
		finally
		{
			priceDAO.CloseConnection();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/trading.jsp");
			request.setAttribute(Constants.ACTION, Constants.UPDATE);
			dispatcher.forward(request, response);
		}		
	}

	private void updateUser(User user)
	{
		UserDao dao = new UserDao();
		dao.OpenConnection();
		try
		{
		 dao.updateUserCash(user);
		}catch(Exception ex)
		{
			logger.error(ex.fillInStackTrace());
		}
		finally
		{
			dao.CloseConnection();
		}
	}

}
