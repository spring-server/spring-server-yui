package project.server.spring.framework.http;

import java.util.HashMap;
import java.util.Map;

public class Session {
	private final String id;
	private final long creationTime;
	private long lastAccessedTime;
	private final Map<String, Object> attributes = new HashMap<>();
	private int maxInactiveInterval = 600;
	private static final long ONE_SECOND = 1000;

	public Session(String id) {
		this.id = id;
		this.creationTime = System.currentTimeMillis();
		this.lastAccessedTime = creationTime;
	}

	public Session(String id, long creationTime, long lastAccessedTime) {
		this.id = id;
		this.creationTime = creationTime;
		this.lastAccessedTime = lastAccessedTime;
	}

	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
		lastAccessedTime = System.currentTimeMillis();
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	public long getCreationTime() {
		return creationTime;
	}

	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

	public void setMaxInactiveInterval(int maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}

	public String getId() {
		return id;
	}

	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public boolean isExpired() {
		long currentTime = System.currentTimeMillis();
		long result = (currentTime - lastAccessedTime) / ONE_SECOND;
		return result > maxInactiveInterval;
	}

	public void renew() {
		this.lastAccessedTime = System.currentTimeMillis();
	}
}
