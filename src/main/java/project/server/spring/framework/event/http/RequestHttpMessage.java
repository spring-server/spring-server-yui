package project.server.spring.framework.event.http;

import project.server.spring.framework.http.HttpHeaders;
import project.server.spring.framework.http.RequestLine;
import project.server.spring.framework.utils.HttpRequestUtils;

public class RequestHttpMessage {
	private static final String CRLF = "\r\n";
	private static final String HEADER_END = "\r\n\r\n";

	public void parse(String message) {
		if (message.contains(HEADER_END)) {
			String[] elements = message.split(HEADER_END);
			if (elements.length == 1) {
				parseRequestLineAndHeaders(elements[0]);
				checkBody();
			} else {
				parseRequestLineAndHeaders(elements[0]);
				if (status == ParseStatus.COMPLETED) {
					throw new IllegalStateException("invalid http message");
				}
				httpBody = elements[1];
			}
		} else {
		}
	}

	private void checkBody() {
		int contentLength = httpHeaders.getContentLength();
		if (contentLength == -1) {
			status = ParseStatus.COMPLETED;
		}
	}

	private void parseRequestLineAndHeaders(String message) {
		String[] elements = message.split(CRLF);
		parseRequestLine(elements[0]);
		httpHeaders = new HttpHeaders();
		for (int i = 1; i < elements.length; i++) {
			addHttpHeader(elements[i]);
		}
	}

	private void parseRequestLine(String message) {
		requestLine = new RequestLine(message);
	}

	enum ParseStatus {
		START_LINE, HEADER, BODY, COMPLETED;
	}

	private ParseStatus status;
	private String remained;
	private RequestLine requestLine;
	private HttpHeaders httpHeaders;

	private String httpBody;

	private byte[] body;

	public RequestHttpMessage() {
		this.status = ParseStatus.START_LINE;
		this.httpHeaders = new HttpHeaders();
		this.remained = "";
		this.httpBody = "";
	}

	public void execute(String message) {
		remained += message;
		if (status == ParseStatus.HEADER || status == ParseStatus.START_LINE) {
			while (remained.contains(CRLF)) {
				int index = remained.indexOf(CRLF);
				String line = remained.substring(0, index);
				if (status == ParseStatus.START_LINE) {
					setRequestLine(line);
				} else if (status == ParseStatus.HEADER) {
					if (line.equals("")) {
						checkCompleted();
						remained = remained.substring(index + 2);
						break;
					}
					addHttpHeader(line);
				}
				remained = remained.substring(index + 2);
			}
		}
		if (status == ParseStatus.BODY) {
			httpBody += remained;
		}
		if (httpBody.getBytes().length >= httpHeaders.getContentLength()) {
			status = ParseStatus.COMPLETED;
		}
	}

	private void checkCompleted() {
		if (httpHeaders.getContentLength() == -1) {
			status = ParseStatus.COMPLETED;
			return;
		}
		status = ParseStatus.BODY;
	}

	public void setRequestLine(String message) {
		this.requestLine = new RequestLine(message);
		this.status = ParseStatus.HEADER;
	}

	private void addHttpHeader(String message) {
		HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(message);
		this.httpHeaders.add(pair.getKey(), pair.getValue());
	}

	public void readyForBody() {
		this.status = ParseStatus.BODY;
	}

	public void setHttpBody(String message) {
		if (httpBody == null) {
			httpBody = message;
		} else {
			httpBody += message;
		}
	}

	public boolean isCompleted() {
		return ParseStatus.COMPLETED.equals(status);
	}

	public HttpHeaders getHttpHeaders() {
		return httpHeaders;
	}

	public String getHttpBody() {
		return httpBody;
	}

	public RequestLine getRequestLine() {
		return requestLine;
	}
}
