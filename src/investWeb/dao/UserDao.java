/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package investWeb.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import investWeb.dao.exception.DAOException;
import investWeb.model.User;

public class UserDao extends AbstractDAO{
	Logger logger = Logger.getLogger(UserDao.class);
	
    public UserDao()
    {
    	super();
    }
    
    //--------------------------------------------------------------------------------------
    public User findByCredentials(String username, String password) {
    	String sql = "SELECT * FROM tb_user WHERE userid = ? AND status='A'"; 
    	StringBuffer sb = new StringBuffer(sql);
    	boolean hasPass = false;
    	if(password!=null&&!password.isEmpty())
    	{
        	sb.append(" AND password = ?");
        	hasPass = true;
    	}
    	User user = null;
        try
        {
        	//super.OpenConnection();
	    	ps = conn.prepareStatement(sb.toString());
	    	ps.setString(1, username);
	    	if(hasPass)
	    	  ps.setString(2, password);
	    	resSet = ps.executeQuery();
	    	if(resSet.next())
	    	{
	    		user = new User();
	    		user.setUsername(username);
	    		user.setFirstname(resSet.getString("first_name"));
	    		user.setLastname(resSet.getString("last_name"));
	    		user.setCashBal(resSet.getDouble("cash_bal"));
	    		user.setLastUpdate(resSet.getTimestamp("lastupdate"));
	    		Date inception = resSet.getDate("datereg");
	    		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
	    		user.setInception(df.format(inception));
	    		user.setYrStartBal(resSet.getDouble("totalval_0101"));
	    		user.setSharesVal(resSet.getDouble("share_val"));
	    		user.setEmail(resSet.getString("email"));
	    		user.setPassword(resSet.getString("password"));
	    		user.setInitialBalance(resSet.getDouble("initialBalance"));
	    	}
        }
        catch(SQLException sqle)
        {
        	logger.error(sqle.fillInStackTrace());
        }
        finally
        {
        	cleanUp();
        }
        return user;
    	//return findSingleUser(q);
    }
    public void updateUserCash(User user)
    {
     	
    	StringBuffer sb = new StringBuffer();
		sb.append("UPDATE tb_user ");
		sb.append("SET cash_bal=? ");
		sb.append("WHERE userid=? ");
        try
        {

	    	ps = conn.prepareStatement(sb.toString());
	    	ps.setDouble(1, user.getCashBal());
	    	ps.setString(2, user.getUsername());
	    	ps.executeUpdate();
	    	
        }
        catch(SQLException sqle)
        {
    		try
    		{
    		   conn.rollback();
    		}
    		catch(SQLException ignore){}
    		logger.error(sqle.fillInStackTrace());
        }
        finally
        {
        	cleanUp();
        }
		
    }
    public void updateUserSharesVal(User user)
    {
     	
    	StringBuffer sb = new StringBuffer();
		sb.append("UPDATE tb_user ");
		sb.append("SET share_val=? ");
		sb.append("WHERE userid=? ");
        try
        {

	    	ps = conn.prepareStatement(sb.toString());
	    	ps.setDouble(1, user.getSharesVal());
	    	ps.setString(2, user.getUsername());
	    	ps.executeUpdate();
        }
        catch(SQLException sqle)
        {
    		try
    		{
    		   conn.rollback();
    		}
    		catch(SQLException ignore){}
    		logger.error(sqle.fillInStackTrace());
        }
        finally
        {
        	cleanUp();
        }
		
    }
    
    public void updateUserProfile(String firstName, String lastName, Timestamp lastupdate, String email,String userid,String newPassword) {
        StringBuffer sb = new StringBuffer();
    	sb.append("UPDATE tb_user ");
    	sb.append("SET lastupdate=?, ");
    	sb.append("last_name=?, ");
    	sb.append("first_name=?, ");
    	sb.append("email=?, ");
    	sb.append("password=? ");
    	sb.append("WHERE userid=? ");
    	try
        {

	    	ps = conn.prepareStatement(sb.toString());
	    	ps.setTimestamp(1, lastupdate);
	    	ps.setString(2, lastName);
	    	ps.setString(3, firstName);
	    	ps.setString(4, email);
	    	ps.setString(5, newPassword);
	    	ps.setString(6, userid);
	    	ps.executeUpdate();
        }
        catch(SQLException sqle)
        {
    		try
    		{
    		   conn.rollback();
    		}
    		catch(SQLException ignore){}
    		logger.error("Error in  Update User Profile: "+sqle.fillInStackTrace());
        }
        finally
        {
        	cleanUp();
        }
		
    }
    
