package project.server.spring.app.core.repository.user;

import java.util.Optional;

import project.server.spring.app.core.domain.user.User;

public interface UserRepository {
	User save(User user);

	void delete(User user);

	Optional<User> findById(Long id);

	Optional<User> findByEmail(String email);

	boolean existsById(Long id);

	boolean existsByEmail(String email);
}
