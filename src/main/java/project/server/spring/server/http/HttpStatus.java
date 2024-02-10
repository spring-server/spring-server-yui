package project.server.spring.server.http;

public enum HttpStatus {
	OK(200, "OK"),
	CREATED(201, "Created"),
	NO_CONTENT(204, "No Content"),
	MOVED_PERMANENTLY(301, "Moved Permanently"),
	MOVED_TEMPORARILY(302, "Moved Temporarily"),
	BAD_REQUEST(400, "Bad Request"),
	UNAUTHORIZED(401, "Unauthorized"),
	FORBIDDEN(403, "Forbidden"),
	NOT_FOUND(404, "Not Found"),
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	CONFLICT(409, "Conflict"),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error");

	private final int code;
	private final String reasonPhrase;

	HttpStatus(int code, String reasonPhrase) {
		this.code = code;
		this.reasonPhrase = reasonPhrase;
	}

	public static HttpStatus valueOf(int statusCode) {
		for (HttpStatus status : HttpStatus.values()) {
			if (status.code == statusCode) {
				return status;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
	}

	public int getCode() {
		return code;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	@Override
	public String toString() {
		return String.format("code: %d, reason phrase: %s", code, reasonPhrase);
	}
}
