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
        sb.append("FROM tb_stock A, tb_price B ");
        sb.append("WHERE A.status = 'A' AND ");
        sb.append("A.exchange='SGX' AND A.symbol = B.symbol AND ");
        sb.append("A.name like ? ");
        sb.append("AND B.lastupdate = ");
        sb.append("(SELECT MAX( lastupdate ) FROM tb_price WHERE symbol = A.symbol )");
        
        try
        {
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
        	sqle.printStackTrace();
        }
        finally
        {
        	cleanUp();
        }
    	
    	return resultList;
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
