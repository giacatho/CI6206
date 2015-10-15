package ci6206.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ci6206.dao.PermissionDAO;
import ci6206.dao.RolePermissionDAO;
import ci6206.dao.exception.DAOException;
import ci6206.model.Constants;
import ci6206.model.Permission;
import ci6206.utils.Utils;

@WebServlet(name="permission", urlPatterns={"/permission"})
public class PermissionServlet  extends StockGameServlet {
	Logger logger = Logger.getLogger(PermissionServlet.class);

	private static final long serialVersionUID = 1L;
	
	

	//Handle all Permission requests.
	public void handle() throws ServletException, IOException {
		String action = request.getParameter(Constants.ACTION);
		
		if (action != null) {
			action = action.trim();
			if (action.equals(Constants.LIST)){
				listPermission();
			} if (action.equals(Constants.CREATE)){
				createPermission();
			}  if (action.equals(Constants.TO_CREATE)){
				nextPage = "createPermission.jsp";
			}  if (action.equals(Constants.TO_UPDATE)){
				toUpdatePermission();
			} else if (action.equals(Constants.UPDATE)) {
				updatePermission();
			} else if (action.equals(Constants.DELETE)) {
				deletePermission();
			}
		}
	}

	private void deletePermission() {
		PermissionDAO permissionDAO = new PermissionDAO();
		RolePermissionDAO rolePermissionDAO = new RolePermissionDAO();
		
		try {
			String nbr = request.getParameter("nbr");
			logger.debug("++++++++++++Delete permission with id: "+nbr);
			
			if (nbr != null && !nbr.equals("")) {
				rolePermissionDAO.OpenConnection();
				
				boolean exist = rolePermissionDAO.exist(Long.parseLong(nbr));
				logger.debug("++++++++++++Is current Permission used by role "+exist);
				if (exist) {
					showInfoMessage("Current Permission is used by Role.");
				} else {
					permissionDAO.OpenConnection();
					permissionDAO.deletePermission(Long.parseLong(nbr));
				}
				listPermission();
			}
		} catch (Exception e) {
			logger.error(e);
			showErrorMessage(e.getMessage());
		} finally {
			permissionDAO.CloseConnection();
			rolePermissionDAO.CloseConnection();
		}
	}

	/**
	 * Go to Update Permission page.
	 */
	private void toUpdatePermission() {
		nextPage = "updatePermission.jsp";
		PermissionDAO permissionDAO = new PermissionDAO();
		
		try {
			String nbr = request.getParameter("nbr");
			logger.debug("++++++++++++Update permission with id: "+nbr);
			if (nbr != null && !nbr.equals("")) {
				permissionDAO.OpenConnection();
				Permission permission = permissionDAO.findPermission(Long.parseLong(nbr));
				request.setAttribute(Constants.PERMISSION, permission);
			}
		} catch (Exception e) {
			logger.error(e);
			showErrorMessage(e.getMessage());
		} finally {
			permissionDAO.CloseConnection();
		}
		
	}

	//Create new Permission
	private void createPermission() throws ServletException, IOException  {
		nextPage = "/createPermission.jsp"; 
		PermissionDAO permissionDAO = new PermissionDAO();
		
		try {
			boolean valid = validate(request, response);
			
			if (valid) {
				permissionDAO.OpenConnection();
				
				Permission permission = new Permission();
				permission.setNbr(Utils.generateUniqueId());
				populate(permission);
				permissionDAO.createPermission(permission);
				
				listPermission();
			} 
		} catch (Exception e) {
			logger.error(e);
			showErrorMessage(e.getMessage());
		} finally {
			permissionDAO.CloseConnection();
		}
	}
	
	//Create new Permission
	private void updatePermission() throws ServletException, IOException  {
		nextPage = "/updatePermission.jsp"; 
		PermissionDAO permissionDAO = new PermissionDAO();
		
		try {
			boolean valid = validate(request, response);
			
			if (valid) {
				permissionDAO.OpenConnection();
				
				Permission permission = new Permission();
				permission.setNbr(Long.parseLong(request.getParameter("nbr")));
				populate(permission);
				permissionDAO.editPermission(permission);
				
				listPermission();
			} else {
				toUpdatePermission();
			}
		} catch (Exception e) {
			logger.error(e);
			showErrorMessage(e.getMessage());
		} finally {
			permissionDAO.CloseConnection();
		}
	}

	//Validate user input and return true if valid, otherwise return false.
	private boolean validate(HttpServletRequest request,
			HttpServletResponse response) {
		String actionDesc = request.getParameter("actionDesc");
		String permissionName = request.getParameter("permissionName");
		
		if (actionDesc == null || actionDesc.trim().equals("")) {
			showWarnMessage("The Action Desc cannot be null.");
			return false;
		}
		
		if (permissionName == null || permissionName.trim().equals("")) {
			showWarnMessage("The Permission Name cannot be null.");
			return false;
		}
		
		return true;
	}
	
	//Transfer user input to Permission object.
	private void populate(Permission permission) {
		
		if (permission != null) {
			permission.setActionDesc(request.getParameter("actionDesc"));
			permission.setPermissionDesc(request.getParameter("permissionDesc"));
			permission.setPermissionName(request.getParameter("permissionName"));
		}
	}

	//Retrieve Permission list
	private void listPermission() throws ServletException, IOException {
		nextPage = "/listPermission.jsp";
		PermissionDAO permissionDAO = new PermissionDAO();
		
		try {
			permissionDAO.OpenConnection();
			List<Permission> permissionList = permissionDAO.listPermission();
			request.setAttribute(Constants.PERMISSION_LIST, permissionList);
		} catch (Exception de) {
			showErrorMessage(de.getMessage());
			logger.error(de);
		} finally {
			permissionDAO.CloseConnection();
		}
	}
}
