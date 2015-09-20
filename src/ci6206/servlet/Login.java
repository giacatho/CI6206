package ci6206.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ci6206.dao.UserDao;
import ci6206.model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet(name="login", urlPatterns={"/login"})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	
    	if (username==null || password ==null) {
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
    		dispatcher.forward(request, response);
    		return;
    	}
    	
    	username = username.trim();
    	password = password.trim();
    	
    	if (username.equals("") || password.equals("")) {
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
    		request.setAttribute("message", "Wrong params, username and password are needed.");
    		dispatcher.forward(request, response);
    		return;
    	}
    	
    	UserDao dao = new UserDao();
    	User user = dao.findByCredentials(username, password);
    	
    	if (user == null) {
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
    		request.setAttribute("message", "Login fails.");
    		dispatcher.forward(request, response);
    		return;
    	}
    	
    	// OK, store user to section and redirect to profile.
    	HttpSession session = request.getSession();
    	session.setAttribute("user", user);
    	response.sendRedirect(getServletContext().getContextPath() + "/home");
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
