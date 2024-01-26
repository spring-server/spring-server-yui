package project.server.spring.app.core.controller.base;

import project.server.spring.framework.annotation.Controller;
import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.HttpServletResponse;
import project.server.spring.server.http.HttpMethod;

@Controller
public class BaseController {
	@RequestMapping(value = "/", method = HttpMethod.GET)
	public String welcome() {
		return "index";
	}
}
