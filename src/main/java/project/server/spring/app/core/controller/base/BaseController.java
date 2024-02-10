package project.server.spring.app.core.controller.base;

import project.server.spring.framework.annotation.Controller;
import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.servlet.HttpServletRequestImpl;
import project.server.spring.framework.servlet.HttpServletResponseImpl;
import project.server.spring.server.http.HttpMethod;

@Controller
public class BaseController {
	@RequestMapping(value = "/", method = HttpMethod.GET)
	public String welcome(HttpServletRequestImpl request, HttpServletResponseImpl response) {
		return "index";
	}

	@RequestMapping(value = "/sign-in", method = HttpMethod.GET)
	public String signIn(HttpServletRequestImpl request, HttpServletResponseImpl response) {
		return "sign-in";
	}
}
