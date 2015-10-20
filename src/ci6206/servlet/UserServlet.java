package ci6206.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ci6206.dao.RoleDAO;
import ci6206.dao.UserDao;
import ci6206.dao.UserRoleDAO;
import ci6206.model.Constants;
import ci6206.model.Role;
import ci6206.model.User;
import ci6206.utils.Utils;


@WebServlet(name="user", urlPatterns={"/user"})
public class UserServlet  extends StockGameServlet {
	Logger logger = Logger.getLogger(UserServlet.class);

	private static final long serialVersionUID = 1L;
	
	

	//Handle all User requests.
	public void handle() throws ServletException, IOException {
		request.setAttribute(Constants.TITLE, "User");
		
		String action = request.getParameter(Constants.ACTION);
		
		if (action != null) {
			action = action.trim();
			if (action.equals(Constants.LIST)){
				listUser();
			} if (action.equals(Constants.TO_UPDATE)){
				toUpdateUser();
			} if (action.equals(Constants.TO_VIEW)){
				toViewUser();
			} else if (action.equals(Constants.UPDATE)) {
				updateUser();
			} else if (action.equals(Constants.DELETE)) {
				deleteUser();
			}
		}
	}

	private void deleteUser() {
		UserDao userDAO = new UserDao();
		UserRoleDAO userRoleDAO = new UserRoleDAO();
		
		try {
			String userId = request.getParameter("userId");
			logger.debug("++++++++++++Delete User with id: "+userId);
			
			if (userId != null && !userId.equals("")) {
				userDAO.OpenConnection();
				userRoleDAO.OpenConnection();
				userRoleDAO.deleteRoleByUserId(userId);
				userDAO.deleteUser(userId);
				
				listUser();
			}
		} catch (Exception e) {
			logger.error(e);
			showErrorMessage(e.getMessage());
		} finally {
			userDAO.CloseConnection();
			userRoleDAO.CloseConnection();
		}
	}

	/**
	 * Go to Update User page.
	 */
	private void toUpdateUser() {
		nextPage = "updateUser.jsp";
		UserDao userDAO = new UserDao();
		RoleDAO roleDAO = new RoleDAO();
		
		try {
			String userId = request.getParameter("userId");
			logger.debug("++++++++++++Update User with id: "+userId);
			if (userId != null && !userId.equals("")) {
				userDAO.OpenConnection();
				roleDAO.OpenConnection();
				
				User user = userDAO.findUser(userId);
				//Retrieve all Roles.
				List<Role> roleList = roleDAO.listRole();
				//Retrieve Role for current User.
				Role role = roleDAO.findRoleByUserId(userId);
				
				logger.debug("User object: "+user+"; Role object: "+role);
				//Flag out Role of current user.
				if (roleList != null && roleList.size() > 0 && role != null) {
					for (Role r : roleList) {
						if (r.getNbr().equals(role.getNbr())) {
							r.setSelected(true);
							break;
						}
					}
				}
				
				request.setAttribute(Constants.USER, user);
				request.setAttribute(Constants.ROLE_LIST, roleList);
			}
		} catch (Exception e) {
			logger.error(e);
			showErrorMessage(e.getMessage());
		} finally {
			userDAO.CloseConnection();
			roleDAO.CloseConnection();
		}
		
	}
	
	/**
	 * Go to View User page.
	 */
	private void toViewUser() {
		nextPage = "viewUser.jsp";
		UserDao userDAO = new UserDao();
		RoleDAO roleDAO = new RoleDAO();
		
		try {
			String userId = request.getParameter("userId");
			logger.debug("++++++++++++View User with id: "+userId);
			if (userId != null && !userId.equals("")) {
				userDAO.OpenConnection();
				roleDAO.OpenConnection();
				
				User user = userDAO.findUser(userId);
				//Retrieve Role for current User.
				Role role = roleDAO.findRoleByUserId(userId);
				if (role != null) {
					user.setRole(role);
				}
				
				request.setAttribute(Constants.USER, user);
			}
		} catch (Exception e) {
			logger.error(e);
			showErrorMessage(e.getMessage());
		} finally {
			userDAO.CloseConnection();
			roleDAO.CloseConnection();
		}
	}
	
