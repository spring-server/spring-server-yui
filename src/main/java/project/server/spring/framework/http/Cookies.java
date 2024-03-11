package project.server.spring.framework.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import project.server.spring.framework.utils.HttpRequestUtils;

public class Cookies {
	private static final String SESSION_ID = "session_id";
	private final Map<String, String> cookieMap;

	private Cookies(Map<String, String> cookieMap) {
		this.cookieMap = cookieMap;
	}

	//TODO:정적 메소드를 좀 더 고민해보자.
	public static Cookies create(String cookiesMessage) {
		Map<String, String> cookieMap = HttpRequestUtils.parseCookies(cookiesMessage);
		return new Cookies(cookieMap);
	}

	public static Cookies create(String key, String value) {
		Map<String, String> cookieMap = new HashMap<>();
		cookieMap.put(key, value);
		return new Cookies(cookieMap);
	}

	public static Cookies empty() {
		return new Cookies(new HashMap<>());
	}

	public String get(String key) {
		if (key == null) {
			throw new IllegalArgumentException("cookie key is not found");
		}
		return cookieMap.get(key);
	}

	public Set<String> getAllKeys() {
		return cookieMap.keySet();
	}

	public String getSessionId() {
		return cookieMap.get(SESSION_ID);
	}
}
