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

import ci6206.dao.PortDAO;
import ci6206.dao.StockDAO;
import ci6206.model.Constants;
import ci6206.model.Stock;
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
    	if(beginStr!=null&&!beginStr.isEmpty())
    	{
        	StockDAO stockDO = new StockDAO();
        	ArrayList<Stock>list = stockDO.GetStocksStartWith(beginStr);
    		request.setAttribute(Constants.STOCK_LIST,list);
    	}
    	else if(symbol!=null&&!symbol.isEmpty())
    	{
    		
    		page = "/stockTrade.jsp";
        	StockDAO stockDO = new StockDAO();
        	Stock stock = stockDO.GetStock(symbol);
    		request.setAttribute(Constants.STOCK,stock);
    		
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
		String page= "/trading.jsp";
		String action = request.getParameter(Constants.OPT_PARAM);
		String symbol = request.getParameter(Constants.SYMBOL_PARAM);
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
		stock.setQuantity(qty);
		
		if(user.getCashBal()>=amount)
		{	
			stock.setAmount(amount);
	
			PortDAO port = new PortDAO();
			port.Trade(action, stock, user);
		
		}
		else
		{
			request.setAttribute(Constants.ERR, "Not Enough Cash");
			request.setAttribute(Constants.STOCK,stock);
			page="/stockTrade.jsp";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);

	}

}
