package project.server.spring.framework.event.http;

import project.server.spring.framework.http.HttpHeaders;
import project.server.spring.framework.http.HttpMethod;
import project.server.spring.framework.http.HttpSession;
import project.server.spring.framework.http.MediaType;
import project.server.spring.framework.http.QueryParams;
import project.server.spring.framework.http.RequestLine;
import project.server.spring.framework.servlet.HttpServletRequest;

public class AsyncHttpRequest implements HttpServletRequest {
	private final RequestLine requestLine;
	private final QueryParams queryParams;

	private final HttpHeaders httpHeaders;
	private String body;

	@Override
	public String getRequestURI() {
		return requestLine.getPath();
	}

	@Override
	public HttpMethod getHttpMethod() {
		return requestLine.getHttpMethod();
	}

	@Override
	public QueryParams getQueryParams() {
		return requestLine.getQueryParms();
	}

	@Override
	public String getHeader(String name) {
		return httpHeaders.get(name);
	}

	@Override
	public HttpHeaders getHeaders() {
		return httpHeaders;
	}

	@Override
	public char[] getBody() {
		return new char[0];
	}

	@Override
	public HttpSession getSession() {
		return null;
	}

	@Override
	public HttpSession createSession() {
		return null;
	}

	public AsyncHttpRequest(RequestLine requestLine, QueryParams queryParams, HttpHeaders httpHeaders) {
		this.requestLine = requestLine;
		this.queryParams = queryParams;
		this.httpHeaders = httpHeaders;
	}

	public AsyncHttpRequest(HttpHeaders httpHeaders, RequestLine requestLine, String body) {
		this.httpHeaders = httpHeaders;
		this.queryParams = requestLine.getQueryParms();
		this.requestLine = requestLine;
	}

	@Override
	public int getContentLength() {
		return 0;
	}

	@Override
	public MediaType getContentType() {
		return null;
	}
}
