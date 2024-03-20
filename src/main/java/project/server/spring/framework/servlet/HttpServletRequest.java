package project.server.spring.framework.servlet;

import java.util.Collection;
import java.util.List;

import project.server.spring.framework.http.Cookie;
import project.server.spring.framework.http.HttpMethod;
import project.server.spring.framework.http.HttpSession;
import project.server.spring.framework.http.QueryParams;

public interface HttpServletRequest extends ServletRequest {
	String getRequestURI();

	HttpMethod getHttpMethod();

	QueryParams getQueryParams();

	HttpSession getSession();

	HttpSession createSession();

	List<Cookie> getCookies();

	String getHeader(String var1);

	Collection<String> getHeaderNames();

	int getIntHeader(String var1);

	char[] getBody();
}
