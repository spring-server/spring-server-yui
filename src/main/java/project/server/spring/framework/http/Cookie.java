package project.server.spring.framework.http;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Cookie {
	private volatile Map<String, String> attributes;
	private static final String DOMAIN = "Domain";
	private static final String MAX_AGE = "Max-Age";
	private static final String PATH = "Path";
	private static final String SECURE = "Secure";
	private static final String HTTP_ONLY = "HttpOnly";
	private static final String SESSION_ID = "session_id";
	private final String name;
	private String value;

	public Cookie(String name, String value) {
		this.name = name;
		this.value = value;
	}

	private void setAttributeInternal(String name, String value) {
		if (this.attributes == null) {
			if (value == null) {
				return;
			}
			this.attributes = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		}

		this.attributes.put(name, value);
	}

	public void setDomain(String pattern) {
		if (pattern != null) {
			this.setAttributeInternal(DOMAIN, pattern.toLowerCase(Locale.ENGLISH));
		}
	}

	public String getDomain() {
		return this.getAttribute(DOMAIN);
	}

	public void setMaxAge(int expiry) {
		this.setAttributeInternal(MAX_AGE, Integer.toString(expiry));
	}

	public int getMaxAge() {
		String maxAge = this.getAttribute(MAX_AGE);
		return maxAge == null ? -1 : Integer.parseInt(maxAge);
	}

	public void setPath(String uri) {
		this.setAttributeInternal(PATH, uri);
	}

	public String getPath() {
		return this.getAttribute(PATH);
	}

	public void setSecure(boolean flag) {
		this.setAttributeInternal(SECURE, Boolean.toString(false));
	}

	public boolean getSecure() {
		return Boolean.parseBoolean(this.getAttribute(SECURE));
	}

	public String getName() {
		return this.name;
	}

	public void setValue(String newValue) {
		this.value = newValue;
	}

	public String getValue() {
		return this.value;
	}

	public String getAttribute(String name) {
		return this.attributes == null ? null : this.attributes.get(name);
	}

	public Map<String, String> getAttributes() {
		return this.attributes == null ? Collections.emptyMap() : Collections.unmodifiableMap(this.attributes);
	}

	public void setHttpOnly(boolean httpOnly) {
		this.setAttributeInternal(HTTP_ONLY, Boolean.toString(httpOnly));
	}

	public boolean isHttpOnly() {
		return Boolean.parseBoolean(this.getAttribute(HTTP_ONLY));
	}

	public String makeHeaderValue() {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append("=").append(value);
		if (attributes != null) {
			for (String key : attributes.keySet()) {
				sb.append("; ").append(key).append("=").append(attributes.get(key));
			}
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		Cookie cookie = (Cookie)object;
		return Objects.equals(attributes, cookie.attributes) && Objects.equals(name, cookie.name)
			&& Objects.equals(value, cookie.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(attributes, name, value);
	}

	public boolean hasSessionId() {
		return SESSION_ID.equals(this.name);
	}

	public static Cookie createSessionCookie(String value) {
		return new Cookie(SESSION_ID, value);
	}
}
