package ci6206.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
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
    	if(action!=null&&!action.isEmpty())
    	{
    		sql = buildInsertSQL(); 
	    	tradeStmt(trans, sql);
    	}
    }
    public int getTotalQty(String symbol, String userId)
    {
    	int sum=0;
    	ArrayList<Transaction> list = getTransactionList(symbol,userId);
    	for (int i=0; i<list.size();i++)
    	{
    		Transaction trans = list.get(i);
    		if(trans.getAction().equals(Constants.BUY))
    		{
    			sum = sum + trans.getQty();
    		}
    		else if (trans.getAction().equals(Constants.SELL))
    		{
    			sum = sum - trans.getQty();
    		}
    	}
    	return sum;
    }
    public ArrayList<Transaction> getTransactionList(String userId)
    {
    	ArrayList<Transaction> list = new ArrayList<Transaction>();
    	Transaction trans = null;
    	
	    try
	    {	
	    	//super.OpenConnection();
	        String sql = new String("SELECT * FROM tb_trans WHERE userid=?");
	    	ps = conn.prepareStatement(sql);
	    	ps.setString(1, userId);
	    	resSet = ps.executeQuery();
	        while(resSet.next())
	        {
	    	   trans = populate();
	    	   list.add(trans);
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
       return list;
    	
    }
    
    public ArrayList<Transaction> getTransactionList(String symbol, String userId)
    {
    	ArrayList<Transaction> list = new ArrayList<Transaction>();
    	Transaction trans = null;
    	
	    try
	    {	
	    	//super.OpenConnection();
	        String sql = new String("SELECT * FROM tb_trans WHERE symbol=? AND userid=?");
	    	ps = conn.prepareStatement(sql);
	    	ps.setString(1, symbol);
	    	ps.setString(2, userId);
	    	resSet = ps.executeQuery();
	        while(resSet.next())
	        {
	    	   trans = populate();
	    	   list.add(trans);
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
       return list;
    	
    }
    private Transaction populate() throws SQLException
    {
    	Transaction trans = new Transaction();
    	User user = new User();
    	
    	user.setUsername(resSet.getString("userid"));

    	Stock stock = new Stock();
    	stock.setSymbol(resSet.getString("symbol"));
    	stock.setPrice(resSet.getDouble("price"));
    	trans.setAmount(resSet.getDouble("amt"));
    	trans.setQty(resSet.getInt("qty"));
    	trans.setStock(stock);
    	trans.setUser(user);
    	trans.setAction(resSet.getString("type"));
    	
    	return trans;
    	
    }

    private String buildInsertSQL()
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("INSERT INTO tb_trans(");
    	sb.append("userid,symbol,type,date,price,qty,amt) VALUES(?,?,?,?,?,?,?)");
    	
    	return sb.toString();
    	
    }
    
    private void tradeStmt(Transaction trans, String sql)
    {
    	try
    	{
    		//super.OpenConnection();
    		java.sql.Date now = new Date(Calendar.getInstance().getTimeInMillis());
   	        ps = conn.prepareStatement(sql);
   	        
	    	ps.setString(1, trans.getUser().getUsername());
	    	ps.setString(2, trans.getStock().getSymbol());
	    	ps.setString(3, trans.getAction());
	    	ps.setDate(4, now);
	    	ps.setDouble(5, trans.getStock().getPrice());
	    	ps.setInt(6, trans.getQty());
	    	ps.setDouble(7, trans.getAmount());
	    	
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


}
