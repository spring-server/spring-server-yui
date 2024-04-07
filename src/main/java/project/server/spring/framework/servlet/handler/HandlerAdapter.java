package project.server.spring.framework.servlet.handler;

import project.server.spring.framework.annotation.Component;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.HttpServletResponse;
import project.server.spring.framework.servlet.ModelAndView;

@Component
public interface HandlerAdapter {
	boolean supports(Object handler);

	ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
