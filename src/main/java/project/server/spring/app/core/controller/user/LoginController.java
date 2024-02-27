package project.server.spring.app.core.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.app.core.service.user.UserService;
import project.server.spring.framework.annotation.Controller;
import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.http.HttpBody;
import project.server.spring.framework.http.HttpMethod;
import project.server.spring.framework.http.HttpSession;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.HttpServletResponse;
import project.server.spring.framework.utils.ObjectMapper;

@Controller
public class LoginController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	private static final String EMAIL_KEY = "email";
	private static final String PASSWORD_KEY = "password";
	private static final String USER_ID = "user_id";
	private final UserService userService;

	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/sign-in", method = HttpMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		HttpBody httpBody = ObjectMapper.readValue(request.getBody(), request.getContentType());
		HttpSession session = request.createSession();
		String email = httpBody.get(EMAIL_KEY);
		String password = httpBody.get(PASSWORD_KEY);
		Long userId = userService.login(email, password);
		session.setAttribute(USER_ID, userId);
		return "redirect:/";
	}
}
