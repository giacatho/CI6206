/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci6206.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ci6206.model.User;

/**
 *
 * @author Michael Tan
 */
public class UserDao extends AbstractDAO{
	/*
    private Connection connect = null;	 
    private Statement statement = null;
    private PreparedStatement ps = null;
    private ResultSet resSet = null;
    */
    public UserDao()
    {
    	super();
    }
    
    public User findById(int id)
    {
    	return null;
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
	    	}
        }
        catch(SQLException sqle)
        {
        	sqle.printStackTrace();
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
        	sqle.printStackTrace();
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
        	sqle.printStackTrace();
        }
        finally
        {
        	cleanUp();
        }
		
    }
    
  /*
   * 
   *   Have to change the following. Not SQL Injection safe
   *   
   */
    //--------------------------------------------------------------------------------------
    public User findByUsername(String username) {
    	String q = "SELECT * FROM tb_user " +
    			"WHERE userid = '" + username + "'";
    	
    	return findSingleUser(q);
    }
    
    //--------------------------------------------------------------------------------------
    public List<User> findByFirstname(String partialTitle) {
        List<User> todos = new ArrayList<>();
        
        try {
            String q = "SELECT * FROM t_users " +
                    "WHERE c_firstname LIKE '%" + partialTitle + "%'";
            
            //conn = getConnection();
            statement = conn.createStatement();
            resSet = statement.executeQuery(q);
            
            while(resSet.next()) {
                User user = new User();
                //UserMapper.map(user, resSet);
                todos.add(user);
            }        
                   
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanUp();
        }
        
        return todos;
    }
    
    //--------------------------------------------------------------------------------------
    public void insertUser(String username, String password, 
    			String firstName, String lastName) {
    	try {
    		String q = "INSERT INTO t_users " +
    				"SET c_username='" + username + "'," +
    				"    c_password='" + password + "'," +
    				"    c_firstname='" + firstName + "'," +
    				"    c_lastname='" + lastName + "'";
    				
    		//conn = getConnection();
            statement = conn.createStatement();
            statement.executeUpdate(q);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
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
		try {
			String q = "INSERT INTO tb_user " +
					"SET userid='" + username + "'," +
					"    password='" + password + "'," +
					"    last_name='" + lastName + "'," +
					"    first_name='" + firstName + "'," +
					"    initialBalance='" + intialValue + "'," +
					"    email='" + email + "'," +
					"    status='" + status + "'," +
					"    datereg='" + registeredDate + "'";
					
			//conn = getConnection();
	        statement = conn.createStatement();
	        statement.executeUpdate(q);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cleanUp();
		}
    }
    
    //--------------------------------------------------------------------------------------
    private User findSingleUser(String query){
    	User user = null;
        try {
            //conn = getConnection();
            statement = conn.createStatement();
            resSet = statement.executeQuery(query);
            
            if (resSet.next()) {
                user = new User();
                //UserMapper.map(user, resSet);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanUp();
        }
        
        return user;
    }
    
}
