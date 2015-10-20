package ci6206.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import ci6206.dao.exception.DAOException;

public class PriceDAO extends AbstractDAO {
	Logger logger = Logger.getLogger(PriceDAO.class);
	
	
	/**
	 * Batch update stock prices based on symbol.
	 * 
	 * @param stockPrices Stock price map
	 *  @return Return true if success, otherwise return false.
	 * @throws DAOException DAO level exception
	 */
	public boolean updateStockPrice(Map<String, Double> stockPrices) throws DAOException {
		boolean result = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp ts = new Timestamp(new Date().getTime());
		
		try {	
	    	if (stockPrices != null && stockPrices.size() > 0) {
	    		String sql = "INSERT INTO TB_PRICE(symbol,price,lastupdate) VALUES(?,?,?)";
		    	ps = conn.prepareStatement(sql);
		    	
		    	Iterator<Entry<String,Double>> stockPriceIterator = stockPrices.entrySet().iterator();
		    	while (stockPriceIterator.hasNext()) {
		    		Entry<String, Double> stockPriceEntry = stockPriceIterator.next();
		    		String symbol = stockPriceEntry.getKey();
		    		Double price = stockPriceEntry.getValue();
		    		logger.debug("symbol = "+symbol);
		    		ps.setString(1, symbol);
		    		ps.setDouble(2, price);
		    		ps.setString(3, sdf.format(ts));
		    		ps.addBatch();
		    	}
		    	
		    	int rows[] = ps.executeBatch();
		        for (int row : rows) {
		        	if (row <= 0) {
		        		result = false;
		        	}
		        }
	    	}
	    } catch(SQLException sqle) {
	    	logger.error("Failed to update stock price"
	    			+" in updateStockPrice method in PriceDAO.", sqle);
	    	throw new DAOException("Failed to update stock price. ", sqle);
	    } finally {
	    	cleanUp();
	    }
		 
		return result;
	}
}
