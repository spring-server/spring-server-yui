package project.server.spring.framework.servlet;

import java.io.IOException;
import java.io.OutputStream;

import project.server.spring.server.http.HttpHeaders;
import project.server.spring.server.http.HttpResponse;
import project.server.spring.server.http.HttpStatus;
import project.server.spring.server.http.MediaType;

public class HttpServletResponseImpl implements HttpServletResponse {
	private final HttpResponse response;
	private final HttpHeaders headers;

	public HttpServletResponseImpl(HttpResponse response) {
		this.response = response;
		this.headers = new HttpHeaders();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return response.getOutputStream();
	}

	public void render200(byte[] body, int lengthOfBodyContent) throws IOException {
		response.response200Header(body.length, "text/html; charset=UTF-8");
		response.responseBody(body);
	}

	public void render(byte[] body, int lengthOfBodyContent) throws IOException {
		response.responseHeader(lengthOfBodyContent);
		response.responseBody(body);
	}

	public void setContentType(MediaType mediaType) {
		response.setContentType(mediaType);
	}

	public void setContentLength(long length) {
		response.setContentLength(length);
	}

	public void setHttpStatus(HttpStatus status) {
		response.setHttpStatus(status);
	}

	public void dispatch(String path) {
		response.forward(path);
	}

	@Override
	public void sendError(HttpStatus status) {
		response.setHttpStatus(status);

	}
}
