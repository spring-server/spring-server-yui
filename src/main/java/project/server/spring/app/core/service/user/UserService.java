package project.server.spring.app.core.service.user;

import project.server.spring.app.core.domain.user.User;
import project.server.spring.app.core.dto.UserInfoDto;
import project.server.spring.app.core.global.exception.DuplicatedUserException;
import project.server.spring.app.core.global.exception.UserNotFoundException;
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
		user.validatePassword(password);
		return user.getId();
	}

	public UserInfoDto getUserInfo(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("userId does not exist"));
		return new UserInfoDto(user);
	}

	public void update(UserInfoDto userInfoDto, Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("userId does not exist"));
		user.update(userInfoDto.getName(), userInfoDto.getPassword(), userInfoDto.getEmail(),
			userInfoDto.getPhoneNumber());
	}
}
