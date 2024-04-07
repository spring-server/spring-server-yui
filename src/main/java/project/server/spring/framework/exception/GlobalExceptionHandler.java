package project.server.spring.framework.exception;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.server.spring.framework.cache.DefaultPageMap;
import project.server.spring.framework.http.HttpStatus;
import project.server.spring.framework.http.error.ErrorResponse;
import project.server.spring.framework.servlet.HttpServletResponse;

public class GlobalExceptionHandler {
	private final DefaultPageMap defaultPageMap = new DefaultPageMap();
	private final ObjectMapper objectMapper = new ObjectMapper();

	public void resolveException(HttpServletResponse response, Exception exception) throws IOException {
		if (!(exception instanceof RuntimeException)) {
			response.setContentType("text/html; charset=UTF-8");
			String page = defaultPageMap.getPage(HttpStatus.INTERNAL_SERVER_ERROR.getCode());
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), page.getBytes());
			return;
		}
		ErrorResponse errorResponse = objectMapper.readValue(exception.toString(), ErrorResponse.class);
		String page = defaultPageMap.getPage(errorResponse.getCode());
		response.setContentType("text/html; charset=UTF-8");
		response.sendError(errorResponse.getCode(), page.getBytes());
	}
}
