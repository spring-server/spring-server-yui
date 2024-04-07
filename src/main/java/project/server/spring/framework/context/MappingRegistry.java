package project.server.spring.framework.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import project.server.spring.framework.annotation.Controller;
import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.servlet.handler.HandlerMethod;
import project.server.spring.framework.servlet.mvc.RequestMappingInfo;

public class MappingRegistry {
	private Map<RequestMappingInfo, HandlerMethod> handlerMethods = null;

	public MappingRegistry() {
	}

	private void findMappingHandlers() {
		handlerMethods = new HashMap<>();
		for (Object bean : ApplicationContext.getAllBeans()) {
			Annotation[] annotations = bean.getClass().getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().equals(Controller.class)) {
					findMappingMethods(bean);
				}
			}
		}
	}

	private void findMappingMethods(Object handler) {
		for (Method method : handler.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(RequestMapping.class)) {
				RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
				if (requestMapping.value().length == 0 || requestMapping.method().length == 0) {
					continue;
				}
				RequestMappingInfo requestMappingInfo = new RequestMappingInfo(requestMapping.method()[0],
					requestMapping.value()[0]);
				if (handlerMethods.get(requestMappingInfo) != null) {
					throw new IllegalStateException("handler method duplicated");
				}
				handlerMethods.put(requestMappingInfo, new HandlerMethod(handler, method));
			}
		}
	}

	public HandlerMethod getHandlerMethod(RequestMappingInfo info) {
		if (handlerMethods == null) {
			findMappingHandlers();
		}
		return handlerMethods.get(info);
	}
}
