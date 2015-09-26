package ci6206.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ci6206.dao.SubTransactionDAO;
import ci6206.model.Constants;
import ci6206.model.SubTransaction;
import ci6206.model.User;

/**
 * Servlet implementation class Transaction
 */
@WebServlet(name="transactions", urlPatterns={"/transactions"})
public class Transactions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transactions() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute(Constants.TITLE, "Transaction History");
    	
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Constants.USER_ATTR);
		
		SubTransactionDAO subtranDO = new SubTransactionDAO();
		subtranDO.OpenConnection();
		List<SubTransaction> subTranList = subtranDO.getSubTransactions(user.getUsername()); 
		request.setAttribute("subTranList",	subTranList);
		
		subtranDO.CloseConnection();
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/transaction.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
