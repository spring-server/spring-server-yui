package project.server.spring.app.core.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.app.core.service.user.UserService;
import project.server.spring.framework.annotation.Controller;
import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.http.HttpBody;
import project.server.spring.framework.http.HttpMethod;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.HttpServletResponse;
import project.server.spring.framework.utils.ObjectMapper;

@Controller
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private static final String EMAIL_KEY = "email";
	private static final String NAME_KEY = "name";
	private static final String PASSWORD_KEY = "password";
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/sign-up", method = HttpMethod.POST)
	public String signIn(HttpServletRequest request, HttpServletResponse response) {
		HttpBody httpBody = ObjectMapper.readValue(request.getBody(), request.getContentType());
		String name = httpBody.get(NAME_KEY);
		String email = httpBody.get(EMAIL_KEY);
		String password = httpBody.get(PASSWORD_KEY);
		userService.add(name, email, password);
		return "redirect:/";
	}

	@RequestMapping(value = "/sign-up", method = HttpMethod.GET)
	public String signUp(HttpServletRequest request, HttpServletResponse response) {
		return "sign-up";
	}

}
