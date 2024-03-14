package project.server.spring.framework.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.utils.IOUtils;

public class SyncHttpRequest implements HttpServletRequest {
	private static final String SESSION_ID = "session_id";
	private static final int BUFFER_SIZE = 1000000;
	private static final Logger log = LoggerFactory.getLogger(SyncHttpRequest.class);
	private static final String CRLF = "\r\n";

	private InputStream inputStream;
	private BufferedReader bufferedReader;
	private final HttpHeaders httpHeaders;
	private final RequestLine requestLine;

	private HttpSession session;
	private char[] body;

	public SyncHttpRequest(InputStream inputStream) throws IOException {
		this.inputStream = inputStream;
		this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		this.requestLine = new RequestLine();
		this.httpHeaders = new HttpHeaders();
		readClientMessage();
	}

	//TODO: path vs uri
	@Override
	public String getRequestURI() {
		return requestLine.getPath();
	}

	@Override
	public HttpMethod getHttpMethod() {
		return requestLine.getHttpMethod();
	}

	@Override
	public QueryParams getQueryParams() {
		return requestLine.getQueryParms();
	}

	@Override
	public HttpSession getSession() {
		List<Cookie> cookies = getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.hasSessionId()) {
				return new InMemoryServerSession(cookie.getValue());
			}
		}
		if (session == null) {
			return null;
		}
		return new InMemoryServerSession(session.getId());
	}

	@Override
	public HttpSession createSession() {
		String sessionId = UUID.randomUUID().toString();
		SessionStore.save(new Session(sessionId));
		session = new InMemoryServerSession(sessionId);
		return session;
	}

	@Override
	public List<Cookie> getCookies() {
		return httpHeaders.getCookies();
	}

	@Override
	public String getHeader(String name) {
		return httpHeaders.get(name);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return httpHeaders.getAllFields();
	}

	@Override
	public int getIntHeader(String name) {
		return Integer.parseInt(name);
	}

	@Override
	public char[] getBody() {
		return body;
	}

	@Override
	public int getContentLength() {
		return httpHeaders.getContentLength();
	}

	@Override
	public String getContentType() {
		return httpHeaders.getContentType().getValue();
	}

	//TODO: 수정하기
	public void readClientMessage() throws IOException {
		readRequestLine();
		readHeaders();
		readBody();
	}

	private void readRequestLine() throws IOException {
		String line = bufferedReader.readLine();
		requestLine.parse(line);
	}

	private void readHeaders() throws IOException {
		String line;
		while (!"".equals(line = bufferedReader.readLine())) {
			httpHeaders.parse(line);
		}
	}

	private void readBody() throws IOException {
		if (httpHeaders.getContentLength() > 0) {
			body = IOUtils.readData(bufferedReader, httpHeaders.getContentLength());
		}
	}
}
