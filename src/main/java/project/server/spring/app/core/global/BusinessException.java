package project.server.spring.app.core.global;

import project.server.spring.server.http.HttpStatus;

public class BusinessException extends RuntimeException {
	private final HttpStatus status;

	public BusinessException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public BusinessException(String message, Throwable cause, HttpStatus status) {
		super(message, cause);
		this.status = status;
	}
}
