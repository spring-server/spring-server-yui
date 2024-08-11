package project.server.spring.framework.servlet.mvc;

import com.google.common.base.Objects;

import project.server.spring.framework.http.HttpMethod;

public class RequestMappingInfo {
	private final HttpMethod httpMethod;
	private final String url;

	public RequestMappingInfo(HttpMethod httpMethod, String url) {
		this.httpMethod = httpMethod;
		this.url = url;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		RequestMappingInfo that = (RequestMappingInfo)object;
		return httpMethod == that.httpMethod && Objects.equal(url, that.url);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(httpMethod, url);
	}

}
