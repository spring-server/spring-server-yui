package project.server.spring.framework.servlet;

import java.io.IOException;
import java.io.OutputStream;

import project.server.spring.framework.http.HttpHeaders;
import project.server.spring.framework.http.HttpResponse;
import project.server.spring.framework.http.HttpStatus;
import project.server.spring.framework.http.MediaType;

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

	@Override
	public void render200(byte[] body, int lengthOfBodyContent) throws IOException {
		response.response200Header(body.length, "text/html; charset=UTF-8");
		response.responseBody(body);
	}

	@Override
	public void render40x(byte[] body, int lengthOfBodyContent) throws IOException {
		response.response400Header(body.length, "text/html; charset=UTF-8");
		response.responseBody(body);
	}

	@Override
	public void render30x(String redirectUrl) throws IOException {
		response.sendRedirect(redirectUrl);
	}

	public void render(byte[] body, int lengthOfBodyContent) throws IOException {
		response.responseHeader(lengthOfBodyContent);
		response.responseBody(body);
	}

	@Override
	public void setContentType(MediaType mediaType) {
		response.setContentType(mediaType);
	}

	public void setContentLength(long length) {
		response.setContentLength(length);
	}

	@Override
	public void setStatus(HttpStatus status) {
		response.setHttpStatus(status);
	}

	@Override
	public void dispatch(String path) {
		response.forward(path);
	}

	@Override
	public void sendError(HttpStatus status) {
		response.setHttpStatus(status);
	}
}
