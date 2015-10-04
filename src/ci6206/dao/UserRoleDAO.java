package ci6206.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import ci6206.dao.exception.DAOException;
import ci6206.model.Role;

/**
 *User Role Data Access Object.
 *
 * Author Qiao Guo Jun
 * Date Oct 1, 2015
 * Version 1.0 
 */
public class UserRoleDAO extends AbstractDAO {
	Logger logger = Logger.getLogger(UserRoleDAO.class);
	
	/**
	 * Check if current Role is used by User.
	 * 
	 * @param roleId Role ID
	 * @return boolean return true if yes, otherwise return false.
	 * @throws DAOException DAO level exception
	 */
	public boolean exist(Long roleId) throws DAOException {
		
		try {	
	    	if (roleId != null) {
	    		String sql = new String("SELECT * FROM TB_USER_ROLE WHERE ROLE_NBR=?");
		    	ps = conn.prepareStatement(sql);
		    	ps.setLong(1, roleId);
		    	resSet = ps.executeQuery();
		        if (resSet.next()) {
		        	return true;
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to check Role by id "+roleId
	    			+" in exist method in UserRoleDAO.", sqle);
	    	throw new DAOException("Failed to check if the Role "+roleId+" is used by User.",sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return false;
	}
	
	/**
	 * Search user role
	 * 
	 * @param roleId Role Id
	 * @return The role of current user
	 * @throws DAOException
	 */
	public Role findRoleByUserId(String userId) throws DAOException {
		Role role = null;
		try {	
	    	if (userId != null) {
	    		String sql = new String("SELECT * FROM TB_ROLE WHERE NBR = "
	    				+ "(select ROLE_NBR FROM TB_USER_ROLE WHERE USER_ID=?)");
		    	ps = conn.prepareStatement(sql);
		    	ps.setString(1, userId);
		    	resSet = ps.executeQuery();
		        if(resSet.next()) {
		        	role = populate();
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to retrieve Role by User Id "+userId
	    			+" in findRoleByUserId method in UserRoleDAO.", sqle);
	    	throw new DAOException("Failed to retrieve Role by User id "+userId,sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return role;
	}
	
	/**
	 * Update role for current user.
	 *  
	 * @param userId User Id
	 * @param roleId Role Id
	 * @return Return true if success, otherwise return false.
	 * @throws DAOException
	 */
	public boolean  updateRoleForUser(String userId, Long roleId) throws DAOException {
		boolean result = false;
		try {	
	    	if (userId != null && roleId != null) {
	    		String sql = new String("UPDATE TB_USER_ROLE SET ROLE_NBR=? WHERE USER_ID=?");
		    	ps = conn.prepareStatement(sql);
		    	ps.setLong(1, roleId);
		    	ps.setString(2, userId);
		    	int row = ps.executeUpdate();
		        if(row >= 0) {
		        	result = true;
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to update Role for User "+userId
	    			+" in updateRoleForUser method in UserRoleDAO.", sqle);
	    	throw new DAOException("Failed to update Role for User "+userId,sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return result;
	}
	
	/**
	 * Delete a Role for specified user
	 * 
	 * @param userId User Id
	 *  @return Return true if success, otherwise return false.
	 * @throws DAOException DAO level exception
	 */
	public boolean deleteRoleByUserId(String userId) throws DAOException {
		boolean result = false;
		try {	
	    	if (userId != null) {
	    		String sql = "DELETE FROM TB_USER_ROLE WHERE USER_ID=?";
		    	ps = conn.prepareStatement(sql);
		    	ps.setString(1, userId);
		    	int row = ps.executeUpdate();
		        if (row > 0) {
		        	result = true;
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to delete Role by User Id "+userId
	    			+" in deleteRoleByUserId method in UserRoleDAO.", sqle);
	    	throw new DAOException("Failed to delete Role by User Id "+userId,sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return result;
	}
	
	/**
	 * Create Role for specified User
	 * 
	 * @param userId User Id
	 * @param roleId Role Id
	 *  @return Return true if success, otherwise return false.
	 * @throws DAOException DAO level exception
	 */
	public boolean createRoleForUser(String userId, Long roleId) throws DAOException {
		boolean result = true;
		try {	
	    	if (userId != null && !userId.equals("") && roleId != null) {
	    		String sql = "INSERT INTO TB_USER_ROLE(USER_ID, ROLE_NBR) VALUES(?,?)";
		    	ps = conn.prepareStatement(sql);
		    	
		    	ps.setString(1, userId);
		    	ps.setLong(2, roleId);
		    	
		    	
		    	int row = ps.executeUpdate();
	        	if (row <= 0) {
	        		result = false;
	        	}
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to create Role for User "+userId
	    			+" in createRoleForUser method in UserRoleDAO.", sqle);
	    	throw new DAOException("Failed to create Role for User "+userId,sqle);
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
    	role.setRoleName(resSet.getString("ROLE_DESC"));
    	
    	return role;
    }
}
