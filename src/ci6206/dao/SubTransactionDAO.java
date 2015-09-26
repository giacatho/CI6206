package ci6206.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ci6206.model.SubTransaction;

public class SubTransactionDAO extends AbstractDAO {
	public List<SubTransaction> getSubTransactions(String userId) {
		List<SubTransaction> subTransList = new ArrayList<SubTransaction>();
		
		try {
			String sql = "" 
					// Buy
					+ " SELECT 'Buy' AS type, a.symbol AS stock_symbol, b.name AS stock_name, a.buyqty AS qty, a.buyprice AS price, a.buydate AS trandate "
					+ " FROM tb_trans a "
					+ " LEFT JOIN tb_stock b "
					+ "    ON a.symbol = b.symbol "
					+ " WHERE a.userid = ? " 
					+ " UNION "
					// Sell
					+ " SELECT 'Sell' AS type, a.symbol AS stock_symbol, b.name AS stock_name, a.sellqty AS qty, a.sellprice AS price, a.selldate AS trandate "
					+ " FROM tb_trans a "
					+ " LEFT JOIN tb_stock b "
					+ "    ON a.symbol = b.symbol "
					+ " WHERE a.userid = ? AND a.selldate != '0000-00-00' " 
					// Order
					+ " ORDER BY trandate DESC ";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, userId);
			
			resSet = ps.executeQuery();
			while (resSet.next()) {
				subTransList.add(populate());
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally
		{
			cleanUp();
		}
		
		return subTransList;
	}
	
	private SubTransaction populate() throws SQLException
	{
		SubTransaction subTran = new SubTransaction();
		subTran.setType(resSet.getString("type"));
		subTran.setStockSymbol(resSet.getString("stock_symbol"));
		subTran.setStockName(resSet.getString("stock_name"));
		subTran.setPrice(resSet.getDouble("price"));
		subTran.setQty(resSet.getInt("qty"));
		subTran.setTransactionDate(resSet.getDate("trandate"));
		
		return subTran;
	}
}
