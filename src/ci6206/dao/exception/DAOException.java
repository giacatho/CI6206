package ci6206.dao.exception;

/**
 *DAO level exception
 *
 * Author Qiao Guo Jun
 * Date Sep 28, 2015
 * Version 1.0 
 */
public class DAOException extends RuntimeException {
	public DAOException(String message, Throwable e) {
		super(message, e);
	}
}
