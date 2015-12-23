package investWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public abstract class AbstractDAO {
	Logger logger = Logger.getLogger(AbstractDAO.class);
	
    protected Connection conn=null;
    protected Statement statement = null;
    protected PreparedStatement ps = null;
    protected ResultSet resSet = null;
    private DBFactory dbFactory = null;
	public AbstractDAO()
	{
		dbFactory = new DBFactory();
		//conn = dbFactory.getConnection();
	}
	
	public void OpenConnection() 
	{
  		   conn = dbFactory.getConnection();
  		   try
  		   {
    		   conn.setAutoCommit(true);
  		   }
  		   catch (SQLException sqle)
  		   {
  			   logger.error(sqle.fillInStackTrace());
  		   }
    		   
	}
	
	public void CloseConnection()
	{
	    try
	    {	
			if(conn!=null)
			{
				conn.close();
			}
	    }catch(SQLException sqle)
	    {
	    	logger.error(sqle.fillInStackTrace());
	    }
		
	}
	
	protected void cleanUp()
	{
    	try{
    	    if(resSet!=null)	 
    	    	resSet.close();
        	if(ps!=null)
     	      ps.close();
        	//if(conn!=null)
	        //	   conn.close();
    	}catch(SQLException ignore){}
		
	}
}
