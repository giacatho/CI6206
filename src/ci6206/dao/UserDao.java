/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci6206.dao;

import ci6206.model.User;
import ci6206.model.UserMapper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tin
 */
public class UserDao extends AbstractDAO{
    //private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement ps = null;
    private ResultSet resSet = null;
    
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

      	
    	String sql = "SELECT * FROM tb_user WHERE userid = ? AND password = ? AND status='A'";
    	ResultSet rs=null;
    	User user = null;
        try
        {
	    	ps = conn.prepareStatement(sql);
	    	ps.setString(1, username);
	    	ps.setString(2, password);
	    	rs = ps.executeQuery();
	    	if(rs.next())
	    	{
	    		user = new User();
	    		user.setUsername(username);
	    		user.setFirstname(rs.getString("first_name"));
	    		user.setLastname(rs.getString("last_name"));
	    	}
        }
        catch(SQLException sqle)
        {
        	sqle.printStackTrace();
        }
        finally
        {
        	try{
        	    if(rs!=null)	 
	        	  rs.close();
	        	if(ps!=null)
         	      ps.close();
	        	if(conn!=null)
 	        	   conn.close();
        	}catch(SQLException ignore){}
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
                UserMapper.map(user, resSet);
                todos.add(user);
            }        
                   
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
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
    		close();
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
                UserMapper.map(user, resSet);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        
        return user;
    }
    
    //--------------------------------------------------------------------------------------
    /*
    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
    }
    */
    
    private void close() {
        try {
            if (resSet != null) {
                resSet.close();
            }
            
            if (statement != null) {
                statement.close();
            }
            
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            // No
        }
    }
}
