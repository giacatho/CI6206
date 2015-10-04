package ci6206.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ci6206.model.Constants;

/**
 *The base servlet
 *
 * Author Qiao Guo Jun
 * Date Sep 29, 2015
 * Version 1.0 
 */
public abstract class StockGameServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected String nextPage = "";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		init(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		init(request, response);
	}
	
	/**
	 * Handle user requests
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public abstract void handle() throws ServletException, IOException;
	
	//Show error message on UI.
	protected void showErrorMessage(String message) {
		showMessage(Constants.ERROR_MESSAGE, message);
	}
	
	//Show warn message on UI.
	protected void showWarnMessage(String message) {
		showMessage(Constants.WARN_MESSAGE, message);
	}
	
	//Show info message on UI.
	protected void showInfoMessage(String message) {
		showMessage(Constants.INFO_MESSAGE, message);
	}
	
	//Show message on UI.
	protected void showMessage(String level, String message) {
		request.setAttribute(Constants.MESSAGE_LEVEL, level+": ");
		request.setAttribute(Constants.MESSAGE, message);
	}
	
	//Go to specified page.
	protected void forward() throws ServletException, IOException  {
		if (nextPage != null && !nextPage.trim().equals("")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
		}
	}
	
	private void init(HttpServletRequest request, 
			HttpServletResponse response)  throws ServletException, IOException{
		this.request = request;
		this.response = response;
		this.nextPage = "";
		handle();
		forward();
	}
}
