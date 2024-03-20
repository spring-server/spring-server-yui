package project.server.spring.app.core.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

import project.server.spring.app.core.dto.UserInfoDto;
import project.server.spring.app.core.service.user.UserService;
import project.server.spring.framework.annotation.Controller;
import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.http.HttpBody;
import project.server.spring.framework.http.HttpMethod;
import project.server.spring.framework.http.HttpSession;
import project.server.spring.framework.http.MediaType;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.HttpServletResponse;
import project.server.spring.framework.servlet.ModelAndView;
import project.server.spring.framework.utils.BodyParser;

@Controller
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private static final String EMAIL_KEY = "email";
	private static final String NAME_KEY = "name";
	private static final String PASSWORD_KEY = "password";
	private static final String USER_ID = "user_id";
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/sign-up", method = HttpMethod.POST)
	public ModelAndView signUp(HttpServletRequest request, HttpServletResponse response) {
		HttpBody httpBody = BodyParser.readForm(request.getBody(), MediaType.ofValue(request.getContentType()));
		assert httpBody != null;
		String name = httpBody.get(NAME_KEY);
		String email = httpBody.get(EMAIL_KEY);
		String password = httpBody.get(PASSWORD_KEY);
		userService.add(name, email, password);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/profile", method = HttpMethod.PUT)
	public ModelAndView updateUserInfo(HttpServletRequest request, HttpServletResponse response) throws
		JsonProcessingException {
		HttpSession session = request.getSession();
		UserInfoDto userInfoDto = BodyParser.readJson(request.getBody(), MediaType.ofValue(request.getContentType()),
			UserInfoDto.class);
		userService.update(userInfoDto, (Long)session.getAttribute(USER_ID));
		response.setContentType(MediaType.APPLICATION_JSON.getValue());
		return new ModelAndView();
	}
}
