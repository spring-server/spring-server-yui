package project.server.spring.framework.http;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.utils.HttpRequestUtils;

public class HttpHeaders {

	private static final Logger log = LoggerFactory.getLogger(HttpHeaders.class);
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String AUTHORIZATION = "Authorization";
	public static final String CONNECTION = "Connection";
	public static final String COOKIE = "Cookie";
	public static final String SET_COOKIE = "Set-Cookie";
	public static final String HOST = "Host";
	private final Map<String, String> headers = new LinkedHashMap<>();
	private static final String EQUIVALENCE = "=";
	private static final String AND_INDEX = "; ";
	private Cookies cookies = null;

	void parse(String header) {
		HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(header);
		add(pair.getKey(), pair.getValue());
		if (COOKIE.equals(pair.getKey())) {
			cookies = Cookies.create(pair.getValue());
		}
	}

	public String get(String key) {
		return headers.get(key);
	}

	public void add(String key, String value) {
		headers.put(key, value);
	}

	public int getContentLength() {
		String value = get(CONTENT_LENGTH);
		return (value != null ? Integer.parseInt(value) : -1);
	}

	public void setContentLength(long contentLength) {
		String value = String.valueOf(contentLength);
		add(CONTENT_LENGTH, value);
	}

	public MediaType getContentType() {
		String value = get(CONTENT_TYPE);
		return MediaType.ofValue(value);
	}

	public void setContentType(MediaType mediaType) {
		String value = mediaType.getValue();
		add(CONTENT_TYPE, value);
	}

	public Set<String> getAllFields() {
		return headers.keySet();
	}

	public Cookies getCookies() {
		return cookies;
	}

	public void sendCookie(Cookies cookies) {
		for (String key : cookies.getAllKeys()) {
			String value = cookies.get(key);
			String headerValue = key + EQUIVALENCE + value;
			headers.put(SET_COOKIE, headerValue);
		}
	}

	public boolean contains(String name) {
		return headers.get(name) != null;
	}

	// public String makeLine() {
	// 	for (String headerField : headers.keySet()) {
	//
	// 	}
	// }
}
