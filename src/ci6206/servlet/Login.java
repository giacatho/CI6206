package ci6206.servlet;

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

import ci6206.dao.RoleDAO;
import ci6206.dao.RolePermissionDAO;
import ci6206.dao.UserDao;
import ci6206.model.Constants;
import ci6206.model.Permission;
import ci6206.model.Role;
import ci6206.model.User;

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
    	request.setAttribute(Constants.TITLE, "Login");
    	
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	
    	if((username!=null&&!username.isEmpty())&&
    	   (password!=null&&!password.isEmpty()))
    	{
	    	UserDao dao = new UserDao();
	    	dao.OpenConnection();
	    	User user = dao.findByCredentials(username, password);
	    	dao.CloseConnection();
	    	if (user == null) {
	    		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
	    		request.setAttribute("message", "Login fails.");
	    		dispatcher.forward(request, response);
	    		return;
	    	}
	    	
	    	//2015-10-04 by Qiao Guo Jun: Retrieve Permissions for current user.
	    	retrievePermissions(request, response, username);
		    	// OK, store user to section and redirect to profile.
		    	HttpSession session = request.getSession();
		    	session.setAttribute(Constants.USER_ATTR, user);
		    	response.sendRedirect(getServletContext().getContextPath() + "/home");
		    	
    	}
    	else{
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
    		dispatcher.forward(request, response);
    		return;
    	}
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
