package project.server.spring.app.core.repository.user;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import project.server.spring.app.core.domain.user.User;
import project.server.spring.framework.annotation.Component;

@Component
public class UserRepository {
	private final Map<Long, User> users;

	public UserRepository() {
		this.users = new ConcurrentHashMap<>();
	}

	public void create(User user) {
		if (users.get(user.getId()) != null) {
			throw new IllegalStateException("user id already exists");
		}
		users.put(user.getId(), user);
	}

	public void update(Long id, User user) {
		if (users.get(id) == null) {
			throw new IllegalStateException("user id does not exist");
		}
		users.put(id, user);
	}

	public User findById(Long id) {
		return users.get(id);
	}

	void delete(Long id, User user) {
		users.remove(id);
	}
}
