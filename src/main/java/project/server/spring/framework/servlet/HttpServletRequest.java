package project.server.spring.framework.servlet;

import project.server.spring.server.http.HttpHeaders;
import project.server.spring.server.http.HttpMethod;
import project.server.spring.server.http.MediaType;
import project.server.spring.server.http.QueryParams;

public interface HttpServletRequest extends ServletRequest {
	String getRequestURI();

	HttpMethod getHttpMethod();

	QueryParams getQueryParams();

	String getHeader(String name);

	MediaType getContentType();

	public HttpHeaders getHeaders();

	public String getBody();
}
