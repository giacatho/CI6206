package ci6206.servlet;

import java.io.IOException;
import java.sql.Timestamp;
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

import ci6206.dao.UserDao;
import ci6206.model.Constants;
import ci6206.model.User;

/**
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
    	    		
    	        	dao.OpenConnection();
    	        	Calendar calendar = Calendar.getInstance();
    	        	Date now = calendar.getTime();
    	        	Timestamp currentTimestamp = new Timestamp(now.getTime());
    	        	dao.updateUserProfile(firstName, lastName, currentTimestamp, email,user.getUsername());
    	        	request.setAttribute("successMessage","Profile has been updated succseefully");
    	        	RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
    	    		dispatcher.forward(request, response);
    	    		return;
    	    	}
        	}
    	} catch (Exception e) {
    		logger.error(e.fillInStackTrace());
    	} finally {
    		dao.CloseConnection();
    	}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/profile.jsp");
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
