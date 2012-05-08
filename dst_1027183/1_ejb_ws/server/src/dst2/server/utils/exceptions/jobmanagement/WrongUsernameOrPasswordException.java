package dst2.server.utils.exceptions.jobmanagement;

public class WrongUsernameOrPasswordException extends Exception {

	
	
	public WrongUsernameOrPasswordException() {
		super();
	}

	public WrongUsernameOrPasswordException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WrongUsernameOrPasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongUsernameOrPasswordException(String message) {
		super(message);
	}

	public WrongUsernameOrPasswordException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8693298565587991649L;


}
