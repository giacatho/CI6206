package ci6206.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ci6206.dao.exception.DAOException;
import ci6206.model.Permission;

/**
 *Permission Data Access Object.
 *
 * Author Qiao Guo Jun
 * Date Sep 28, 2015
 * Version 1.0 
 */
public class PermissionDAO extends AbstractDAO {
	Logger logger = Logger.getLogger(PermissionDAO.class);
	
	/**
	 * Get all Permissions
	 * 
	 * @return Permission list
	 * @throws DAOException DAO level exception
	 */
	public List<Permission> listPermission() throws DAOException{
		List<Permission> permissionList = new ArrayList<Permission> ();
		
	 try {	
	    	//super.OpenConnection();
	        String sql = new String("SELECT * FROM TB_PERMISSION");
	    	ps = conn.prepareStatement(sql);
	    	resSet = ps.executeQuery();
	        while(resSet.next()) {
	        	permissionList.add(populate());
	        }
	    } catch(SQLException sqle) {
	    	logger.error("Failed to list all Permissions in "
	    			+ "listPermission method in PermissionDAO.", sqle);
	    	throw new DAOException("Failed to list all Permissions.",sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return permissionList;
	}
	
	/**
	 * Get Permission by ID
	 * 
	 * @param permissionId Permission ID
	 * @return Permission object
	 * @throws DAOException DAO level exception
	 */
	public Permission findPermission(Long permissionId) throws DAOException {
		Permission permission = null;
		
		try {	
	    	if (permissionId != null) {
	    		String sql = new String("SELECT * FROM TB_PERMISSION WHERE NBR=?");
		    	ps = conn.prepareStatement(sql);
		    	ps.setLong(1, permissionId);
		    	resSet = ps.executeQuery();
		        if (resSet.next()) {
		        	permission = populate();
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to find Permission by id "+permissionId
	    			+" in findPermission method in PermissionDAO.", sqle);
	    	throw new DAOException("Failed to find the Permission by Id "+permissionId+".",sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return permission;
	}
	
	/**
	 * Create new Permission
	 * 
	 * @param permission Permission object
	 *  @return Return true if success, otherwise return false.
	 * @throws DAOException DAO level exception
	 */
	public boolean createPermission(Permission permission) throws DAOException {
		boolean result = false;
		
		try {	
	    	if (permission != null) {
	    		StringBuffer sb = new StringBuffer();
		    	sb.append("INSERT INTO TB_PERMISSION(");
		    	sb.append("NBR, ACTION_DESC,PERMISSION_NAME,PERMISSION_DESC) VALUES(?,?,?,?)");
		    	
		    	ps = conn.prepareStatement(sb.toString());
		    	ps.setLong(1, permission.getNbr());
		    	ps.setString(2, permission.getActionDesc());
		    	ps.setString(3, permission.getPermissionName());
		    	ps.setString(4, permission.getPermissionDesc());
		    	int row = ps.executeUpdate();
		        if (row > 0) {
		        	result = true;
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to create a new Permission in "
	    			+ "createPermission method in PermissionDAO.", sqle);
	    	throw new DAOException("Failed to create a new Permission.",sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return result;
	}
	
	/**
	 * Delete a Permission by Id
	 * 
	 * @param permissionId Permission Id
	 *  @return Return true if success, otherwise return false.
	 * @throws DAOException DAO level exception
	 */
	public boolean deletePermission(Long permissionId) throws DAOException {
		boolean result = false;
		logger.debug("Start to delete Permission "+permissionId);
		try {	
	    	if (permissionId != null) {
	    		String sql = "DELETE FROM TB_PERMISSION WHERE NBR=?";
		    	ps = conn.prepareStatement(sql);
		    	ps.setLong(1, permissionId);
		    	int row = ps.executeUpdate();
		        if (row > 0) {
		        	result = true;
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to delete a Permission by Id "+permissionId
	    			+" in deletePermission method in PermissionDAO.", sqle);
	    	throw new DAOException("Failed to delete a Permission by Id "+permissionId+".",sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return result;
	}
	
	/**
	 * Edit a Permission by Id
	 * 
	 * @param permission Permission object
	 *  @return Return true if success, otherwise return false.
	 * @throws DAOException DAO level exception
	 */
	public boolean editPermission(Permission permission) throws DAOException {
		boolean result = false;
		
		try {	
	    	if (permission != null) {
	    		StringBuffer sb = new StringBuffer();
		    	sb.append("UPDATE TB_PERMISSION ");
		    	sb.append("SET ACTION_DESC=?,PERMISSION_NAME=?,PERMISSION_DESC=? WHERE NBR=?");
		    	
		    	ps = conn.prepareStatement(sb.toString());
		    	ps.setString(1, permission.getActionDesc());
		    	ps.setString(2, permission.getPermissionName());
		    	ps.setString(3, permission.getPermissionDesc());
		    	ps.setLong(4, permission.getNbr());
		    	int row = ps.executeUpdate();
		        if (row > 0) {
		        	result = true;
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to update a Permission in "
	    			+ "editPermission method in PermissionDAO.", sqle);
	    	throw new DAOException("Failed to edit a Permission.",sqle);
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
