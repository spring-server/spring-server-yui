package project.server.spring.framework.servlet;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.server.http.HttpHeaders;
import project.server.spring.server.http.HttpMethod;
import project.server.spring.server.http.HttpRequest;
import project.server.spring.server.http.HttpResponse;
import project.server.spring.server.http.MediaType;

public class HttpServletRequest {
	private static final Logger log = LoggerFactory.getLogger(HttpServletRequest.class);

	private HttpMethod httpMethod;
	private String path;
	private MediaType contentType;
	private String body;
	private HttpHeaders headers;
	//TODO: queryParam 클래스로 만들기
	private Map<String, String> queryParams;

	public HttpServletRequest(HttpRequest request) {
		this.httpMethod = request.getHttpMethod();
		this.path = request.getPath();
		this.contentType = request.getContentType();
		this.body = request.getBody();
		this.headers = request.getHeaders();
		this.queryParams = request.getQueryParams();
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getPath() {
		return path;
	}

	public MediaType getContentType() {
		return contentType;
	}

	public String getBody() {
		return body;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}
}
