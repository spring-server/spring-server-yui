package project.server.spring.app.core.repository.user;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import project.server.spring.app.core.domain.user.User;
import project.server.spring.framework.annotation.Repository;

@Repository
public class InMemoryUserRepository implements UserRepository {
	private static final Map<Long, User> users = new ConcurrentHashMap<>();
	private static final AtomicLong idGenerator = new AtomicLong(0L);

	public InMemoryUserRepository() {
	}

	@Override
	public User save(User user) {
		if (isNew(user)) {
			user.initializeId(idGenerator.incrementAndGet());
		}
		users.put(user.getId(), user);
		return user;
	}

	@Override
	public void delete(User user) {
		if (isNew(user)) {
			return;
		}
		users.remove(user.getId());
	}

	@Override
	public Optional<User> findById(Long id) {
		return Optional.ofNullable(users.get(id));
	}

	@Override
	public boolean existsById(Long id) {
		return findById(id).isPresent();
	}

	@Override
	public boolean existsByEmail(String email) {
		return findByEmail(email).isPresent();
	}

	@Override
	public Optional<User> findByEmail(String email) {
		for (User user : users.values()) {
			if (user.hasEmail(email)) {
				return Optional.of(user);
			}
		}
		return Optional.empty();
	}

	private boolean isNew(User user) {
		return user.isNew();
	}
}
