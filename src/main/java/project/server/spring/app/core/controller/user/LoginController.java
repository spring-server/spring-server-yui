package project.server.spring.app.core.controller.user;

import project.server.spring.app.core.service.user.UserService;
import project.server.spring.framework.annotation.Controller;
import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.http.HttpMethod;

@Controller
public class LoginController {
	private final UserService userService;

	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/login", method = HttpMethod.POST)
	public String login() {
		return "redirect:/";
	}
}
