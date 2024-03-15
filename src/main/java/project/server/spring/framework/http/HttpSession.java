package project.server.spring.framework.http;

public interface HttpSession {
	void setAttribute(String name, Object value);

	Object getAttribute(String name);

	boolean isExpired();

	long getCreationTime();

	long getLastAccessedTime();

	void setMaxInactiveInterval(int second);

	int getMaxInactiveInterval();

	void invalidate();

	String getId();

	boolean isNew();
}
