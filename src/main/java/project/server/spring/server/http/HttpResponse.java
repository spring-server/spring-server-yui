package project.server.spring.server.http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
	private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
	private HttpStatus status;
	private DataOutputStream dos;
	private HttpHeaders headers = new HttpHeaders();
	private String path;
	private String protocol;//TODO: 필요하면 Protocol Enum으로 바꾸기

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
			File directory = new File("./src/main/resources");

			// 디렉터리 내의 모든 파일과 디렉터리 나열
			String[] files = directory.list();
			if (files != null) {
				for (String file : files) {
					System.out.println("file:" + file);
				}
			}
			byte[] body = Files.readAllBytes(new File("./src/main/resources" + url).toPath());
			if (url.endsWith(".css")) {
				headers.add("Content-Type", "text/css");
			} else if (url.endsWith(".js")) {
				headers.add("Content-Type", "application/javascript");
			} else {
				headers.add("Content-Type", "text/html;charset=utf-8");
			}
			headers.add("Content-Length", body.length + "");
			response200Header(body.length);
			responseBody(body);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void forwardBody(String body) {
		byte[] contents = body.getBytes();
		headers.add("Content-Type", "text/html;charset=utf-8");
		headers.add("Content-Length", contents.length + "");
		response200Header(contents.length);
		responseBody(contents);
	}
	public void responseHeader(int lengthOfBodyContent) {
		try {
			writeResponseLine();
			processHeaders();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void writeResponseLine() throws IOException {
		String responseLine = "HTTP/1.1 " + String.valueOf(status.getCode()) + " " + status.getReasonPhrase() + " \r\n";
		dos.writeBytes(responseLine);
	}


	public void response200Header(int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void responseBody(byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.writeBytes("\r\n");
			dos.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void sendRedirect(String redirectUrl) {
		try {
			dos.writeBytes("HTTP/1.1 302 Found \r\n");
			processHeaders();
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
}
