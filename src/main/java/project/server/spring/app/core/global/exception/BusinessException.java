package project.server.spring.app.core.global.exception;

import project.server.spring.framework.http.HttpStatus;

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
