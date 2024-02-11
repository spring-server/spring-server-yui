package project.server.spring.framework.http;

import java.util.Map;

public class HttpBody {
	private final Map<String, String> params;

	public HttpBody(Map<String, String> params) {
		this.params = params;
	}

	public String get(String key) {
		return params.get(key);
	}
}
