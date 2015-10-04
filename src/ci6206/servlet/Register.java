package ci6206.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

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
	
    public Register() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	double initialBalance = 0.0;
    	request.setAttribute(Constants.TITLE, "Registration");
    	
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	String firstName = request.getParameter("firstname");
    	String lastName = request.getParameter("lastname");
    	String email = request.getParameter("email");
    	String status = request.getParameter("status");
    	if(request.getParameter("initialBalance") !=null)  {
    		try{
        		initialBalance = Double.parseDouble(request.getParameter("initialBalance"));
        	}catch (NumberFormatException ex) {
        		request.setAttribute("errorMessage","Only Integers allowed for initial balance");
            	RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
        		dispatcher.forward(request, response);
        		return;
        	}
    	}
    	
    	Calendar calendar = Calendar.getInstance();
        Date registeredDate = new java.sql.Date(calendar.getTime().getTime());
        
        if (username==null || password ==null) {
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
    		dispatcher.forward(request, response);
    		return;
    	}
        
        if (email == null) {
    		request.setAttribute("errorMessage", "Please enter Email");
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
    		dispatcher.forward(request, response);
    		return;
    	} else {
                if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
                	request.setAttribute("errorMessage","Please Enter Valid Email");
                	RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
            		dispatcher.forward(request, response);
            		return;
                }
        }
        
    	username = username.trim();
    	password = password.trim();
    	firstName = firstName.trim();
    	lastName = lastName.trim();
    	
    	UserDao dao = new UserDao();
    	dao.OpenConnection();
    	User user = dao.findUser(username);//dao.findByUsername(username);
    	
    	if (user != null) {
    		request.setAttribute("errorMessage", "Username has been taken.");
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
    		dispatcher.forward(request, response);
    		return;
    	}

    	dao.registerUser(username, password, firstName, lastName, registeredDate, email, initialBalance,status);
    	dao.CloseConnection();
    	request.setAttribute("successMessage", "Account has been created successfully");
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/success.jsp");
    	dispatcher.forward(request, response);
		return;
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
