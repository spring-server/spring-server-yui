package project.server.spring.framework.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStore {
	private static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

	public static Session save(Session session) {
		sessionMap.put(session.getId(), session);
		return session;
	}

	public static Session get(String sessionId) {
		Session session = sessionMap.get(sessionId);
		if (session.isExpired()) {
			return null;
		}
		session.renew();
		return session;
	}

	public static void delete(String sessionId) {
		sessionMap.remove(sessionId);
	}
}
