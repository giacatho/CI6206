package ci6206.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ci6206.model.Stock;
import ci6206.model.User;

public class StockDAO extends AbstractDAO 
{
    public StockDAO()
    {
    	super();
    }
    
    public ArrayList<Stock> GetStocksStartWith(String beginStr)
    {
    	ArrayList<Stock> resultList = new ArrayList<Stock>();
        String sql = "SELECT A.name, A.symbol, A.exchange, B.price, B.lastupdate ";
        StringBuffer sb = new StringBuffer(sql);
        sb.append("FROM tb_stock A ");
        sb.append("INNER JOIN tb_price B ");
        sb.append("ON A.symbol = B.symbol ");
        sb.append("WHERE A.status = 'A' and A.name like ? ");
        sb.append("ORDER BY A.name, B.lastupdate desc ");
        
        try
        {
	    	ps = conn.prepareStatement(sb.toString());
	    	ps.setString(1, beginStr+"%");
	    	resSet = ps.executeQuery();
	    	int i = 0;
	    	while(resSet.next())
	    	{
    			String symbol = resSet.getString("symbol");
	    		
	    		if(i>0)
	    		{
	    			Stock stock = resultList.get(i);
	    			if(!symbol.equalsIgnoreCase(stock.getSymbol()))
	    			{
	    				resultList.add(populate(symbol));
	    			}
	    		}
	    		else if (i==0)
	    		{
	    			resultList.add(populate(symbol));
	    		}
	    		
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
    	
    	return resultList;
    }
    
    private Stock populate(String symbol) throws SQLException
    {
    	Stock stock = new Stock();
    	stock.setSymbol(symbol);
    	stock.setName(resSet.getString("name"));
    	stock.setPrice(resSet.getDouble("price"));
    	stock.setExchange(resSet.getString("exchange"));
    	stock.setLastupdate(resSet.getDate("lastupdate"));
    	return stock;
    }

}
