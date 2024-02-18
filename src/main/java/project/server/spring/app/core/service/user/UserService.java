package project.server.spring.app.core.service.user;

import project.server.spring.app.core.domain.user.User;
import project.server.spring.app.core.global.DuplicatedUserException;
import project.server.spring.app.core.global.UserNotFoundException;
import project.server.spring.app.core.repository.user.UserRepository;
import project.server.spring.framework.annotation.Service;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Long add(String name, String email, String password) {
		if (userRepository.existsByEmail(email)) {
			throw new DuplicatedUserException("email already registered");
		}
		User user = new User(name, password, email);
		userRepository.save(user);
		return user.getId();
	}

	public Long login(String email, String password) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("email does not exist"));
		return user.getId();
	}
}
