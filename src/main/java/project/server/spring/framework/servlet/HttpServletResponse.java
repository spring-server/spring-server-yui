package project.server.spring.framework.servlet;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Collection;

import project.server.spring.framework.http.Cookie;

public interface HttpServletResponse extends ServletResponse {
	void addCookie(Cookie var1);

	boolean containsHeader(String var1);

	String encodeURL(String var1);

	String encodeRedirectURL(String var1);

	void sendError(int var1, byte[] var2) throws IOException;

	void sendError(int var1) throws IOException;

	void sendRedirect(String location) throws IOException;

	void setDateHeader(String var1, ZonedDateTime time);

	void addDateHeader(String var1, ZonedDateTime time);

	void setHeader(String var1, String var2);

	void addHeader(String var1, String var2);

	void setIntHeader(String var1, int var2);

	void addIntHeader(String var1, int var2);

	void setStatus(int var1);

	int getStatus();

	String getHeader(String var1);

	Collection<String> getHeaderNames();

	public void flush(byte[] body);

	public void dispatch(String url);
}
