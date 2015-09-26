package ci6206.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import ci6206.model.Constants;
import ci6206.model.Stock;
import ci6206.model.Transaction;
import ci6206.model.User;


public class TransactionDAO extends AbstractDAO {
	
	
    public void Trade(Transaction trans)
    {
    	
    	String sql = "";
    	String action = trans.getAction();
    	if(action!=null)
    	{
	    	if(action.equals(Constants.BUY))
	    	{
	    		sql = buildBuySQL();
	    		buy(trans, sql);
	    	}
	    	else if (action.equals(Constants.SELL))
	    	{
	    		sql = buildSellSQL();
	    		sell(trans, sql);
	    	}
	    	else if (action.equals(Constants.ADD))
	    	{
	    		sql = buildAddSQL();
	    		add(trans,sql);
	    		
	    	}
	    	else if (action.equals(Constants.REDUCE))
	    	{
	    		sql = buildReduceSQL();
	    		reduce(trans,sql);
	    	}
    	}
    }
    
    
    public Transaction getTransaction(String symbol, String userId)
    {
    	Transaction trans = null;
    	
	    try
	    {	
	    	//super.OpenConnection();
	        String sql = new String("SELECT * FROM tb_trans WHERE symbol=? AND userid=?");
	    	ps = conn.prepareStatement(sql);
	    	ps.setString(1, symbol);
	    	ps.setString(2, userId);
	    	resSet = ps.executeQuery();
	       if(resSet.next())
	       {
	    	   trans = populate();
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
       return trans;
    	
    }
   
    private Transaction populate() throws SQLException
    {
    	Transaction trans = new Transaction();
    	User user = new User();
    	
    	user.setUsername(resSet.getString("userid"));

    	Stock buyStock = new Stock();
    	buyStock.setSymbol(resSet.getString("symbol"));
    	buyStock.setPrice(resSet.getDouble("buyprice"));
    	trans.setBuyAmount(resSet.getDouble("buyamt"));
    	trans.setBuyQuantity(resSet.getInt("buyqty"));
    	Stock sellStock = new Stock();
    	sellStock.setSymbol(buyStock.getSymbol());
    	sellStock.setPrice(resSet.getDouble("sellprice"));
    	trans.setSellAmount(resSet.getDouble("sellamt"));
    	trans.setSellQuantity(resSet.getInt("sellqty"));
    	
    	trans.setBuyStock(buyStock);
    	trans.setSellStock(sellStock);
    	trans.setUser(user);
    	
    	return trans;
    	
    }

    private String buildReduceSQL()
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("UPDATE tb_trans ");
    	sb.append("set buyqty=?, buyamt=?, selldate=?, sellprice=?, sellqty=?, profit=?, sellamt=? ");
    	sb.append("WHERE symbol=? AND userid=?");
    	return sb.toString();
    }

    
    private String buildAddSQL()
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("UPDATE tb_trans ");
    	sb.append("set buydate=?, buyprice=?, buyqty=?, buyamt=? ");
    	sb.append("WHERE symbol=? AND userid=?");
    	return sb.toString();
    }
    private String buildBuySQL()
    {
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("INSERT INTO tb_trans(");
    	sb.append("userid,symbol,buydate,buyprice,buyqty,buyamt) VALUES(?,?,?,?,?,?)");
    	
    	return sb.toString();

    }

    
    private String buildSellSQL()
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("update tb_trans set selldate=?, sellprice=?, sellqty=?, profit=?, sellamt=?, buyqty=?, buyamt=? ");
    	sb.append("where userid=? and symbol=?");
    	
    	return sb.toString();

    }

    private void reduce(Transaction trans, String sql)
    {
    	try
    	{
    		//super.OpenConnection();
    		java.sql.Date now = new Date(Calendar.getInstance().getTimeInMillis());
   	        ps = conn.prepareStatement(sql);
	    	ps.setInt(1, trans.getBuyQuantity());
	    	ps.setDouble(2, trans.getBuyAmount());
	    	ps.setDate(3, now);
	    	ps.setDouble(4, trans.getSellStock().getPrice());
	    	ps.setInt(5, trans.getSellQuantity());
	    	ps.setDouble(6, trans.getProfit());
	    	ps.setDouble(7, trans.getSellAmount());
	    	ps.setString(8, trans.getSellStock().getSymbol());
	    	ps.setString(9, trans.getUser().getUsername());
	    	
			ps.executeUpdate();

			System.out.println("Record is updated into table for reduce position!");
	    	
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
    private void sell(Transaction trans, String sql)
    {
    	try
    	{
    		//super.OpenConnection();
    		java.sql.Date now = new Date(Calendar.getInstance().getTimeInMillis());
   	        ps = conn.prepareStatement(sql);
	    	ps.setDate(1, now);
	    	ps.setDouble(2, trans.getSellStock().getPrice());
	    	ps.setInt(3, trans.getSellQuantity());
	    	ps.setDouble(4, trans.getProfit());
	    	ps.setDouble(5, trans.getSellAmount());
	    	ps.setInt(6, trans.getBuyQuantity());
	    	ps.setDouble(7, trans.getBuyAmount());	
	    	ps.setString(8, trans.getUser().getUsername());
	    	ps.setString(9, trans.getSellStock().getSymbol());
	    	
			ps.executeUpdate();

			System.out.println("Record is updated into table!");
	    	
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
	
    private void buy(Transaction trans, String sql)
    {
    	try
    	{
    		//super.OpenConnection();
    		java.sql.Date now = new Date(Calendar.getInstance().getTimeInMillis());
   	        ps = conn.prepareStatement(sql);
   	        
	    	ps.setString(1, trans.getUser().getUsername());
	    	ps.setString(2, trans.getBuyStock().getSymbol());
	    	ps.setDate(3, now);
	    	ps.setDouble(4, trans.getBuyStock().getPrice());
	    	ps.setInt(5, trans.getBuyQuantity());
	    	ps.setDouble(6, trans.getBuyAmount());
	    	
			ps.executeUpdate();

			System.out.println("Record is inserted into table!");
	    	
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

    private void add(Transaction trans, String sql)
    {
    	try
    	{
    		//super.OpenConnection();
    		java.sql.Date now = new Date(Calendar.getInstance().getTimeInMillis());
   	        ps = conn.prepareStatement(sql);
	    	ps.setDate(1, now);
	    	ps.setDouble(2, trans.getBuyStock().getPrice());
	    	ps.setInt(3, trans.getBuyQuantity());
	    	ps.setDouble(4, trans.getBuyAmount());
	    	ps.setString(5, trans.getBuyStock().getSymbol());
	    	ps.setString(6, trans.getUser().getUsername());
	    	
			ps.executeUpdate();

			System.out.println("Record updated into table for Add position!");
	    	
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

}
