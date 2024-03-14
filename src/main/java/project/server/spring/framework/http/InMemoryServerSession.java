package project.server.spring.framework.http;

public class InMemoryServerSession implements HttpSession {
	//TODO: 세션 스토어 고민해보기
	private Session session;

	public InMemoryServerSession(String sessionId) {
		if (sessionId == null) {
			throw new IllegalArgumentException("session id is null");
		}
		if (SessionStore.get(sessionId) == null) {
			throw new IllegalArgumentException("invalid session id");
		}
		this.session = SessionStore.get(sessionId);
	}

	@Override
	public void setAttribute(String name, Object value) {
		session.setAttribute(name, value);
		SessionStore.save(session);
	}

	@Override
	public Object getAttribute(String name) {

		return session != null ? session.getAttribute(name) : null;
	}

	@Override
	public long getCreationTime() {
		return session != null ? session.getCreationTime() : 0;
	}

	@Override
	public long getLastAccessedTime() {
		return session != null ? session.getLastAccessedTime() : 0;
	}

	@Override
	public void setMaxInactiveInterval(int seconds) {
		session.setMaxInactiveInterval(seconds);
	}

	@Override
	public void invalidate() {
		SessionStore.delete(session.getId());
	}

	@Override
	public String getId() {
		if (session == null) {
			throw new IllegalArgumentException("session is null");
		}
		return session.getId();
	}

	@Override
	public int getMaxInactiveInterval() {
		return session.getMaxInactiveInterval();
	}

	@Override
	public boolean isNew() {
		return SessionStore.get(session.getId()) == null;
	}
}
