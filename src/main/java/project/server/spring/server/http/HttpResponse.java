package project.server.spring.server.http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
	private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
	private static final String STATIC_RESOURCE_PATH = "./src/main/resources";
	private HttpStatus status;
	private DataOutputStream dos; //TODO: OutputStream 으로 바꾸기
	private HttpHeaders headers = new HttpHeaders();
	private String path;
	private String protocol; //TODO: 필요하면 Protocol Enum으로 바꾸기

	private String body;

	//TODO: 생성자 받기

	public HttpResponse(OutputStream out) {
		dos = new DataOutputStream(out);
	}

	public HttpResponse(HttpStatus status, HttpHeaders headers, String path, String protocol, String body) {
		this.status = status;
		this.headers = headers;
		this.path = path;
		this.protocol = protocol;
		this.body = body;
	}

	public void addHeader(String key, String value) {
		headers.add(key, value);
	}

	public void forward(String url) {
		try {
			byte[] body = Files.readAllBytes(new File(STATIC_RESOURCE_PATH + url).toPath());
			if (url.endsWith(".css")) {
				headers.add("Content-Type", "text/css");
			} else if (url.endsWith(".js")) {
				headers.add("Content-Type", "application/javascript");
			} else {
				headers.add("Content-Type", "text/html;charset=utf-8");
			}
			headers.add("Content-Length", body.length + "");
			response200Header(body.length, headers.get("Content-Type"));
			responseBody(body);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void forwardBody(String body) throws IOException {
		byte[] contents = body.getBytes();
		headers.add("Content-Type", "text/html;charset=utf-8");
		headers.add("Content-Length", contents.length + "");
		response200Header(contents.length, "text/html;charset=utf-8");
		responseBody(contents);
	}

	public void responseHeader(int lengthOfBodyContent) throws IOException {
		writeResponseLine();
		processHeaders();
	}

	private void writeResponseLine() throws IOException {
		String responseLine = "HTTP/1.1 " + String.valueOf(status.getCode()) + " " + status.getReasonPhrase() + " \r\n";
		dos.writeBytes(responseLine);
	}

	public void response200Header(int lengthOfBodyContent, String contentType) throws IOException {
		String contentTypeLine = "Content-Type: " + contentType + "\r\n";
		dos.writeBytes("HTTP/1.1 200 OK \r\n");
		dos.writeBytes(contentTypeLine);
		dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
		dos.writeBytes("\r\n");
	}

	public void response400Header(int lengthOfBodyContent, String contentType) {
		String contentTypeLine = "Content-Type: " + contentType + "\r\n";
		try {
			dos.writeBytes("HTTP/1.1 400 Bad Request \r\n");
			dos.writeBytes(contentTypeLine);
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void responseBody(byte[] body) throws IOException {
		dos.write(body);
		dos.writeBytes("\r\n");
		dos.flush();
	}

	public void sendRedirect(String redirectUrl) {
		try {
			dos.writeBytes("HTTP/1.1 302 Found \r\n");
			dos.writeBytes("Location: " + redirectUrl + " \r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void processHeaders() {
		try {
			Set<String> keys = headers.getAllFields();
			for (String key : keys) {
				dos.writeBytes(key + ": " + headers.get(key) + " \r\n");
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void setContentType(MediaType mediaType) {
		headers.setContentType(mediaType);
	}

	public void setContentLength(long contentLength) {
		headers.setContentLength(contentLength);
	}

	public void setHttpStatus(HttpStatus status) {
		this.status = status;
	}

	public OutputStream getOutputStream() {
		return dos;
	}
}
