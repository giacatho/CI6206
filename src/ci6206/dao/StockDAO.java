package ci6206.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ci6206.model.Stock;
import ci6206.model.User;
import ci6206.model.Constants;

public class StockDAO extends AbstractDAO 
{
	Logger logger = Logger.getLogger(StockDAO.class);

	
    public StockDAO()
    {
    	super();
    }
    
    public ArrayList<Stock> GetStocksStartWith(String beginStr)
    {
    	ArrayList<Stock> resultList = new ArrayList<Stock>();
    	/*
	        String sql = "SELECT A.name, A.symbol, A.exchange, B.price, B.lastupdate ";
	        StringBuffer sb = new StringBuffer(sql);
	        sb.append("FROM tb_stock A, tb_price B ");
	        sb.append("WHERE A.status = 'A' AND ");
	        sb.append("A.exchange='SGX' AND A.symbol = B.symbol AND ");
	        sb.append("A.name like ? ");
	        sb.append("AND B.lastupdate = ");
	        sb.append("(SELECT MAX( lastupdate ) FROM tb_price WHERE symbol = A.symbol )");
        */
    	StringBuffer sb = buildMainSQL();
    	sb.append("AND A.name like ? ");
    	try
        {
    		//super.OpenConnection();
	    	ps = conn.prepareStatement(sb.toString());
	    	ps.setString(1, beginStr+"%");
	    	resSet = ps.executeQuery();
	    	while(resSet.next())
	    	{
	    		resultList.add(populate());
	    	}
        }
        catch(SQLException sqle)
        {
        	//sqle.printStackTrace();
        	logger.error(sqle.fillInStackTrace());
        }
        finally
        {
        	cleanUp();
        }
    	
    	return resultList;
    }
    
    public Stock GetStock(String symbol)
    {
    	
    	Stock stock = null;
    	StringBuffer sb = buildMainSQL();
    	sb.append("AND A.symbol=?");
        try
        {
        	//super.OpenConnection();
	    	ps = conn.prepareStatement(sb.toString());
	    	ps.setString(1, symbol);
	    	resSet = ps.executeQuery();
	    	if(resSet.next())
	    	{
	    		stock = populate();
	    	}
        }
        catch(SQLException sqle)
        {
        	//sqle.printStackTrace();
        	logger.error(sqle.fillInStackTrace());

        }
        finally
        {
        	cleanUp();
        }
    	
    	return stock;
    	
    }
    

    private StringBuffer buildMainSQL()
    {
        String sql = "SELECT A.name, A.symbol, A.exchange, B.price, B.lastupdate ";
        StringBuffer sb = new StringBuffer(sql);
        sb.append("FROM tb_stock A, tb_price B ");
        sb.append("WHERE A.status = 'A' AND ");
        sb.append("A.exchange='SGX' AND A.symbol = B.symbol ");
        sb.append("AND B.lastupdate = ");
        sb.append("(SELECT MAX( lastupdate ) FROM tb_price WHERE symbol = A.symbol )");
        
        return sb;

    }
    private Stock populate() throws SQLException
    {
    	Stock stock = new Stock();
    	stock.setSymbol(resSet.getString("symbol"));
    	stock.setName(resSet.getString("name"));
    	stock.setPrice(resSet.getDouble("price"));
    	stock.setExchange(resSet.getString("exchange"));
    	stock.setLastupdate(resSet.getDate("lastupdate"));
    	return stock;
    }

}
