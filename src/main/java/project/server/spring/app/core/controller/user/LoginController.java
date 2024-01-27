package project.server.spring.app.core.controller.user;

import project.server.spring.app.core.repository.user.UserRepository;
import project.server.spring.framework.annotation.Controller;
import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.server.http.HttpMethod;

@Controller
public class LoginController {
	private final UserRepository userRepository;

	public LoginController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@RequestMapping(value = "/login", method = HttpMethod.POST)
	public void login() {
	}
}