	//Update User by User Id
	private void updateUser() throws ServletException, IOException  {
		nextPage = "/updateUser.jsp"; 
		UserDao userDAO = new UserDao();
		UserRoleDAO userRoleDAO = new UserRoleDAO();
		
		try {
			boolean valid = validate(request, response);
			
			if (valid) {
				//1. Update a User details.
				userDAO.OpenConnection();
				User user = new User();
				populate(user);
				userDAO.editUser(user);
				
				//2. Update Role to current User.
				userRoleDAO.OpenConnection();
				String userId = request.getParameter("userId");
				String roleId = request.getParameter("roleId");
				logger.debug("Update role "+roleId+" to user "+userId);
				
				userRoleDAO.deleteRoleByUserId(userId);
				
				if (roleId != null && !roleId.trim().equals("0"))
					userRoleDAO.createRoleForUser(userId, Long.parseLong(roleId));
				
				//3. Go to User list page.
				listUser();
			} else {
				toUpdateUser();
			}
		} catch (Exception e) {
			logger.error(e);
			showErrorMessage(e.getMessage());
		} finally {
			userDAO.CloseConnection();
			userRoleDAO.CloseConnection();
		}
	}

	//Validate user input and return true if valid, otherwise return false.
	private boolean validate(HttpServletRequest request,
			HttpServletResponse response) {
		String firstName = request.getParameter("firstName");
		
		if (firstName == null || firstName.trim().equals("")) {
			showWarnMessage("The First Name cannot be null.");
			return false;
		}
		
		String lastName = request.getParameter("lastName");
		
		if (lastName == null || lastName.trim().equals("")) {
			showWarnMessage("The Last Name cannot be null.");
			return false;
		}
		
		String email = request.getParameter("email");
		
		if (email == null || email.trim().equals("")) {
			showWarnMessage("The Email cannot be null.");
			return false;
		}
		
		if (Utils.validateEmail(email)) {
			showWarnMessage("Please Enter Valid Email.");
			return false;
		}
		
		String initialBalance = request.getParameter("initialBalance");
		
		if (initialBalance == null || initialBalance.trim().equals("")) {
			showWarnMessage("The Initial Balance cannot be null.");
			return false;
		}
		
		if (!Utils.isPositiveNumeric(initialBalance)) {
			showWarnMessage("The Initial Balance should be a positive number.");
			return false;
		}
		
		String password = request.getParameter("password");
		
		if (password == null || password.trim().equals("")) {
			showWarnMessage("The Password cannot be null.");
			return false;
		}
		
		
		String status = request.getParameter("status");
		if (status == null || status.trim().equals("")) {
			showWarnMessage("The Status cannot be null.");
			return false;
		}
		
		return true;
	}
	
	//Transfer user input to User object.
	private void populate(User user) {
		if (user != null) {
			user.setUsername(request.getParameter("userId"));
			user.setFirstname(request.getParameter("firstName"));
			user.setLastname(request.getParameter("lastName"));
			user.setEmail(request.getParameter("email"));
			user.setInitialBalance(Double.parseDouble(request.getParameter("initialBalance")));
			user.setPassword(request.getParameter("password"));
			user.setStatus(request.getParameter("status"));
			user.setLastUpdate(new Timestamp(new Date().getTime()));
		}
	}

	//Retrieve User list
	private void listUser() throws ServletException, IOException {
		nextPage = "/listUser.jsp";
		UserDao userDAO = new UserDao();
		RoleDAO roleDAO = new RoleDAO();
		
		try {
			userDAO.OpenConnection();
			roleDAO.OpenConnection();
			List<User> userList = userDAO.listUser();
			
			//Retrieve Role for each User
			if(userList != null && userList.size() > 0) {
				for (User user : userList) {
					user.setRole(roleDAO.findRoleByUserId(user.getUsername()));
				}
			}
			request.setAttribute(Constants.USER_OBJECT_LIST, userList);
		} catch (Exception de) {
			showErrorMessage(de.getMessage());
			logger.error(de);
		} finally {
			userDAO.CloseConnection();
			roleDAO.CloseConnection();
		}
	}
}
