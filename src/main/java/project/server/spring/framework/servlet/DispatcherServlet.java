package project.server.spring.framework.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.server.spring.framework.RequestHandler;
import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.context.ApplicationContext;
import project.server.spring.framework.http.Cookie;
import project.server.spring.framework.http.HttpSession;
import project.server.spring.framework.http.HttpStatus;
import project.server.spring.framework.http.error.ErrorResponse;
import project.server.spring.framework.servlet.handler.HandlerMethod;
import project.server.spring.framework.util.PageProcessor;

public class DispatcherServlet extends FrameworkServlet {
	private static final DispatcherServlet SINGLE_INSTACE = new DispatcherServlet();

	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	private static final String DELIMETER = "/";
	private static final String END_CHARCTER = "\\.";

	private static final String REDIRECT_INDEX = "redirect:";

	private final ObjectMapper objectMapper = new ObjectMapper();

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
		try {
			HandlerMethod handlerMethod = getHandlerMethod(request);
			if (handlerMethod == null) {
				throw new IllegalStateException("handler method does not exit");
			}
			log.info("info : {} , {}", handlerMethod.getHandler(), handlerMethod.getMethod());
			ModelAndView modelAndView = (ModelAndView)handlerMethod.getMethod()
				.invoke(handlerMethod.getHandler(), request, response);
			//TODO: 세션 처리해주는 로직 어떻게 분리할지
			handleSession(request, response);
			if (modelAndView.getView() == null) {
				resolveResponseBody(response);
				return;
			}
			resolveView(modelAndView, response);
		} catch (Exception exception) {
			resolveException(response, exception);
		}
	}

	void resolveException(HttpServletResponse response, Exception exception) throws IOException {
		Throwable cause = exception.getCause();
		if (cause == null) {
			response.setContentType("text/html; charset=UTF-8");
			PageProcessor pageProcessor = new PageProcessor();
			String page = pageProcessor.read("400.html");
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), page.getBytes());
			return;
		}
		ErrorResponse errorResponse = objectMapper.readValue(cause.toString(), ErrorResponse.class);
		PageProcessor pageProcessor = new PageProcessor();
		String page = pageProcessor.read("400.html");
		response.setContentType("text/html; charset=UTF-8");
		response.sendError(errorResponse.getCode(), page.getBytes());
	}

	private void resolveResponseBody(HttpServletResponse response) {
		response.setStatus(200);
		response.flush(null);
	}

	private void handleSession(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = null;
		try {
			session = request.getSession();
		} catch (Exception e) {
			Cookie sessionCookie = Cookie.createSessionCookie("");
			sessionCookie.setMaxAge(0);
			response.addCookie(sessionCookie);
		}
		if (session != null && session.getId() != null) {
			String sessionId = session.getId();
			Cookie cookie = Cookie.createSessionCookie(sessionId);
			cookie.setHttpOnly(true);
			cookie.setMaxAge(600);
			response.addCookie(cookie);
		}
	}

	private void resolveView(ModelAndView modelAndView, HttpServletResponse response) throws IOException {
		String viewName = modelAndView.getView();
		if (viewName.startsWith(REDIRECT_INDEX)) {
			String redirectUrl = viewName.substring(REDIRECT_INDEX.length());
			response.sendRedirect(redirectUrl);
			return;
		}
		PageProcessor pageProcessor = new PageProcessor();
		String page = pageProcessor.read(getViewName(viewName));
		if (modelAndView.hasModelMap()) {
			page = pageProcessor.changeAttributes(modelAndView, page);
		}
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(200);
		response.flush(page.getBytes(StandardCharsets.UTF_8));
	}

	private String getViewName(String viewName) {
		if (viewName.startsWith(REDIRECT_INDEX)) {
			return viewName.substring(REDIRECT_INDEX.length());
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
