/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci6206.dao;

import ci6206.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    
    //--------------------------------------------------------------------------------------
    public User findByUsername(String username) {
    	String q = "SELECT * FROM t_users " +
    			"WHERE c_username = '" + username + "'";
    	
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
