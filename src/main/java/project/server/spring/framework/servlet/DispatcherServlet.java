package project.server.spring.framework.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.context.ApplicationContext;
import project.server.spring.framework.util.FileFormat;
import project.server.spring.framework.util.FileProcessor;
import project.server.spring.server.RequestHandler;
import project.server.spring.server.http.MediaType;

public class DispatcherServlet extends FrameworkServlet {
	private static final DispatcherServlet SINGLE_INSTACE = new DispatcherServlet();

	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	private static final String DELIMETER = "/";
	private static final String END_CHARCTER = "\\.";

	private DispatcherServlet() {
	}

	@Override
	protected void doService(
		HttpServletRequest request,
		HttpServletResponse response
	) throws Exception {
		doDispatch(request, response);
	}

	private void doDispatch(HttpServletRequest request, HttpServletResponse response) {
		try {
			for (Object handler : ApplicationContext.getAllBeans()) {
				String viewName = processHandler(request, response, handler.getClass(), handler);
				if (viewName != null) {
					FileProcessor fileProcessor = new FileProcessor();
					byte[] buffer = fileProcessor.read(viewName + ".html");
					response.render200(buffer, buffer.length);
					return;
				}
			}
			throw new IllegalArgumentException("handler does not exist");
		} catch (Exception e) {
			log.info("Exception occurs {}", e.getMessage());

		}
	}

	public static DispatcherServlet getInstance() {
		return SINGLE_INSTACE;
	}

	//TODO: model , view, view resolver 나누기
	public void serviceTemp(HttpServletRequest request, HttpServletResponseImpl response) {
		log.info("request : {}", request.getRequestURI());
		if (isStaticResource(request.getRequestURI())) {
			handleStaticResource(request.getRequestURI(), response);
			return;
		}
		try {
			for (Object handler : ApplicationContext.getAllBeans()) {
				String viewName = processHandler(request, response, handler.getClass(), handler);
				if (viewName != null) {
					FileProcessor fileProcessor = new FileProcessor();
					byte[] buffer = fileProcessor.read(viewName + ".html");
					response.render200(buffer, buffer.length);
					return;
				}
			}
			throw new IllegalArgumentException("handler does not exist");
		} catch (InvocationTargetException | IllegalAccessException e) {
			log.info("Exception occurs {}", e.getMessage());
		} catch (IOException e) {
			log.info("Exception occurs {}", e.getMessage());
			throw new IllegalArgumentException(e);
		}
	}

	private static String processHandler(HttpServletRequest request, HttpServletResponse response,
		Class<?> clazz,
		Object handler) throws IllegalAccessException, InvocationTargetException {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(RequestMapping.class)) {
				RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
				if (requestMapping.value().length == 0 || requestMapping.method().length == 0) {
					continue;
				}
				if (request.getRequestURI().equals(requestMapping.value()[0]) && request.getHttpMethod()
					.equals(requestMapping.method()[0])) {
					return (String)method.invoke(handler, request, response); // 매개변수가 있는 경우는 추가로 전달
				}
			}
		}
		return null;
	}

	private boolean isStaticResource(String path) {
		String[] pathElements = parsePath(path);
		if (pathElements.length < 1) {
			return false;
		}
		String[] fileElements = pathElements[pathElements.length - 1].split(END_CHARCTER);
		if (pathElements.length == 1) {
			return false;
		}
		return true;
	}

	private static String[] parsePath(String path) {
		return path.split(DELIMETER);
	}

	private static String[] splitPath(String path) {
		String[] paths = path.split(DELIMETER);
		return paths[paths.length - 1].split(END_CHARCTER);
	}

	private void handleStaticResource(String path, HttpServletResponseImpl response) {
		try {
			String[] pathElements = parsePath(path);
			String[] fileElements = pathElements[pathElements.length - 1].split(END_CHARCTER);
			String extension = fileElements[1];
			FileFormat fileFormat = FileFormat.ofExtension(extension);
			response.setContentType(MediaType.ofSubType(fileFormat.getExtension()));
			response.dispatch(path);
		} catch (RuntimeException e) {
			log.info("{}", e.getMessage());
		}
	}
}
