package project.server.spring.framework.servlet;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.http.MediaType;
import project.server.spring.framework.util.FileFormat;

public abstract class FrameworkServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(FrameworkServlet.class);
	private static final String DELIMETER = "/";
	private static final String END_CHARCTER = "\\.";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		processRequest(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//TODO: 리팩토링 필요
		if (isStaticResource(request.getRequestURI())) {
			processStaticRequest(request, response);
			return;
		}
		processRequest(request, response);
	}

	protected abstract void doService(HttpServletRequest request, HttpServletResponse response) throws Exception;

	//TODO: 검토 필요
	private boolean isStaticResource(String path) {
		String[] pathElements = parsePath(path);
		if (pathElements.length < 1) {
			return false;
		}
		String[] fileElements = pathElements[pathElements.length - 1].split(END_CHARCTER);
		if (fileElements.length == 1) {
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

	private void processStaticRequest(
		HttpServletRequest request,
		HttpServletResponse response
	) throws IOException {
		handleStaticResource(request.getRequestURI(), response);
		// ResourceHttpRequestHandler handler = ResourceHttpRequestHandler.getInstance();
		// handler.handleRequest(request, response);
	}

	protected final void processRequest(
		HttpServletRequest request,
		HttpServletResponse response
	) throws Exception {
		doService(request, response);
	}

	private void handleStaticResource(String path, HttpServletResponse response) {
		try {
			String[] pathElements = parsePath(path);
			String[] fileElements = pathElements[pathElements.length - 1].split(END_CHARCTER);
			String extension = fileElements[1];
			FileFormat fileFormat = FileFormat.ofExtension(extension);
			response.setContentType(MediaType.ofSubType(fileFormat.getExtension()));
			response.dispatch(path);
		} catch (RuntimeException e) {
			log.info("exception message : {}", e.getMessage());
		}
	}

}
