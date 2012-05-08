package dst2.server.utils.exceptions.jobmanagement;

public class NotEnoughCPUException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 635484082395962746L;

	public NotEnoughCPUException() {
		super();
	}

	public NotEnoughCPUException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotEnoughCPUException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEnoughCPUException(String message) {
		super(message);
	}

	public NotEnoughCPUException(Throwable cause) {
		super(cause);
	}

	
}
