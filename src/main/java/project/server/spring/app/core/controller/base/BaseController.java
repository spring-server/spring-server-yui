package project.server.spring.app.core.controller.base;

import project.server.spring.framework.annotation.Controller;
import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.http.HttpMethod;
import project.server.spring.framework.servlet.HttpServletResponseImpl;
import project.server.spring.framework.servlet.HttpServletServletRequestImpl;

@Controller
public class BaseController {
	@RequestMapping(value = "/", method = HttpMethod.GET)
	public String welcome(HttpServletServletRequestImpl request, HttpServletResponseImpl response) {
		return "index";
	}

	@RequestMapping(value = "/sign-in", method = HttpMethod.GET)
	public String signIn(HttpServletServletRequestImpl request, HttpServletResponseImpl response) {
		return "sign-in";
	}

	@RequestMapping(value = "/profile", method = HttpMethod.GET)
	public String profile(HttpServletServletRequestImpl request, HttpServletResponseImpl response) {
		return "my-info";
	}
}
