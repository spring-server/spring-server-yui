package project.server.spring.framework.servlet;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.http.HttpHeaders;
import project.server.spring.framework.http.HttpMethod;
import project.server.spring.framework.http.HttpRequest;
import project.server.spring.framework.http.HttpSession;
import project.server.spring.framework.http.InMemoryServerSession;
import project.server.spring.framework.http.MediaType;
import project.server.spring.framework.http.QueryParams;
import project.server.spring.framework.http.Session;
import project.server.spring.framework.http.SessionStore;

public class HttpServletRequestImpl implements AsyncHttpRequest {
	private static final Logger log = LoggerFactory.getLogger(HttpServletRequestImpl.class);

	private HttpMethod httpMethod;
	private String path;
	private MediaType contentType;
	private char[] body;
	private HttpHeaders headers;
	private QueryParams queryParams;

	private HttpSession session = null;
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
		if (request.getCookies() != null && request.getCookies().getSessionId() != null) {
			this.session = new InMemoryServerSession(request.getCookies().getSessionId());
		}
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

	@Override
	public HttpSession getSession() {
		return session;
	}

	@Override
	public HttpSession createSession() {
		String sessionId = UUID.randomUUID().toString();
		session = new InMemoryServerSession(sessionId);
		SessionStore.save(new Session(sessionId));
		return session;
	}
}
