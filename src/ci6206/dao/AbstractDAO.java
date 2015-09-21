package ci6206.dao;

import java.sql.Connection;

public abstract class AbstractDAO {
    Connection conn=null;
	public AbstractDAO()
	{
		DBFactory dbFactory = new DBFactory();
		conn = dbFactory.getConnection();
	}
}
