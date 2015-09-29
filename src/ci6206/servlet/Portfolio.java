package ci6206.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ci6206.dao.HoldingDAO;
import ci6206.dao.TransactionDAO;
import ci6206.model.Constants;
import ci6206.model.Holding;
import ci6206.model.Transaction;
import ci6206.model.User;

/**
 * Servlet implementation class Porfolio
 */
@WebServlet(name="portfolio", urlPatterns={"/portfolio"})
public class Portfolio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
    	ArrayList<Transaction> transList = transDAO.getTransactionList(user.getUsername());
    	double shareVal=0;
    	for(int i=0; i<transList.size();i++)
    	{
    		Transaction trans = transList.get(i);
    		if(trans.getAction().equals(Constants.BUY))
    		{
    			shareVal = shareVal + trans.getAmount();
    		}
    		else if (trans.getAction().equals(Constants.SELL))
    		{
    			shareVal = shareVal - trans.getAmount();
    		}
    	}
    	request.setAttribute(Constants.SHARES, shareVal);
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/portfolio.jsp");
		dispatcher.forward(request, response);
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
