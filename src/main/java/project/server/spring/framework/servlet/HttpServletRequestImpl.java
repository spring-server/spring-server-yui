package project.server.spring.framework.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.http.HttpHeaders;
import project.server.spring.framework.http.HttpMethod;
import project.server.spring.framework.http.HttpRequest;
import project.server.spring.framework.http.MediaType;
import project.server.spring.framework.http.QueryParams;

public class HttpServletRequestImpl implements HttpServletRequest {
	private static final Logger log = LoggerFactory.getLogger(HttpServletRequestImpl.class);

	private HttpMethod httpMethod;
	private String path;
	private MediaType contentType;
	private char[] body;
	private HttpHeaders headers;
	private QueryParams queryParams;

	private final HttpRequest request;

	@Override
	public String getRequestURI() {
		return path;
	}

	public HttpServletRequestImpl(HttpRequest request) {
		this.httpMethod = request.getHttpMethod();
		this.path = request.getPath();
		this.contentType = request.getContentType();
		this.body = request.getBody();
		this.headers = request.getHeaders();
		this.queryParams = request.getQueryParams();
		this.request = request;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	@Override
	public MediaType getContentType() {
		return contentType;
	}

	@Override
	public char[] getBody() {
		return body;
	}

	@Override
	public HttpHeaders getHeaders() {
		return headers;
	}

	@Override
	public QueryParams getQueryParams() {
		return queryParams;
	}

	@Override
	public String getHeader(String name) {
		return headers.get(name);
	}

	@Override
	public int getContentLength() {
		return body.length;
	}
}
