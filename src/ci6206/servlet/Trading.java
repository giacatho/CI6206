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

import org.apache.log4j.Logger;

import ci6206.dao.TransactionDAO;
import ci6206.dao.UserDao;
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
    	StockDAO stockDO = new StockDAO();
    	stockDO.OpenConnection();
    	try
    	{
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
					page="/stockTrade.jsp";
					
				}
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