    public void updateUserPassword(String newPassword, Timestamp lastupdate,String userid) {
        StringBuffer sb = new StringBuffer();
    	sb.append("UPDATE tb_user ");
    	sb.append("SET lastupdate=?, ");
    	sb.append("password=? ");
    	sb.append("WHERE userid=? ");
    	try
        {
	    	ps = conn.prepareStatement(sb.toString());
	    	ps.setTimestamp(1, lastupdate);
	    	ps.setString(2, newPassword);
	    	ps.setString(3, userid);
	    	ps.executeUpdate();
        }
        catch(SQLException sqle)
        {
    		try
    		{
    		   conn.rollback();
    		}
    		catch(SQLException ignore){}
    		logger.error("Error in updating Password: "+sqle.fillInStackTrace());
        }
        finally
        {
        	cleanUp();
        }
		
    }
    
    
    /**
     * @return create user
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param registeredDate
     * @param email
     * @param intialValue
     * @author lokenath
     */
    public void registerUser(String username, String password, 
			String firstName, String lastName, Date registeredDate, String email, double intialValue,String status) {
    	 StringBuffer sb = new StringBuffer();
     	sb.append("INSERT INTO tb_user ");
     	sb.append("SET userid=?, ");
     	sb.append("password=?, ");
     	sb.append("last_name=?, ");
     	sb.append("first_name=?, ");
     	sb.append("cash_bal=?, ");
     	sb.append("email=?, ");
     	sb.append("status=?, ");
     	sb.append("datereg=?, ");
     	sb.append("initialBalance=?, ");
     	sb.append("totalval_0101=? ");
     	try
         {

 	    	ps = conn.prepareStatement(sb.toString());
 	    	ps.setString(1, username);
 	    	ps.setString(2, password);
 	    	ps.setString(3, lastName);
 	    	ps.setString(4, firstName);
 	    	ps.setDouble(5, intialValue);
 	    	ps.setString(6, email);
 	    	ps.setString(7, status);
 	    	ps.setDate(8, registeredDate);
 	    	ps.setDouble(9, intialValue);
 	    	ps.setDouble(10, intialValue);
 	    	ps.executeUpdate();
		} catch (Exception e) {
    		try
    		{
    		   conn.rollback();
    		}
    		catch(SQLException ignore){}
			logger.equals("Error in Register User: "+e.fillInStackTrace());
		} finally {
			cleanUp();
		}
    }
    
    /********************User Management Methods******************/
    /**
   	 * Get all Users
   	 * 
   	 * @return User list
   	 * @throws DAOException DAO level exception
   	 */
   	public List<User> listUser() throws DAOException{
   		List<User> userList = new ArrayList<User> ();
   		
   	 try {	
   	    	//super.OpenConnection();
   	        String sql = new String("SELECT * FROM TB_USER WHERE STATUS ='A' OR STATUS ='I' ");
   	    	ps = conn.prepareStatement(sql);
   	    	resSet = ps.executeQuery();
   	        while(resSet.next()) {
   	        	userList.add(populate());
   	        }
   	    } catch(SQLException sqle) {
   	    	logger.error("Failed to list all Users in "
   	    			+ "listUser method in UserDao.", sqle);
   	    	throw new DAOException("Failed to list all Users.",sqle);
   	    } finally {
   	    	cleanUp();
   	    }
   		 
   		return userList;
   	}
   	
   	/**
   	 * Get User by User ID
   	 * 
   	 * @param userId User ID
   	 * @return User object
   	 * @throws DAOException DAO level exception
   	 */
   	public User findUser(String userId) throws DAOException {
   		User user = null;
   		
   		try {	
   	    	if (userId != null && !userId.trim().equals("")) {
   	    		String sql = new String("SELECT * FROM TB_USER WHERE userid=? AND (status='A' or status='I') ");
   		    	ps = conn.prepareStatement(sql);
   		    	ps.setString(1, userId);
   		    	resSet = ps.executeQuery();
   		        if (resSet.next()) {
   		        	user = populate();
   		        }
   	    	}
   	    } catch(SQLException sqle) {
   	    	logger.error("Failed to find User by id "+userId
   	    			+" in findUser method in UserDao.", sqle);
   	    	throw new DAOException("Failed to find the User by Id "+userId+".",sqle);
   	    } finally {
   	    	cleanUp();
   	    }
   		 
   		return user;
   	}
   	
