package dst2.server.utils.exceptions.jobmanagement;

public class CacheEmptyException extends Exception{

	public CacheEmptyException() {
		super();
	}

	public CacheEmptyException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CacheEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheEmptyException(String message) {
		super(message);
	}

	public CacheEmptyException(Throwable cause) {
		super(cause);
	}

	
}
