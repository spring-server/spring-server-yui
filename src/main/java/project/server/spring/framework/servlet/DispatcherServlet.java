package project.server.spring.framework.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.context.ApplicationContext;
import project.server.spring.framework.servlet.handler.HandlerMethod;
import project.server.spring.framework.util.FileProcessor;
import project.server.spring.server.RequestHandler;

public class DispatcherServlet extends FrameworkServlet {
	private static final DispatcherServlet SINGLE_INSTACE = new DispatcherServlet();

	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	private static final String DELIMETER = "/";
	private static final String END_CHARCTER = "\\.";

	private static final String REDIRECT_INDEX = "redirect:";

	private DispatcherServlet() {
	}

	@Override
	protected void doService(
		HttpServletRequest request,
		HttpServletResponse response
	) throws Exception {
		doDispatch(request, response);
	}

	private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws
		InvocationTargetException,
		IllegalAccessException, IOException {
		HandlerMethod handlerMethod = getHandlerMethod(request);
		if (handlerMethod == null) {
			//TODO: response 상태코드 설정 어디서 할지
			throw new IllegalStateException("handler method does not exit");
		}
		String viewName = (String)handlerMethod.getMethod().invoke(handlerMethod.getHandler(), request, response);
		if (viewName == null) {
			throw new IllegalStateException("view name is null");
		}
		resolveView(viewName, response);
	}

	private void resolveView(String viewName, HttpServletResponse response) throws IOException {
		if (viewName.startsWith(REDIRECT_INDEX)) {
			String redirectUrl = viewName.substring(REDIRECT_INDEX.length());
			response.render30x(redirectUrl);
			return;
		}
		FileProcessor fileProcessor = new FileProcessor();
		byte[] buffer = fileProcessor.read(getViewName(viewName));
		response.render200(buffer, buffer.length);
	}

	private String getViewName(String viewName) {
		if (viewName.startsWith(REDIRECT_INDEX)) {
			return viewName.substring(REDIRECT_INDEX.length()) + "";
		}
		return viewName + ".html";
	}

	private HandlerMethod getHandlerMethod(HttpServletRequest request) {
		Method targetMethod = null;
		Object tagetHandler = null;
		for (Object handler : ApplicationContext.getAllBeans()) {
			Method mappingMethod = getMappingMethod(handler.getClass(), request);
			if (targetMethod != null && mappingMethod != null) {
				throw new IllegalStateException("handler method duplicated");
			}
			if (mappingMethod != null) {
				targetMethod = mappingMethod;
				tagetHandler = handler;
			}
		}
		if (targetMethod == null) {
			return null;
		}
		return new HandlerMethod(tagetHandler, targetMethod);
	}

	private Method getMappingMethod(Class<?> clazz, HttpServletRequest request) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(RequestMapping.class)) {
				RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
				if (requestMapping.value().length == 0 || requestMapping.method().length == 0) {
					continue;
				}
				if (request.getRequestURI().equals(requestMapping.value()[0]) && request.getHttpMethod()
					.equals(requestMapping.method()[0])) {
					return method;
				}
			}
		}
		return null;
	}

	public static DispatcherServlet getInstance() {
		return SINGLE_INSTACE;
	}
}
