package ci6206.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ci6206.dao.RoleDAO;
import ci6206.dao.TransactionDAO;
import ci6206.model.Constants;
import ci6206.model.User;

/**
 * Servlet implementation class TransactionDet
 */
@WebServlet("/TransactionDet")
public class TransactionDet extends HttpServlet {
	Logger logger = Logger.getLogger(TransactionDet.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionDet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		processRequest(request,response);
	}
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
    	request.setAttribute(Constants.TITLE, "Transaction Detail");
    	String symbol = request.getParameter(Constants.SYMBOL_PARAM);
    	HttpSession session = request.getSession();
    	User user = (User)session.getAttribute(Constants.USER_ATTR);
    	String page = "/portfolio.jsp";
        if(symbol!=null && !symbol.isEmpty())
        {
	    	TransactionDAO txnDAO = new TransactionDAO();
	    	txnDAO.OpenConnection();
	    	try
	    	{
	    	 request.setAttribute(Constants.HOLDING,txnDAO.getTransactionList(symbol, user.getUsername()));
	    	}catch (Exception ex)
	    	{
	    		logger.error(ex.fillInStackTrace());
	    	}
	    	finally
	    	{
	    	    txnDAO.CloseConnection();
	    	}
	    	page = "/txn_det.jsp";
        }
    	RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);

    }

}
