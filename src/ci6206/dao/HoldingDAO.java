package ci6206.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ci6206.model.Holding;

public class HoldingDAO extends AbstractDAO {
	public List<Holding> getHoldings(String userId) {
		List<Holding> holdingList = new ArrayList<Holding>();
		
		try {
			String sql = ""
					+ " SELECT a.symbol AS stock_symbol, a.name AS stock_name, b.total_buy_qty - c.total_sell_qty AS holding_qty, d.market_price " 
					+ " FROM tb_stock a "
					+ "    INNER JOIN "
					// Buy
					+ " (SELECT symbol, SUM(buyqty) AS total_buy_qty "
					+ " FROM tb_trans "
					+ " WHERE userid = ? "
					+ " GROUP BY symbol) b "
					+ "       ON a.symbol = b.symbol "
					+ "    INNER JOIN "
					// Sell
					+ " (SELECT symbol, SUM(sellqty) AS total_sell_qty "
					+ " FROM tb_trans "
					+ " WHERE userid = ? AND selldate != '0000-00-00' " 
					+ " GROUP BY symbol) c "
					+ "       ON a.symbol = c.symbol " 
					+ "    INNER JOIN "
					// Market Price
					+ " (SELECT symbol, MAX(lastupdate), price AS market_price "
					+ " FROM tb_price "
					+ " GROUP BY symbol) d " 
					+ "       ON a.symbol = d.symbol "
					// Order
					+ " ORDER BY a.symbol ";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, userId);
			
			resSet = ps.executeQuery();
			while (resSet.next()) 
			{
				Holding holding = populate();
				if (holding.getQty() > 0) {
					holdingList.add(populate());
				}
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally
		{
			cleanUp();
		}
		
		return holdingList;
	}
	
	private Holding populate() throws SQLException
	{
		Holding holding = new Holding();
		holding.setStockSymbol(resSet.getString("stock_symbol"));
		holding.setStockName(resSet.getString("stock_name"));
		holding.setMarketPrice(resSet.getDouble("market_price"));
		holding.setQty(resSet.getInt("holding_qty"));
		
		return holding;
	}
}
