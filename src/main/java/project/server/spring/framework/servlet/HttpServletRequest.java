package project.server.spring.framework.servlet;

import project.server.spring.framework.http.HttpHeaders;
import project.server.spring.framework.http.HttpMethod;
import project.server.spring.framework.http.HttpSession;
import project.server.spring.framework.http.QueryParams;

public interface HttpServletRequest extends ServletRequest {
	String getRequestURI();

	HttpMethod getHttpMethod();

	QueryParams getQueryParams();

	String getHeader(String name);

	public HttpHeaders getHeaders();

	public char[] getBody();

	HttpSession getSession();

	HttpSession createSession();
}
