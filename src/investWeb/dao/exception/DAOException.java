package investWeb.dao.exception;


public class DAOException extends RuntimeException {
	public DAOException(String message, Throwable e) {
		super(message, e);
	}
}
