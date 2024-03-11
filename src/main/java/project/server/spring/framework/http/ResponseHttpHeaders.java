package project.server.spring.framework.http;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseHttpHeaders {
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String SET_COOKIE = "Set-Cookie";
	public static final String CONNECTION = "Connection";
	private static final String EQUIVALENCE = "=";
	private static final String SEMICOLON = "; ";
	private static final String COMMA = ", ";
	private Cookies cookies = null;
	private final Map<String, String> headers = new LinkedHashMap<>();

	private boolean isKeepAlive = true;

	//정적 팩토리 메소드로?
	public ResponseHttpHeaders() {
		cookies = Cookies.empty();
		setContentType("text/html");
	}

	//TODO: type 고치기
	public ResponseHttpHeaders(boolean isBinaryData) {
		setContentType("application/octet-stream");
	}

	public void set(String key, String value) {
		headers.put(key, value);
	}

	public void setContentType(String mediaType) {
		set(CONTENT_TYPE, mediaType);
	}

	//TODO: keep-alive, close
	public void setConnection(String connectionValue) {
		set(CONNECTION, connectionValue);
	}

	public void setCookies(String key, String value) {
		set(SET_COOKIE, value);
	}
}
