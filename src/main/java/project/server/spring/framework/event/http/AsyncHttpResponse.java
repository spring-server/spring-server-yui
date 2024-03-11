package project.server.spring.framework.event.http;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import project.server.spring.framework.http.Cookie;
import project.server.spring.framework.http.HttpHeaders;
import project.server.spring.framework.http.HttpStatus;
import project.server.spring.framework.http.ResponseLine;

public class AsyncHttpResponse implements TempHttpServletResponse {
	private static final String httpVersion = "HTTP/1.1";
	private static final String CRLF = "\r\n";

	private SocketChannel socketChannel;
	private final HttpHeaders httpHeaders;
	private final List<Cookie> cookies;
	private final ResponseLine responseLine;

	public AsyncHttpResponse() {
		this.httpHeaders = new HttpHeaders();
		this.responseLine = new ResponseLine();
		this.cookies = new ArrayList<>();
		httpHeaders.add("Content-Type", "application/octet-stream");
	}

	public AsyncHttpResponse(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
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
	public void sendError(int status, String meesage) throws IOException {
		//TODO: SocketChannel이랑 ByteBuffer이용해서 작성할 수 있나?
		this.setStatus(status);
		String responseMessage = generateResponseString(meesage);
		System.out.println(responseMessage);
		// ByteBuffer buffer = ByteBuffer.wrap(responseMessage.getBytes(StandardCharsets.UTF_8));
		// while (buffer.hasRemaining()) {
		// 	socketChannel.write(buffer);
		// }
		// socketChannel.close();
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
}
