package project.server.spring.app.core.global.error;

public enum ErrorMessage {
	AUTHENTICATION_FAILED("authentication is needed"),
	USER_DUPLICATED("user is duplicated"),
	EMAIL_DUPLICATED("email already registered"),
	NOT_FOUND("%s does not exist"),
	INVALID_PASSWORD("invalid password"),
	INVALID_FORMAT("%s format is invalid");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
