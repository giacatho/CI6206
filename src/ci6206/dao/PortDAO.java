package ci6206.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import ci6206.model.Constants;
import ci6206.model.Stock;
import ci6206.model.User;


public class PortDAO extends AbstractDAO {
	
    public void Trade(String action, Stock stock, User user)
    {
    	
    	String sql = "";
    	if(action!=null)
    	{
	    	if(action.equals(Constants.BUY))
	    	{
	    		sql = buildInsertSQL();
	    		buy(stock, user, sql);
	    	}
    	}
    }
    
   
    private String buildInsertSQL()
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("INSERT INTO tb_trans(");
    	sb.append("userid,symbol,buydate,buyprice,buyqty,buyamt) VALUES(?,?,?,?,?,?)");
    	
    	return sb.toString();

    }
    
    private void buy(Stock stock, User user, String sql)
    {
    	try
    	{
    		java.sql.Date now = new Date(Calendar.getInstance().getTimeInMillis());
   	        ps = conn.prepareStatement(sql);
	    	ps.setString(1, user.getUsername());
	    	ps.setString(2, stock.getSymbol());
	    	ps.setDate(3, now);
	    	ps.setDouble(4, stock.getPrice());
	    	ps.setInt(5, stock.getQuantity());
	    	ps.setDouble(6, stock.getAmount());
	    	
			ps.executeUpdate();

			System.out.println("Record is inserted into table!");
	    	
    	}
    	catch(SQLException sqle)
        {
        	sqle.printStackTrace();
        }
        finally
        {
        	cleanUp();
        }    	

    }
	

}
