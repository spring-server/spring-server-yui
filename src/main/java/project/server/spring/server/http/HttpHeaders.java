package project.server.spring.server.http;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.server.common.utils.HttpRequestUtils;

public class HttpHeaders {

	private static final Logger log = LoggerFactory.getLogger(HttpHeaders.class);
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String AUTHORIZATION = "Authorization";
	public static final String CONNECTION = "Connection";
	public static final String COOKIE = "Cookie";
	public static final String HOST = "Host";
	private final Map<String, String> headers = new LinkedHashMap<>();

	//TODO: header 파싱 역할 분리??
	void parse(String header) {
		log.debug("header : {}", header);
		HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(header);
		add(pair.getKey(), pair.getValue());
	}

	public String get(String key) {
		return headers.get(key);
	}
	void add(String key, String value) {
		headers.put(key, value);
	}

	public long getContentLength() {
		String value = get(CONTENT_LENGTH);
		return (value != null ? Long.parseLong(value) : -1);
	}

	public MediaType getContentType() {
		String value = get(CONTENT_TYPE);
		return (MediaType.ofValue(value));
	}

	public Set<String> getAllFields() {
		return headers.keySet();
	}
}
