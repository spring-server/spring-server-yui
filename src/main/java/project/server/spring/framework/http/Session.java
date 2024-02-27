package project.server.spring.framework.http;

import java.util.HashMap;
import java.util.Map;

public class Session {
	private String id;
	private long creationTime;
	private long lastAccessedTime;
	private final Map<String, Object> attributes = new HashMap<>();
	private int maxInactiveInterval = 600;

	public Session(String id) {
		this.id = id;
		this.creationTime = System.currentTimeMillis();
		this.lastAccessedTime = creationTime;
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
}
