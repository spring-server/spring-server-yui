package project.server.spring.framework.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.server.http.HttpHeaders;
import project.server.spring.server.http.HttpMethod;
import project.server.spring.server.http.HttpRequest;
import project.server.spring.server.http.MediaType;
import project.server.spring.server.http.QueryParams;

public class HttpServletRequest {
	private static final Logger log = LoggerFactory.getLogger(HttpServletRequest.class);

	private HttpMethod httpMethod;
	private String path;
	private MediaType contentType;
	private String body;
	private HttpHeaders headers;
	//TODO: queryParam 클래스로 만들기
	private QueryParams queryParams;

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

	public QueryParams getQueryParams() {
		return queryParams;
	}
}
