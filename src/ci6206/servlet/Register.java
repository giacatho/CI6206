package ci6206.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ci6206.dao.UserDao;
import ci6206.model.Constants;
import ci6206.model.User;

/**
 * Servlet implementation class Register
 */
@WebServlet(name = "register", urlPatterns = {"/register"})
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setAttribute(Constants.TITLE, "Registration");
    	
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	String firstName = request.getParameter("firstname");
    	String lastName = request.getParameter("lastname");
    	
    	if (username==null || password ==null) {
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
    		dispatcher.forward(request, response);
    		return;
    	}
    	
    	username = username.trim();
    	password = password.trim();
    	firstName = firstName.trim();
    	lastName = lastName.trim();
    	
    	if (username.equals("") || password.equals("")) {
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
    		request.setAttribute("message", "Wrong params. Username and password are compulsory.");
    		dispatcher.forward(request, response);
    		return;
    	}
    	
    	
    	UserDao dao = new UserDao();
    	User user = dao.findByUsername(username);
    	
    	if (user != null) {
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
    		request.setAttribute("message", "Username has been taken.");
    		dispatcher.forward(request, response);
    		return;
    	}

    	// OK, so insert to DB and redirect to login page
    	dao.insertUser(username, password, firstName, lastName);
    	response.sendRedirect(getServletContext().getContextPath() + "/login");
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
