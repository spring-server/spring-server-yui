package project.server.spring.framework.exception;

import project.server.spring.framework.http.HttpStatus;

public class NestedRuntimeException extends RuntimeException {
	public NestedRuntimeException() {
	}

	public NestedRuntimeException(String message) {
		super(message);
	}

	public NestedRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NestedRuntimeException(Throwable cause) {
		super(cause);
	}

	public NestedRuntimeException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@Override
	public String toString() {
		return String.format(
			"{\"code\":%d, \"message\":\"%s\"}",
			HttpStatus.INTERNAL_SERVER_ERROR.getCode(),
			getMessage()
		);
	}
}
