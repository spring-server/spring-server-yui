package project.server.spring.app.core.global.exception;

import project.server.spring.framework.http.HttpStatus;

public class InvalidParameterException extends BusinessException {
	public InvalidParameterException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}

	public InvalidParameterException(String message, Throwable cause,
		HttpStatus status) {
		super(message, cause, HttpStatus.BAD_REQUEST);
	}
}
