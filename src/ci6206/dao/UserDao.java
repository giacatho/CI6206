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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguyentritin
 */
public class UserDao {
    private static final String DB_NAME = "ci6206";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/" + DB_NAME;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "toor";
    
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resSet = null;
    
    //--------------------------------------------------------------------------------------
    public User findById(int id) {
    	String q = "SELECT * FROM t_users " +
                "WHERE c_id = " + id;
    	
    	return findSingleUser(q);
    }
    
    //--------------------------------------------------------------------------------------
    public User findByCredentials(String username, String password) {
    	String q = "SELECT * FROM t_users " +
    			"WHERE c_username = '" + username + "' AND c_password = '" + password + "'";
    	
    	return findSingleUser(q);
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
            
            connect = getConnection();
            statement = connect.createStatement();
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
    				
    		connect = getConnection();
            statement = connect.createStatement();
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
            connect = getConnection();
            statement = connect.createStatement();
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
    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    private void close() {
        try {
            if (resSet != null) {
                resSet.close();
            }
            
            if (statement != null) {
                statement.close();
            }
            
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            // No
        }
    }
}
