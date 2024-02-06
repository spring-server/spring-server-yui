package project.server.spring.framework.servlet;

import java.io.IOException;

import project.server.spring.server.http.HttpStatus;
import project.server.spring.server.http.MediaType;

public interface HttpServletResponse extends ServletResponse {

	void sendError(HttpStatus status);

	// void setHeader(String name, String value);

	//TODO: 나중에 지우기
	public void dispatch(String path);

	public void render200(byte[] body, int lengthOfBodyContent) throws IOException;

	public void setContentType(MediaType mediaType);
}
