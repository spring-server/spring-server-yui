package project.server.spring.framework.servlet;

import java.io.IOException;

@FunctionalInterface
public interface HttpRequestHandler {
	void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
