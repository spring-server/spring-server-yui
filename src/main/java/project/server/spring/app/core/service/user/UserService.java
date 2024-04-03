package project.server.spring.app.core.service.user;

import static project.server.spring.app.core.global.error.ErrorMessage.*;

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
			throw new DuplicatedUserException(EMAIL_DUPLICATED.getMessage());
		}
		User user = new User(name, password, email);
		userRepository.save(user);
		return user.getId();
	}

	public Long login(String email, String password) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException(String.format(NOT_FOUND.getMessage(), "email")));
		user.validatePassword(password);
		return user.getId();
	}

	public UserInfoDto getUserInfo(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(String.format(NOT_FOUND.getMessage(), "userId")));
		return new UserInfoDto(user);
	}

	public void update(UserInfoDto userInfoDto, Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(String.format(NOT_FOUND.getMessage(), "userId")));
		user.update(userInfoDto.getName(), userInfoDto.getPassword(), userInfoDto.getEmail(),
			userInfoDto.getPhoneNumber());
		userRepository.save(user);
	}
}
