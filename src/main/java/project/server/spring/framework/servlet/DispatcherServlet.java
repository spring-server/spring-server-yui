package project.server.spring.framework.servlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.annotation.RequestMapping;

// import project.server.spring.framework.context.BeanContainer;
// import project.server.spring.framework.context.BeanContainer;
import project.server.spring.framework.context.BeanRegistry;
import project.server.spring.server.RequestHandler;
import project.server.spring.server.http.HttpMethod;
public class DispatcherServlet {
	private BeanRegistry container;
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

	public DispatcherServlet(BeanRegistry container) {
		this.container = container;
	}

	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			for (Class<?> clazz : container.getClasses()) {
				Object handler = container.getBean(clazz);
				if (isMatched(request, response, clazz, handler))
					return;
			}
		} catch (InvocationTargetException | IllegalAccessException e) {
			log.info("Exception occurs {}",e.getMessage());
		}
	}

	private static boolean isMatched(HttpServletRequest request, HttpServletResponse response, Class<?> clazz,
		Object handler) throws IllegalAccessException, InvocationTargetException {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(RequestMapping.class)) {
				RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
				if (requestMapping.value().length == 0 || requestMapping.method().length == 0) {
					continue;
				}
				if (request.getPath().equals(requestMapping.value()[0]) && request.getHttpMethod().equals(requestMapping.method()[0])) {
					method.invoke(handler, request, response); // 매개변수가 있는 경우는 추가로 전달
					return true;
				}
			}
		}
		return false;
	}
}
