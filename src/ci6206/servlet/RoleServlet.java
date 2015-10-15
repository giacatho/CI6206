package ci6206.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ci6206.dao.PermissionDAO;
import ci6206.dao.RoleDAO;
import ci6206.dao.RolePermissionDAO;
import ci6206.dao.UserRoleDAO;
import ci6206.dao.exception.DAOException;
import ci6206.model.Constants;
import ci6206.model.Permission;
import ci6206.model.Role;
import ci6206.utils.Utils;

@WebServlet(name="role", urlPatterns={"/role"})
public class RoleServlet  extends StockGameServlet {
	Logger logger = Logger.getLogger(RoleServlet.class);

	private static final long serialVersionUID = 1L;
	
	

	//Handle all Role requests.
	public void handle() throws ServletException, IOException {
		String action = request.getParameter(Constants.ACTION);
		
		if (action != null) {
			action = action.trim();
			if (action.equals(Constants.LIST)){
				listRole();
			} if (action.equals(Constants.CREATE)){
				createRole();
			}  if (action.equals(Constants.TO_CREATE)){
				toCreateRole();
			}  if (action.equals(Constants.TO_UPDATE)){
				toUpdateRole();
			} else if (action.equals(Constants.UPDATE)) {
				updateRole();
			} else if (action.equals(Constants.DELETE)) {
				deleteRole();
			}
		}
	}

	private void toCreateRole() {
		nextPage = "createRole.jsp";
		PermissionDAO permissionDAO = new PermissionDAO();
		
		try {
			permissionDAO.OpenConnection();
			List<Permission> permissionList = permissionDAO.listPermission();
			request.setAttribute(Constants.PERMISSION_LIST, permissionList);
		} catch (Exception e) {
			logger.error(e);
			showErrorMessage(e.getMessage());
		} finally {
			permissionDAO.CloseConnection();
		}
	}

	private void deleteRole() {
		RoleDAO roleDAO = new RoleDAO();
		UserRoleDAO userRoleDAO = new UserRoleDAO();
		
		try {
			String nbr = request.getParameter("nbr");
			logger.debug("++++++++++++Delete Role with id: "+nbr);
			
			if (nbr != null && !nbr.equals("")) {
				userRoleDAO.OpenConnection();
				
				boolean exist = userRoleDAO.exist(Long.parseLong(nbr));
				logger.debug("++++++++++++Is current Role used by User "+exist);
				if (exist) {
					showInfoMessage("Current Role is used by User.");
				} else {
					roleDAO.OpenConnection();
					roleDAO.deleteRole(Long.parseLong(nbr));
				}
				listRole();
			}
		} catch (Exception e) {
			logger.error(e);
			showErrorMessage(e.getMessage());
		} finally {
			roleDAO.CloseConnection();
			userRoleDAO.CloseConnection();
		}
	}

	/**
	 * Go to Update Role page.
	 */
	private void toUpdateRole() {
		nextPage = "updateRole.jsp";
		RoleDAO roleDAO = new RoleDAO();
		RolePermissionDAO rolePermissionDAO = new RolePermissionDAO();
		PermissionDAO permissionDAO = new PermissionDAO();
		
		try {
			String nbr = request.getParameter("nbr");
			logger.debug("++++++++++++Update Role with id: "+nbr);
			if (nbr != null && !nbr.equals("")) {
				roleDAO.OpenConnection();
				rolePermissionDAO.OpenConnection();
				permissionDAO.OpenConnection();
				
				Role role = roleDAO.findRole(Long.parseLong(nbr));
				//Retrieve all Permissions.
				List<Permission> permissionList = permissionDAO.listPermission();
				//Retrieve Permissions for current role.
				List<Permission> selectedPermissionList = rolePermissionDAO.
						listPermissionsByRoleId(Long.parseLong(nbr));
				
				
				//Flag out all permissions of current role.
				if (permissionList != null && permissionList.size() > 0) {
					for (Permission permission : permissionList) {
						Long permissionId = permission.getNbr();
						
						for(Permission selectedPermission : selectedPermissionList) {
							Long selectedPermissionId = selectedPermission.getNbr();
							
							if (permissionId.equals(selectedPermissionId)) {
								permission.setChecked(true);
								break;
							}
						}
					}
				}
				
				request.setAttribute(Constants.ROLE, role);
				request.setAttribute(Constants.PERMISSION_LIST, permissionList);
			}
		} catch (Exception e) {
			logger.error(e);
			showErrorMessage(e.getMessage());
		} finally {
			roleDAO.CloseConnection();
			rolePermissionDAO.CloseConnection();
			permissionDAO.CloseConnection();
		}
		
	}

