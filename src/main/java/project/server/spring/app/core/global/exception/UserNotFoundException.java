package project.server.spring.app.core.global.exception;

import project.server.spring.framework.http.HttpStatus;

public class UserNotFoundException extends BusinessException {
	public UserNotFoundException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause, HttpStatus.NOT_FOUND);
	}
}
