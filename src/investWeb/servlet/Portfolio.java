package investWeb.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import investWeb.dao.StockDAO;
import investWeb.dao.TransactionDAO;
import investWeb.dao.UserDao;
import investWeb.model.Constants;
import investWeb.model.Stock;
import investWeb.model.Transaction;
import investWeb.model.User;

/**
 * Servlet implementation class Porfolio
 */
@WebServlet(name="portfolio", urlPatterns={"/portfolio"})
public class Portfolio extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(Portfolio.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Portfolio() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setAttribute(Constants.TITLE, "My Portfolio");
    	
    	HttpSession session = request.getSession();
    	User user = (User)session.getAttribute(Constants.USER_ATTR);
    	TransactionDAO transDAO = new TransactionDAO();
    	transDAO.OpenConnection();
    	try
    	{
	    	ArrayList<Transaction> transList = transDAO.getTransactionList(user.getUsername());
	    	HashMap<String,Transaction> map = new HashMap<String,Transaction>();
	    	double totalDiv = 0;
	    	for(int i=0; i<transList.size();i++)
	    	{
	    		Transaction trans = transList.get(i);
   		        consolidate(trans,map);
   		        if(trans.getAction().equals(Constants.DIV))
   		        {
   		        	totalDiv = totalDiv + trans.getAmount();
   		        }
	    	}
	    	
	    	double sumShares = 0;
	    	for ( String key : map.keySet() ) 
	    	{
	    		Transaction txn = (Transaction)map.get(key);
	    		if(txn.getQty()>0)
	    			sumShares = sumShares + txn.getStock().getMktPrice()*txn.getQty();
	
	    	}
	    	request.setAttribute(Constants.SHARES, sumShares);
	    	request.setAttribute("DIV", totalDiv);
	    	request.setAttribute(Constants.HOLDING, map);
	    	user.setSharesVal(sumShares);
	    	updateUser(user);
	    	
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/portfolio.jsp");
			dispatcher.forward(request, response);
    	}
		catch (Exception ex)
		{
			logger.error(ex.fillInStackTrace());
			ex.printStackTrace();
		}
		finally
		{
			transDAO.CloseConnection();
		}
    }
	private void updateUser(User user)
	{
		UserDao dao = new UserDao();
		dao.OpenConnection();
		try
		{
			dao.updateUserSharesVal(user);
		}
		catch (Exception ex)
		{
 		  logger.error(ex.fillInStackTrace());
		}
		finally
		{
			 dao.CloseConnection();
		}
	}
    private void consolidate(Transaction trans, HashMap<String,Transaction> summaryMap)
    {
    	//Transaction summary = (Transaction)summaryMap.get(trans.getStock().getSymbol());
    	int qty = 0;
    	double avgCost=0;
    	if(!summaryMap.isEmpty()&&summaryMap.containsKey(trans.getStock().getSymbol()))
    	{
    		Transaction summary = (Transaction)summaryMap.get(trans.getStock().getSymbol());
        	Stock stock = summary.getStock();
    		double amount = summary.getAmount();
    		qty = summary.getQty();
    		/*
    		logger.info("Stock name: "+stock.getName());
    		logger.info("qty: "+qty);
    		logger.info("symbol: "+trans.getStock().getSymbol());
    		logger.info("amount: "+trans.getAmount());
    		logger.info("type: "+trans.getAction());
    		*/
        	if(trans.getAction().equals(Constants.BUY))
        	{
        		//quantity
        		qty = qty + trans.getQty();
        		//logger.info("Summary qty: "+qty);
        		summary.setQty(qty);
        		//total cost
        		amount = amount + trans.getAmount();
        		//logger.info("Summary amt: "+amount);
        		summary.setAmount(amount);
        		//calculate avg cost
        		avgCost = amount/qty;
        		stock.setPrice(avgCost);
        		//logger.info("Stock avg cost: "+avgCost);

        	}
        	else if (trans.getAction().equals(Constants.SELL))
        	{
        		//quantity
        		qty = qty - trans.getQty(); 
        		summary.setQty(qty);
        		amount = amount - trans.getAmount();
        		summary.setAmount(amount);
        		//calculate avg cost
        		if(qty>0)
        		{	
        			avgCost = amount/qty;
        		}
        		else
        		{
        			avgCost = 0;
        		}
        		stock.setPrice(avgCost);

        	}
        	

    	}
    	else 
    	{
    		double mktPrice = getMarketPrice(trans.getStock().getSymbol());
    		trans.getStock().setMktPrice(mktPrice);
    		summaryMap.put(trans.getStock().getSymbol(), trans);
    	}
    	
    }
    
    private double getMarketPrice(String symbol)
    {
    	double price = 0;
    	StockDAO dao = new StockDAO();
    	dao.OpenConnection();
    	try
    	{
    	  price = dao.GetStock(symbol).getPrice();
    	}
    	catch(Exception ex)
    	{
    		logger.error(ex.fillInStackTrace());
    	}
    	finally
    	{
    	 dao.CloseConnection();
    	}
    	return price;
    }
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
