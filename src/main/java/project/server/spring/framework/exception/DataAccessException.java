package project.server.spring.framework.exception;

public class DataAccessException extends RuntimeException {
	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException() {
		super();
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