	//Create new Role
	private void createRole() throws ServletException, IOException  {
		nextPage = "/createRole.jsp"; 
		RoleDAO roleDAO = new RoleDAO();
		RolePermissionDAO rolePermissionDAO = new RolePermissionDAO();
		
		try {
			boolean valid = validate(request, response);
			
			if (valid) {
				//Create a new Role.
				roleDAO.OpenConnection();
				Role role = new Role();
				populate(role);
				Long roleId = Utils.generateUniqueId();
				role.setNbr(roleId);
				roleDAO.createRole(role);
				
				//Create Role Permission records
				rolePermissionDAO.OpenConnection();
				String[] selectedPermissionIds = request.getParameterValues("permissionCxb");
				//Convert String array to Long List
				List<Long> permissionIdList = new ArrayList<Long> ();
				for (String permissionId : selectedPermissionIds) {
					permissionIdList.add(Long.parseLong(permissionId));
				}
				rolePermissionDAO.createPermissionsForRole(roleId, permissionIdList);
				
				listRole();
			} else {
				toCreateRole();
			}
		} catch (Exception e) {
			logger.error(e);
			showErrorMessage(e.getMessage());
		} finally {
			roleDAO.CloseConnection();
			rolePermissionDAO.CloseConnection();
		}
	}
	
	//Create new Permission
	private void updateRole() throws ServletException, IOException  {
		nextPage = "/updateRole.jsp"; 
		RoleDAO roleDAO = new RoleDAO();
		RolePermissionDAO rolePermissionDAO = new RolePermissionDAO();
		
		try {
			boolean valid = validate(request, response);
			
			if (valid) {
				//1. Update a Role details.
				roleDAO.OpenConnection();
				Role role = new Role();
				populate(role);
				Long roleId = Long.parseLong(request.getParameter("nbr"));
				role.setNbr(roleId);
				roleDAO.editRole(role);
				
				//2. Delete all existing Permissions by Role id.
				rolePermissionDAO.OpenConnection();
				rolePermissionDAO.deletePermissionsByRoleId(roleId);
				
				//3. Save all selected new Permissions.
				String[] selectedPermissionIds = request.getParameterValues("permissionCxb");
				//Convert String array to Long List
				List<Long> permissionIdList = new ArrayList<Long> ();
				for (String permissionId : selectedPermissionIds) {
					permissionIdList.add(Long.parseLong(permissionId));
				}
				rolePermissionDAO.createPermissionsForRole(roleId, permissionIdList);
				
				//4. Go to Role list page.
				listRole();
			} else {
				toUpdateRole();
			}
		} catch (Exception e) {
			logger.error(e);
			showErrorMessage(e.getMessage());
		} finally {
			roleDAO.CloseConnection();
			rolePermissionDAO.CloseConnection();
		}
	}

	//Validate user input and return true if valid, otherwise return false.
	private boolean validate(HttpServletRequest request,
			HttpServletResponse response) {
		String roleName = request.getParameter("roleName");
		String[] selectedPermissionIds = request.getParameterValues("permissionCxb");
		
		if (roleName == null || roleName.trim().equals("")) {
			showWarnMessage("The Role Name cannot be null.");
			return false;
		}
		
		if (selectedPermissionIds == null || selectedPermissionIds.length <= 0) {
			showWarnMessage("At least select one Permission.");
			return false;
		}
		
		
		//Validate permission list
		
		return true;
	}
	
	//Transfer user input to Role object.
	private void populate(Role role) {
		if (role != null) {
			role.setRoleName(request.getParameter("roleName"));
			role.setRoleDesc(request.getParameter("roleDesc"));
		}
	}

	//Retrieve Role list
	private void listRole() throws ServletException, IOException {
		nextPage = "/listRole.jsp";
		RoleDAO roleDAO = new RoleDAO();
		RolePermissionDAO rolePermissionDAO = new RolePermissionDAO();
		
		try {
			roleDAO.OpenConnection();
			rolePermissionDAO.OpenConnection();
			List<Role> roleList = roleDAO.listRole();
			
			//Retrieve permissions for each role.
			if(roleList != null && roleList.size() > 0) {
				for (Role role : roleList) {
					List<Permission> permissionList = rolePermissionDAO.listPermissionsByRoleId(role.getNbr());
					role.setPermissionList(permissionList);
				}
			}
			request.setAttribute(Constants.ROLE_LIST, roleList);
		} catch (Exception de) {
			showErrorMessage(de.getMessage());
			logger.error(de);
		} finally {
			roleDAO.CloseConnection();
			rolePermissionDAO.CloseConnection();
		}
	}
}
