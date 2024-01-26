package project.server.spring.app.core.controller.user;

import project.server.spring.app.core.repository.user.UserRepository;
import project.server.spring.framework.annotation.Controller;
import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.HttpServletResponse;
import project.server.spring.server.http.HttpMethod;
import project.server.spring.server.http.HttpResponse;

@Controller
public class UserController {
	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@RequestMapping(value = "/users", method = HttpMethod.POST)
	public void signIn(HttpServletRequest request, HttpServletResponse response) {
	}


}
