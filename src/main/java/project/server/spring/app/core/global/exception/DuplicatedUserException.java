package project.server.spring.app.core.global.exception;

import project.server.spring.framework.http.HttpStatus;

public class DuplicatedUserException extends BusinessException {
	public DuplicatedUserException(String message) {
		super(message, HttpStatus.CONFLICT);
	}

	public DuplicatedUserException(String message, Throwable cause) {
		super(message, cause, HttpStatus.CONFLICT);
	}
}