   	/**
   	 * Delete a User by User Id
   	 * 
   	 * @param userId User Id
   	 *  @return Return true if success, otherwise return false.
   	 * @throws DAOException DAO level exception
   	 */
   	public boolean deleteUser(String userId) throws DAOException {
   		boolean result = false;
   		logger.debug("Start to delete User "+userId);
   		try {	
   	    	if (userId != null) {
   	    		String sql = "UPDATE TB_User SET status='D' WHERE userid=? ";
   		    	ps = conn.prepareStatement(sql);
   		    	ps.setString(1, userId);
   		    	int row = ps.executeUpdate();
   		        if (row > 0) {
   		        	result = true;
   		        }
   	    	}
   	    } catch(SQLException sqle) {
    		try
    		{
    		   conn.rollback();
    		}
    		catch(SQLException ignore){}
   	    	logger.error("Failed to delete a User by Id "+userId
   	    			+" in deleteUser method in UserDao.", sqle);
   	    	throw new DAOException("Failed to delete a User by Id "+userId+".",sqle);
   	    } finally {
   	    	cleanUp();
   	    }
   		 
   		return result;
   	}
   	
   	/**
   	 * Edit a User by User Id
   	 * 
   	 * @param user User object
   	 *  @return Return true if success, otherwise return false.
   	 * @throws DAOException DAO level exception
   	 */
   	public boolean editUser(User user) throws DAOException {
   		boolean result = false;

   		try {	
   	    	if (user != null) {
   	    		logger.debug("Start to update user "+user.getUsername());
   	    		StringBuffer sb = new StringBuffer();
   		    	sb.append("UPDATE TB_USER ");
   		    	sb.append("SET first_name=?,last_name=?,password=?, status=?, "
   		    			+ "lastupdate=?, email=?, initialBalance=? WHERE userid=?");
   		    	
   		    	ps = conn.prepareStatement(sb.toString());
   		    	ps.setString(1, user.getFirstname());
   		    	ps.setString(2, user.getLastname());
   		    	ps.setString(3, user.getPassword());
   		    	ps.setString(4, user.getStatus());
   		    	ps.setTimestamp(5, user.getLastUpdate());
   		    	ps.setString(6, user.getEmail());
   		    	ps.setDouble(7, user.getInitialBalance());
   		    	ps.setString(8, user.getUsername());
   		    	int row = ps.executeUpdate();
   		        if (row > 0) {
   		        	result = true;
   		        }
   	    	}
   	    } catch(SQLException sqle) {
   	    	
    		try
    		{
    		   conn.rollback();
    		}
    		catch(SQLException ignore){}
   	    	logger.error("Failed to update a User in "
   	    			+ "editUser method in UserDAO.", sqle);
   	    	throw new DAOException("Failed to edit a User.",sqle);
   	    } finally {
   	    	cleanUp();
   	    }
   		 
   		return result;
   	}
   	
   	//Convert ResultSet to User
   	 private User populate() throws SQLException {
   		 User user = new User();
       	
   		 user.setUsername(resSet.getString("userid"));
   		 user.setPassword(resSet.getString("password"));
   		 user.setFirstname(resSet.getString("first_name"));
   		 user.setLastname(resSet.getString("last_name"));
   		 user.setCashBal(resSet.getDouble("cash_bal"));
   		 user.setSharesVal(resSet.getDouble("share_val"));
   		 user.setEmail(resSet.getString("email"));
   		 user.setInitialBalance(resSet.getDouble("initialBalance"));
   		 user.setStatus(resSet.getString("status"));
       	
       	return user;
       }
   	 
   	 
   	public ArrayList<User> getUserForRanking()
    {
    	ArrayList<User> resultList = new ArrayList<User>();
    	
    	String sql = "SELECT USER.USERID, USER.FIRST_NAME, USER.LAST_NAME, USER.SHARE_VAL, USER.CASH_BAL ";
        StringBuffer sb = new StringBuffer(sql);
        sb.append("FROM TB_USER USER ");
        sb.append("WHERE USER.STATUS = 'A' ");
        sb.append("ORDER BY (SHARE_VAL+CASH_BAL) DESC ");
     	sb.append("LIMIT 10");
     	
    	try
        {
    		//super.OpenConnection();
	    	ps = conn.prepareStatement(sb.toString());
	    	resSet = ps.executeQuery();
	    	while(resSet.next())
	    	{
	    		
	    		User user = new User();
	        	user.setFirstname(resSet.getString("first_name"));
	        	user.setLastname(resSet.getString("last_name"));
	        	user.setCashBal(resSet.getDouble("cash_bal"));
	        	user.setSharesVal(resSet.getDouble("share_val"));
	    		resultList.add(user);
	    	}
        }
        catch(SQLException sqle)
        {
        	logger.equals("Error in getting User Ranking: "+sqle.fillInStackTrace());
        }
        finally
        {
        	cleanUp();
        }
    	
    	return resultList;
    }
   	 
}
