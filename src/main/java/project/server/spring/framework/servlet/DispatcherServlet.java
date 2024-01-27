package project.server.spring.framework.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.server.spring.framework.annotation.RequestMapping;
import project.server.spring.framework.context.BeanRegistry;
import project.server.spring.framework.util.FileFormat;
import project.server.spring.framework.util.FileProcessor;
import project.server.spring.server.RequestHandler;
import project.server.spring.server.http.MediaType;

public class DispatcherServlet {
	private BeanRegistry container;

	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	private static final String DELIMETER = "/";
	private static final String END_CHARCTER = "\\.";

	public DispatcherServlet(BeanRegistry container) {
		this.container = container;
	}

	//TODO: model , view, view resolver 나누기
	public void service(HttpServletRequest request, HttpServletResponse response) {
		if (isStaticResource(request.getPath())) {
			handleStaticResource(request.getPath(), response);
			return ;
		}
		try {
			for (Class<?> clazz : container.getClasses()) {
				Object handler = container.getBean(clazz);
				String viewName = processHandler(request, response, clazz, handler);
				if (viewName != null) {
					FileProcessor fileProcessor = new FileProcessor();
					byte[] buffer = fileProcessor.read(viewName + ".html");
					response.render200(buffer, buffer.length);
					return ;
				}
			}
			throw new IllegalArgumentException("handler does not exist");
		} catch (InvocationTargetException | IllegalAccessException e) {
			log.info("Exception occurs {}",e.getMessage());
		} catch (IOException e) {
			log.info("Exception occurs {}",e.getMessage());
			throw new IllegalArgumentException(e);
		}
	}

	private static String processHandler(HttpServletRequest request, HttpServletResponse response, Class<?> clazz,
		Object handler) throws IllegalAccessException, InvocationTargetException {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(RequestMapping.class)) {
				RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
				if (requestMapping.value().length == 0 || requestMapping.method().length == 0) {
					continue;
				}
				if (request.getPath().equals(requestMapping.value()[0]) && request.getHttpMethod().equals(requestMapping.method()[0])) {
					return (String) method.invoke(handler, request, response);// 매개변수가 있는 경우는 추가로 전달
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
		String[] pathArray = path.split(DELIMETER);
		return pathArray;
	}
	private static String[] splitPath(String path) {
		String[] paths = path.split(DELIMETER);
		System.out.println(path);
		System.out.println(paths.length);
		String[] fileElements = paths[paths.length - 1].split(END_CHARCTER);
		return fileElements;
	}

	private void handleStaticResource(String path, HttpServletResponse response) {
		try {
			String[] pathElements = parsePath(path);
			String[] fileElements = pathElements[pathElements.length - 1].split(END_CHARCTER);
			String extension = fileElements[1];
			FileFormat fileFormat = FileFormat.ofExtension(extension);
			response.setContentType(MediaType.ofSubType(fileFormat.getSubType()));
			response.dispatch(path);
		} catch (RuntimeException e) {
			log.info("{}", e.getMessage());
		}
	}

	private static FileFormat getFileFormat(String path) {
		String[] fileElements = splitPath(path);
		String extension = fileElements[1];
		for (FileFormat file : FileFormat.values()) {
			if (file.getSubType().equals(extension)) {
				return file;
			}
		}
		return null;
	}
}
