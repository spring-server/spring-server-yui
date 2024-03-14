package project.server.spring.framework.http;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.servlet.HttpServletResponse;

public class SyncHttpResponse implements HttpServletResponse {
	private static final Logger log = LoggerFactory.getLogger(SyncHttpResponse.class);
	private static final String CRLF = "\r\n";
	//TODO: 관리하는 부분 분리
	private static final String STATIC_RESOURCE_PATH = "./src/main/resources";

	private OutputStream outputStream;
	private final HttpHeaders httpHeaders;
	private final List<Cookie> cookies;
	private final ResponseLine responseLine;

	public SyncHttpResponse(OutputStream outputStream) {
		this.outputStream = outputStream;
		this.httpHeaders = new HttpHeaders();
		this.responseLine = new ResponseLine();
		this.cookies = new ArrayList<>();
		httpHeaders.add("Content-Type", "application/octet-stream");
	}

	@Override
	public void addCookie(Cookie cookie) {
		this.cookies.add(cookie);
		String header = this.generateCookieString(cookie);
		httpHeaders.add("Set-Cookie", header);
	}

	private String generateCookieString(Cookie cookie) {
		StringBuilder sb = new StringBuilder();
		sb.append(cookie.getName()).append("=").append(cookie.getValue());
		if (cookie.getMaxAge() > -1) {
			sb.append("; Max-Age=").append(cookie.getMaxAge());
		}
		if (cookie.getPath() != null) {
			sb.append("; Path=").append(cookie.getPath());
		}
		if (cookie.getDomain() != null) {
			sb.append("; Domain=").append(cookie.getDomain());
		}
		if (cookie.getSecure()) {
			sb.append("; Secure");
		}
		if (cookie.isHttpOnly()) {
			sb.append("; HttpOnly");
		}

		return sb.toString();
	}

	@Override
	public boolean containsHeader(String name) {
		return httpHeaders.contains(name);
	}

	@Override
	public String encodeURL(String var1) {
		return null;
	}

	@Override
	public String encodeRedirectURL(String var1) {
		return null;
	}

	@Override
	public void sendError(int status, byte[] buffer) throws IOException {
		//TODO: SocketChannel이랑 ByteBuffer이용해서 작성할 수 있나?
		this.setStatus(status);
		String responseMessage = generateResponseString(null);
		outputStream.write(responseMessage.getBytes());
		outputStream.write(buffer);
	}

	@Override
	public void sendError(int status) throws IOException {
		this.setStatus(status);
		this.sendError(status, null);
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		this.sendRedirect(location, 302);
	}

	public void sendRedirect(String location, int status) throws IOException {
		this.setStatus(status);
		this.addHeader("Location", location);
		String responseMessage = generateResponseString(null);
		outputStream.write(responseMessage.getBytes());
	}

	@Override
	public void setDateHeader(String name, ZonedDateTime time) {
		this.addDateHeader(name, time);
	}

	@Override
	public void addDateHeader(String name, ZonedDateTime time) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		String date = time.format(dateFormat);
		this.addHeader(name, date);
	}

	@Override
	public void setHeader(String name, String value) {
		httpHeaders.add(name, value);
	}

	@Override
	public void addHeader(String name, String value) {
		httpHeaders.add(name, value);
	}

	@Override
	public void setIntHeader(String name, int value) {
		this.addIntHeader(name, value);
	}

	@Override
	public void addIntHeader(String name, int value) {
		this.addHeader(name, String.valueOf(value));
	}

	@Override
	public void setStatus(int var1) {
		this.responseLine.setHttpStatus(HttpStatus.valueOf(var1));
	}

	@Override
	public int getStatus() {
		return responseLine.getHttpStatus().getCode();
	}

	@Override
	public String getHeader(String name) {
		return httpHeaders.get(name);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return httpHeaders.getAllFields();
	}

	private String generateResponseString(String message) {
		StringBuilder responseLineString = new StringBuilder(responseLine.makeLine()).append(CRLF);
		for (String name : httpHeaders.getAllFields()) {
			responseLineString.append(name).append(": ").append(httpHeaders.get(name)).append(CRLF);
		}
		responseLineString.append(CRLF);
		if (message != null) {
			responseLineString.append(message);
		}
		return responseLineString.toString();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return outputStream;
	}

	@Override
	public void setContentType(String contentType) {
		httpHeaders.add("Content-Type", contentType);
	}

	@Override
	public void dispatch(String url) {
		try {
			log.info(STATIC_RESOURCE_PATH + url);
			byte[] body = Files.readAllBytes(new File(STATIC_RESOURCE_PATH + url).toPath());
			int index = url.indexOf('.');
			if (index == -1) {
				throw new IllegalArgumentException("no file extension");
			}
			String extension = url.substring(index + 1);
			FileExtension findType = FileExtension.findByType(extension);
			httpHeaders.add("Content-Type", findType.getValue());
			httpHeaders.add("Content-Length", String.valueOf(body.length));
			String responseMessage = generateResponseString(null);
			outputStream.write(responseMessage.getBytes());
			outputStream.write(body);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void flush(byte[] body) {
		try {
			httpHeaders.setContentLength(body.length);
			String message = generateResponseString(null);
			outputStream.write(message.getBytes());
			outputStream.write(body);
		} catch (IOException e) {
			log.info(e.getMessage());
		}
	}
}
