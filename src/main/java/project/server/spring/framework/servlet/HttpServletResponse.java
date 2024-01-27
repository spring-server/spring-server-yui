package project.server.spring.framework.servlet;

import project.server.spring.server.http.HttpHeaders;
import project.server.spring.server.http.HttpResponse;
import project.server.spring.server.http.HttpStatus;
import project.server.spring.server.http.MediaType;

public class HttpServletResponse {
	private final HttpResponse response;
	private final HttpHeaders headers;

	public HttpServletResponse(HttpResponse response) {
		this.response = response;
		this.headers = new HttpHeaders();
	}

	public void render200(byte[] body, int lengthOfBodyContent) {
		response.response200Header(lengthOfBodyContent);
		response.responseBody(body);
	}

	public void render(byte[] body, int lengthOfBodyContent) {
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
}
