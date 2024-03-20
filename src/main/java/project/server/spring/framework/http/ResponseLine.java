package project.server.spring.framework.http;

public class ResponseLine {
	private static final String SPACE = " ";

	public enum HttpVersion {
		HTTP_1_0("HTTP/1.0"),
		HTTP_1_1("HTTP/1.1"),
		HTTP_2_0("HTTP/2.0");

		private final String versionString;

		HttpVersion(String versionString) {
			this.versionString = versionString;
		}

		@Override
		public String toString() {
			return versionString;
		}
	}

	private HttpVersion httpVersion;
	private HttpStatus httpStatus;

	public ResponseLine() {
		httpVersion = HttpVersion.HTTP_1_1;
		httpStatus = HttpStatus.OK;
	}

	public String makeLine() {
		return httpVersion.versionString + SPACE + httpStatus.getCode() + SPACE + httpStatus.getReasonPhrase();
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
