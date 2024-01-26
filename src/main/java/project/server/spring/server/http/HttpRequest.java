package project.server.spring.server.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.server.common.utils.IOUtils;

public class HttpRequest {
	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

	private HttpMethod httpMethod;
	private String path;

	private MediaType contentType;

	private String body;

	private HttpHeaders headers;
	//TODO: queryParam 클래스로 만들기
	private Map<String, String> queryParams;



	public HttpRequest(InputStream is) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			RequestLine requestLine = createRequestLine(br);
			this.httpMethod = requestLine.getHttpMethod();
			this.path = requestLine.getPath();
			this.queryParams = requestLine.getQueryParms();
			this.headers = createHeaders(br);
			this.contentType = headers.getContentType();
			log.info("data = {}", headers);
			this.body = IOUtils.readData(br, (int) headers.getContentLength());
			log.info("data = {}", body);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private RequestLine createRequestLine(BufferedReader br) throws IOException {
		String line = br.readLine();
		log.info("data : {}", line);
		if (line == null) {
			throw new IllegalStateException("invalid request format");
		}
		return new RequestLine(line);
	}

	private HttpHeaders createHeaders(BufferedReader br) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		String line;
		while (!(line = br.readLine()).equals("")) {
			headers.parse(line);
		}
		return headers;
	}

	private Long getContentLength() {
		return headers.getContentLength();
	}

	public MediaType getContentType() {
		return headers.getContentType();
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getHeader(String key) {
		return headers.get(key);
	}

	public String getPath() {
		return path;
	}

	public String getBody() {
		return body;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}
}
