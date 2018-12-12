package crawler;

public class CrawlingException extends Exception {

	private static final long serialVersionUID = 1L;

	public CrawlingException(String message) {
		super(message);
	}

	public CrawlingException(Throwable cause) {
		super(cause);
	}

	public CrawlingException(String message, Throwable cause) {
		super(message, cause);
	}

}
