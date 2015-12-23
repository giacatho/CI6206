package investWeb.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import investWeb.dao.UserDao;
import investWeb.model.Constants;
import investWeb.model.User;

/**
 * 
 * Servlet implementation class Profile
 */
@WebServlet(name="profile", urlPatterns={"/profile"})
public class Profile extends HttpServlet {
	Logger logger = Logger.getLogger(Profile.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	UserDao dao = new UserDao();
    	
    	try {
    		request.setAttribute(Constants.TITLE, "Profile");
        	String firstName = request.getParameter("firstname");
        	String lastName = request.getParameter("lastname");
        	String email = request.getParameter("email");
        	String currentPassword = request.getParameter("current-password");
        	String newPassword = request.getParameter("new-password");
        	String confirmPassword = request.getParameter("confirm-password");
        	String updateProfile = request.getParameter("updateProfile");
        	
        	HttpSession session = request.getSession();
        	User user = (User)session.getAttribute(Constants.USER_ATTR);
        	request.setAttribute(Constants.USER_LIST,user);
        	if(user.getUsername()!=null) {
    	    	if(updateProfile!=null && updateProfile.equals(Constants.UPDATE_PROFILE)) {
    	    		if(email!=null) {
    		    		if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
    	                	request.setAttribute("errorMessage","Please Enter Valid Email");
    	                	RequestDispatcher dispatcher = request.getRequestDispatcher("/profile.jsp");
    	            		dispatcher.forward(request, response);
    	            		return;
    	                }
    	    		}
    	    		
    	    		
    	    		if(!newPassword.isEmpty() && !confirmPassword.isEmpty()){
	            			if(!(newPassword.equals(confirmPassword))) {
	            				request.setAttribute("errorMessage","New and Confirm passwords don't match. Try again?");
	    	                	RequestDispatcher dispatcher = request.getRequestDispatcher("/profile.jsp");
	    	            		dispatcher.forward(request, response);
	    	            		return;
	            		}
		    		} else if((!newPassword.isEmpty() && confirmPassword.isEmpty()) || ((newPassword.isEmpty() && !confirmPassword.isEmpty()))) {
	        				request.setAttribute("errorMessage","Please enter the both new and confirm password");
		                	RequestDispatcher dispatcher = request.getRequestDispatcher("/profile.jsp");
		            		dispatcher.forward(request, response);
		            		return;
	        		}
    	    		
    	        	dao.OpenConnection();
    	        	Calendar calendar = Calendar.getInstance();
    	        	Date now = calendar.getTime();
    	        	Timestamp currentTimestamp = new Timestamp(now.getTime());
    	        	if(!newPassword.isEmpty()) {
    	        		dao.updateUserProfile(firstName, lastName, currentTimestamp, email,user.getUsername(),newPassword);
    	        	} else {
        	        	dao.updateUserProfile(firstName, lastName, currentTimestamp, email,user.getUsername(),user.getPassword());
    	        	}
    	        	
    	        	ArrayList<User> userList = dao.getUserForRanking();
	            	request.setAttribute(Constants.USER_LIST,userList);
	            	
	            	User currentUser = dao.findUser(user.getUsername());
			    	session.setAttribute(Constants.USER_ATTR, currentUser);
			    	request.setAttribute("title", "Home");
			    	request.setAttribute("successMessage","Profile has been updated successfully");
    	        	RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
    	    		dispatcher.forward(request, response);
    	    		return;
    	    	}
        	}
        	
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/profile.jsp");
    		dispatcher.forward(request, response);
     	} catch (Exception e) {
    		logger.error(e.fillInStackTrace());
    	} finally {
    		dao.CloseConnection();
    	}
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
