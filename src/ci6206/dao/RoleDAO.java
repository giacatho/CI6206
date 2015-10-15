package ci6206.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ci6206.dao.exception.DAOException;
import ci6206.model.Role;

public class RoleDAO extends AbstractDAO {
	Logger logger = Logger.getLogger(RoleDAO.class);
	
	/**
	 * Get all Roles
	 * 
	 * @return Role list
	 * @throws DAOException DAO level exception
	 */
	public List<Role> listRole() throws DAOException{
		List<Role> roleList = new ArrayList<Role> ();
		
	 try {	
	    	//super.OpenConnection();
	        String sql = new String("SELECT * FROM TB_ROLE");
	    	ps = conn.prepareStatement(sql);
	    	resSet = ps.executeQuery();
	        while(resSet.next()) {
	        	roleList.add(populate());
	        }
	    } catch(SQLException sqle) {
	    	logger.error("Failed to list all Roles in "
	    			+ "listRole method in RoleDAO.", sqle);
	    	throw new DAOException("Failed to list all Roles.",sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return roleList;
	}
	
	/**
	 * Get Role by User Id
	 * 
	 * @param userId User ID
	 * @return Role object
	 * @throws DAOException DAO level exception
	 */
	public Role findRoleByUserId(String userId) throws DAOException {
		Role role = null;
		
		try {	
	    	if (userId != null) {
	    		String sql = new String("SELECT * FROM TB_ROLE WHERE NBR="
	    				+ "(SELECT ROLE_NBR FROM TB_USER_ROLE WHERE USER_ID=?)");
		    	ps = conn.prepareStatement(sql);
		    	ps.setString(1, userId);
		    	resSet = ps.executeQuery();
		        if (resSet.next()) {
		        	role = populate();
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to find Role by User Id "+userId
	    			+" in findRoleByUserId method in RoleDAO.", sqle);
	    	throw new DAOException("Failed to find the Role by User Id "+userId+".",sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return role;
	}
	
	/**
	 * Get Role by Id
	 * 
	 * @param roleId Role ID
	 * @return Role object
	 * @throws DAOException DAO level exception
	 */
	public Role findRole(Long roleId) throws DAOException {
		Role role = null;
		
		try {	
	    	if (roleId != null) {
	    		String sql = new String("SELECT * FROM TB_ROLE WHERE NBR=?");
		    	ps = conn.prepareStatement(sql);
		    	ps.setLong(1, roleId);
		    	resSet = ps.executeQuery();
		        if (resSet.next()) {
		        	role = populate();
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to find Role by id "+roleId
	    			+" in findRole method in RoleDAO.", sqle);
	    	throw new DAOException("Failed to find the Role by Id "+roleId+".",sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return role;
	}
	
	/**
	 * Create new Role
	 * 
	 * @param role Role object
	 *  @return Return true if success, otherwise return false.
	 * @throws DAOException DAO level exception
	 */
	public boolean createRole(Role role) throws DAOException {
		boolean result = false;
		
		try {	
	    	if (role != null) {
	    		StringBuffer sb = new StringBuffer();
		    	sb.append("INSERT INTO TB_ROLE(");
		    	sb.append("NBR, ROLE_NAME,ROLE_DESC) VALUES(?,?,?)");
		    	
		    	ps = conn.prepareStatement(sb.toString());
		    	ps.setLong(1, role.getNbr());
		    	ps.setString(2, role.getRoleName());
		    	ps.setString(3, role.getRoleDesc());
		    	int row = ps.executeUpdate();
		        if (row > 0) {
		        	result = true;
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to create a new Role in "
	    			+ "createRole method in RoleDAO.", sqle);
	    	throw new DAOException("Failed to create a new Role.",sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return result;
	}
	
	/**
	 * Delete a Role by Id
	 * 
	 * @param roleId Role Id
	 *  @return Return true if success, otherwise return false.
	 * @throws DAOException DAO level exception
	 */
	public boolean deleteRole(Long roleId) throws DAOException {
		boolean result = false;
		logger.debug("Start to delete Role "+roleId);
		try {	
	    	if (roleId != null) {
	    		String sql = "DELETE FROM TB_ROLE WHERE NBR=?";
		    	ps = conn.prepareStatement(sql);
		    	ps.setLong(1, roleId);
		    	int row = ps.executeUpdate();
		        if (row > 0) {
		        	result = true;
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to delete a Role by Id "+roleId
	    			+" in deleteRole method in RoleDAO.", sqle);
	    	throw new DAOException("Failed to delete a Role by Id "+roleId+".",sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return result;
	}
	
	
	/**
	 * Edit a Role by Id
	 * 
	 * @param role Role object
	 *  @return Return true if success, otherwise return false.
	 * @throws DAOException DAO level exception
	 */
	public boolean editRole(Role role) throws DAOException {
		boolean result = false;
		
		try {	
	    	if (role != null) {
	    		StringBuffer sb = new StringBuffer();
		    	sb.append("UPDATE TB_ROLE ");
		    	sb.append("SET ROLE_NAME=?,ROLE_DESC=? WHERE NBR=?");
		    	
		    	ps = conn.prepareStatement(sb.toString());
		    	ps.setString(1, role.getRoleName());
		    	ps.setString(2, role.getRoleDesc());
		    	ps.setLong(3, role.getNbr());
		    	int row = ps.executeUpdate();
		        if (row > 0) {
		        	result = true;
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to update a Role in "
	    			+ "editRole method in RoleDAO.", sqle);
	    	throw new DAOException("Failed to edit a Role.",sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return result;
	}
	
	//Convert ResultSet to Role
	 private Role populate() throws SQLException {
    	Role role = new Role();
    	
    	role.setNbr(resSet.getLong("nbr"));
    	role.setRoleName(resSet.getString("ROLE_NAME"));
    	role.setRoleDesc(resSet.getString("ROLE_DESC"));
    	
    	return role;
    }
}
