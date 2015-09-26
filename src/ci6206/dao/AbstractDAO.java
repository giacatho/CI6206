package ci6206.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
*
* @author Michael Tan
*/

public abstract class AbstractDAO {
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
	    	sqle.printStackTrace();
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
