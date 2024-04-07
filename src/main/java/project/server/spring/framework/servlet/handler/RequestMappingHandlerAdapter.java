package project.server.spring.framework.servlet.handler;

import lombok.extern.slf4j.Slf4j;
import project.server.spring.framework.annotation.Component;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.HttpServletResponse;
import project.server.spring.framework.servlet.ModelAndView;

@Slf4j
@Component
public class RequestMappingHandlerAdapter implements HandlerAdapter {
	@Override
	public boolean supports(Object handler) {
		return handler instanceof HandlerMethod;
	}

	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		return (ModelAndView)handlerMethod.getMethod()
			.invoke(handlerMethod.getHandler(), request, response);
	}
}
