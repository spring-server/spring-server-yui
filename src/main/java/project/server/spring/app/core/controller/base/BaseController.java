package project.server.spring.app.core.controller.base;

import java.util.HashMap;
import java.util.Map;

import project.server.spring.app.core.dto.UserInfoDto;
import project.server.spring.app.core.service.user.UserService;
import project.server.spring.framework.annotation.Controller;
import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.http.HttpMethod;
import project.server.spring.framework.http.HttpSession;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.HttpServletResponse;
import project.server.spring.framework.servlet.ModelAndView;

@Controller
public class BaseController {
	private static final String USER_ID = "user_id";
	private final UserService userService;

	public BaseController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/", method = HttpMethod.GET)
	public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/sign-in", method = HttpMethod.GET)
	public ModelAndView signIn(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("sign-in");
	}

	@RequestMapping(value = "/profile", method = HttpMethod.GET)
	public ModelAndView profile(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Long userId = (Long)session.getAttribute(USER_ID);
		ModelAndView modelAndView = new ModelAndView("my-info");
		UserInfoDto userInfo = userService.getUserInfo(userId);
		Map<String, Object> map = userInfo.toMap(new HashMap<>());
		modelAndView.setModelMap(map);
		return modelAndView;
	}
}
