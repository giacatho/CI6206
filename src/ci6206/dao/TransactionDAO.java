package ci6206.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

import ci6206.model.Constants;
import ci6206.model.Stock;
import ci6206.model.Transaction;
import ci6206.model.User;
import ci6206.servlet.Portfolio;


public class TransactionDAO extends AbstractDAO {
	Logger logger = Logger.getLogger(TransactionDAO.class);
	
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
	        String sql = new String("SELECT A.*, B.name FROM tb_trans A, tb_stock B WHERE A.userid=? and A.symbol=B.symbol ");
	    	ps = conn.prepareStatement(sql);
	    	ps.setString(1, userId);
	    	resSet = ps.executeQuery();
	        while(resSet.next())
	        {
	    	   trans = populate(true);
	    	   list.add(trans);
	        }
	    }
	    catch(SQLException sqle)
	    {
	    	logger.error(sqle.fillInStackTrace());
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
	        String sql = new String("SELECT A.*, B.Name FROM tb_trans A, tb_stock B WHERE A.symbol=? AND A.userid=? AND A.symbol=B.symbol");
	    	ps = conn.prepareStatement(sql);
	    	ps.setString(1, symbol);
	    	ps.setString(2, userId);
	    	resSet = ps.executeQuery();
	        while(resSet.next())
	        {
	    	   trans = populate(true);
	    	   list.add(trans);
	        }
	    }
	    catch(SQLException sqle)
	    {
	    	logger.error(sqle.fillInStackTrace());
	    }
	    finally
	    {
	    	cleanUp();
	    }
       return list;
    	
    }
    private Transaction populate(boolean isJoinStock) throws SQLException
    {
    	Transaction trans = new Transaction();
    	User user = new User();
    	
    	user.setUsername(resSet.getString("userid"));

    	Stock stock = new Stock();
    	stock.setSymbol(resSet.getString("symbol"));
    	stock.setPrice(resSet.getDouble("price"));
    	if(isJoinStock)
    	{
    		stock.setName(resSet.getString("B.name"));
    	}
    	trans.setAmount(resSet.getDouble("amt"));
    	trans.setQty(resSet.getInt("qty"));
    	trans.setStock(stock);
    	trans.setUser(user);
    	trans.setAction(resSet.getString("type"));
		Date date = resSet.getDate("date");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
		trans.setDate(df.format(date));

    	
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

			logger.info("Record is inserted into tb_trans table!");
	    	
    	}
    	catch(SQLException sqle)
        {
    		try
    		{
    		   conn.rollback();
    		}
    		catch(SQLException ignore){}
	    	logger.error(sqle.fillInStackTrace());

        }
        finally
        {
        	cleanUp();
        }    	

    }


}
