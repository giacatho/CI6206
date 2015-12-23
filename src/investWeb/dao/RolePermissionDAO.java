package investWeb.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import investWeb.dao.exception.DAOException;
import investWeb.model.Permission;
import investWeb.model.Role;

public class RolePermissionDAO extends AbstractDAO {
	Logger logger = Logger.getLogger(PermissionDAO.class);
	
	/**
	 * Check if current Permission is used by Role.
	 * 
	 * @param permissionId Permission ID
	 * @return boolean return true if yes, otherwise return false.
	 * @throws DAOException DAO level exception
	 */
	public boolean exist(Long permissionId) throws DAOException {
		
		try {	
	    	if (permissionId != null) {
	    		String sql = new String("SELECT * FROM TB_ROLE_PERMISSION WHERE PERMISSION_NBR=?");
		    	ps = conn.prepareStatement(sql);
		    	ps.setLong(1, permissionId);
		    	resSet = ps.executeQuery();
		        if (resSet.next()) {
		        	return true;
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to check Permission by id "+permissionId
	    			+" in exist method in RolePermissionDAO.", sqle);
	    	throw new DAOException("Failed to check if the Permission "+permissionId+" is used by role.",sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return false;
	}
	
	
	/**
	 * Search all Permissions for specified role
	 * 
	 * @param roleId Role Id
	 * @return All Permission objects of current role.
	 * @throws DAOException
	 */
	public List<Permission> listPermissionsByRoleId(Long roleId) throws DAOException {
		List<Permission> permissionList = new ArrayList<Permission> ();
		
		try {	
	    	if (roleId != null) {
	    		String sql = new String("SELECT * FROM TB_PERMISSION WHERE NBR in "
	    				+ "(select PERMISSION_NBR FROM TB_ROLE_PERMISSION WHERE ROLE_NBR=?)");
		    	ps = conn.prepareStatement(sql);
		    	ps.setLong(1, roleId);
		    	resSet = ps.executeQuery();
		        while(resSet.next()) {
		        	permissionList.add(populate());
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to retrieve Permission list by Role id "+roleId
	    			+" in listPermissionsByRoleId method in RolePermissionDAO.", sqle);
	    	throw new DAOException("Failed to retrieve Permission list by Role id "+roleId,sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return permissionList;
	}
	
	/**
	 * Delete a Permissions by Role Id
	 * 
	 * @param roleId Role Id
	 *  @return Return true if success, otherwise return false.
	 * @throws DAOException DAO level exception
	 */
	public boolean deletePermissionsByRoleId(Long roleId) throws DAOException {
		boolean result = false;
		try {	
	    	if (roleId != null) {
	    		String sql = "DELETE FROM TB_ROLE_PERMISSION WHERE ROLE_NBR=?";
		    	ps = conn.prepareStatement(sql);
		    	ps.setLong(1, roleId);
		    	int row = ps.executeUpdate();
		        if (row > 0) {
		        	result = true;
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to delete Permissions by Role Id "+roleId
	    			+" in deletePermissionsByRoleId method in RolePermissionDAO.", sqle);
	    	throw new DAOException("Failed to delete Permissions by Role Id "+roleId,sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return result;
	}
	
	/**
	 * Create Permissions for specified Role
	 * 
	 * @param roleId Role Id
	 *  @return Return true if success, otherwise return false.
	 * @throws DAOException DAO level exception
	 */
	public boolean createPermissionsForRole(Long roleId, List<Long> permissionIdList) throws DAOException {
		boolean result = true;
		try {	
	    	if (roleId != null && permissionIdList != null && permissionIdList.size() > 0) {
	    		String sql = "INSERT INTO TB_ROLE_PERMISSION (ROLE_NBR,PERMISSION_NBR) VALUES(?,?)";
		    	ps = conn.prepareStatement(sql);
		    	
		    	ps.setLong(1, roleId);
		    	for(Long permissionId : permissionIdList) {
		    		ps.setLong(2, permissionId);
		    		ps.addBatch();
		    	}
		    	
		    	int rows[] = ps.executeBatch();
		        for (int row : rows) {
		        	if (row <= 0) {
		        		result = false;
		        	}
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to create Permissions for Role "+roleId
	    			+" in createPermissionsForRole method in RolePermissionDAO.", sqle);
	    	throw new DAOException("Failed to create Permissions for Role "+roleId,sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return result;
	}
	
	//Convert ResultSet to Permission
	 private Permission populate() throws SQLException {
    	Permission permission = new Permission();
    	
    	permission.setNbr(resSet.getLong("nbr"));
    	permission.setActionDesc(resSet.getString("ACTION_DESC"));
    	permission.setPermissionName(resSet.getString("PERMISSION_NAME"));
    	permission.setPermissionDesc(resSet.getString("PERMISSION_DESC"));
    	
    	return permission;
    }
}
