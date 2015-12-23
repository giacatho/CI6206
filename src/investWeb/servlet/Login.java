package investWeb.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import investWeb.dao.RoleDAO;
import investWeb.dao.RolePermissionDAO;
import investWeb.dao.UserDao;
import investWeb.model.Constants;
import investWeb.model.Permission;
import investWeb.model.Role;
import investWeb.model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet(name="login", urlPatterns={"/login"})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(Login.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	UserDao dao = new UserDao();
    	
    	try {
    		request.setAttribute(Constants.TITLE, "Login");
        	
        	String username = request.getParameter("username");
        	String password = request.getParameter("password");
        	
        	if((username!=null&&!username.isEmpty())&&
        	   (password!=null&&!password.isEmpty()))
        	{
    	    	
    	    	dao.OpenConnection();
    	    	User user = dao.findByCredentials(username, password);
    	    	if (user == null) {
    	    		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
    	    		request.setAttribute("message", "Login fails.");
    	    		dispatcher.forward(request, response);
    	    		return;
    	    	}
    	    	
    	    	retrievePermissions(request, response, username);
		    	// OK, store user to section and redirect to profile.
		    	HttpSession session = request.getSession();
		    	session.setAttribute(Constants.USER_ATTR, user);
		    	
		    	if (isAdmin(request)) {
		    		response.sendRedirect(getServletContext().getContextPath() + "/home");
		    	} else {
		    		response.sendRedirect(getServletContext().getContextPath() + "/portfolio");
		    	}
    		    	
        	}
        	else{
        		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        		dispatcher.forward(request, response);
        		return;
        	}
    	} catch (Exception e) {
    		logger.error(e.fillInStackTrace());
    	} finally {
    		dao.CloseConnection();
    	}
    	
    }
    
	 /**
	   * 
	   * Check if login user is Admin.
	   * 
	   * @param request
	   * @return Return true if it is admin, otherwise return false.
	 */
    private boolean isAdmin(HttpServletRequest request) {
	  	Map<String, String> permissionMap = (Map<String, String>) request.
	  			getSession().getAttribute(Constants.USER_PERMISSION_MAP);
	  	
	  	if (permissionMap != null && permissionMap.containsKey("VIEW ADMIN")) {
	  		return true;
	  	}
		return false;
	}

	//Retrieve Permissions for login user and put it into Session.
    private void retrievePermissions(HttpServletRequest request,  
    		HttpServletResponse response, String userName) throws ServletException, IOException {
    	RoleDAO roleDAO = new RoleDAO();
    	RolePermissionDAO rolePermissionDAO = new RolePermissionDAO();
    	
    	try {
    		roleDAO.OpenConnection();
    		rolePermissionDAO.OpenConnection();
    		Map<String, String> userPermissionMap = new HashMap<String, String> ();
    		
    		Role role = roleDAO.findRoleByUserId(userName);
    		if (role != null) {
    			List<Permission> permissionList = rolePermissionDAO.
    					listPermissionsByRoleId(role.getNbr());
    			if (permissionList != null && permissionList.size() > 0) {
    				for (Permission permission : permissionList) {
    					logger.debug("User Permission : "+
    							permission.getActionDesc()+" "+ permission.getPermissionName());
    					userPermissionMap.put(permission.getActionDesc()+" "+ permission.getPermissionName(),  
    							permission.getActionDesc()+" "+permission.getPermissionName());
    				}
    			}
    		}
    		
    		request.getSession().setAttribute(Constants.USER_PERMISSION_MAP, userPermissionMap);
    	} catch (Exception e) {
    		logger.error(e);
    	} finally {
    		roleDAO.CloseConnection();
    		rolePermissionDAO.CloseConnection();
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
