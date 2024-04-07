package project.server.spring.framework.servlet.handler;

import project.server.spring.framework.annotation.Component;
import project.server.spring.framework.context.ApplicationContext;
import project.server.spring.framework.context.MappingRegistry;
import project.server.spring.framework.mvc.InterceptorRegistration;
import project.server.spring.framework.mvc.InterceptorRegistry;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.mvc.RequestMappingInfo;

@Component
public class RequestMappingHandlerMapping implements HandlerMapping {
	private final InterceptorRegistry interceptorRegistry;
	private final MappingRegistry mappingRegistry;

	public RequestMappingHandlerMapping(MappingRegistry mappingRegistry) {
		this.interceptorRegistry = ApplicationContext.getBean(InterceptorRegistry.class);
		this.mappingRegistry = mappingRegistry;
	}

	@Override
	public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
		HandlerMethod handlerMethod = getHandlerInternal(request);
		return getHandlerExecutionChain(handlerMethod, request);
	}

	private HandlerExecutionChain getHandlerExecutionChain(
		Object handler,
		HttpServletRequest request
	) {
		HandlerExecutionChain chain = new HandlerExecutionChain(handler);
		for (InterceptorRegistration interceptorRegistration : interceptorRegistry.getInterceptors()) {
			if (interceptorRegistration.matches(request)) {
				chain.addInterceptor(interceptorRegistration.getInterceptor());
			}
		}
		return chain;
	}

	private HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception {
		RequestMappingInfo requestMappingInfo = new RequestMappingInfo(request.getHttpMethod(),
			request.getRequestURI());
		return mappingRegistry.getHandlerMethod(requestMappingInfo);
	}
}
