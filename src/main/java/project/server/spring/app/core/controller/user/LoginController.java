package project.server.spring.app.core.controller.user;

import project.server.spring.app.core.repository.user.UserRepository;
import project.server.spring.framework.annotation.Controller;
import project.server.spring.framework.annotation.RequestMapping;

@Controller
public class LoginController {
	private final UserRepository userRepository;

	public LoginController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@RequestMapping
	public void login() {
	}
}
