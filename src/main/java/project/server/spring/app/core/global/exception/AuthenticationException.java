package project.server.spring.app.core.global.exception;

import project.server.spring.framework.http.HttpStatus;

public class AuthenticationException extends BusinessException {
	public AuthenticationException(String message) {
		super(message, HttpStatus.UNAUTHORIZED);
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause, HttpStatus.UNAUTHORIZED);
	}
}
