package project.server.spring.app.core.controller.user;

import project.server.spring.app.core.service.user.UserService;
import project.server.spring.framework.annotation.Controller;
import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.http.HttpBody;
import project.server.spring.framework.http.HttpMethod;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.HttpServletResponse;
import project.server.spring.framework.utils.ObjectMapper;

@Controller
public class LoginController {
	private static final String EMAIL_KEY = "email";
	private static final String PASSWORD_KEY = "password";
	private final UserService userService;

	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/sign-in", method = HttpMethod.POST)
	public String sginIn(HttpServletRequest request, HttpServletResponse response) {
		HttpBody httpBody = ObjectMapper.readValue(request.getBody(), request.getContentType());
		assert httpBody != null;
		String email = httpBody.get(EMAIL_KEY);
		String password = httpBody.get(PASSWORD_KEY);
		userService.login(email, password);
		return "redirect:/";
	}
}
