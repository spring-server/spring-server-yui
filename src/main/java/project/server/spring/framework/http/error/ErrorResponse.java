package project.server.spring.framework.http.error;

public class ErrorResponse {
	private int code;
	private String message;

	private ErrorResponse() {
	}

	public ErrorResponse(
		int code,
		String message
	) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
