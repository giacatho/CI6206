package ci6206.dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBFactory {

	public Connection getConnection()
	{
	  Connection conn=null;
	  try{	
		Context initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/sigdb");
		conn = ds.getConnection();
	  }
	  catch(Exception ex)
	  {
		ex.printStackTrace();  
	  }
	  return conn;
	}
}
