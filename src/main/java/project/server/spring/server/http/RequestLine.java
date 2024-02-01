package project.server.spring.server.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.server.common.utils.HttpRequestUtils;

public class RequestLine {
	private final HttpMethod httpMethod;

	private final String path;

	private final QueryParams queryParams;
	private static final Logger log = LoggerFactory.getLogger(RequestLine.class);

	public RequestLine(String rawLine) {
		log.debug("request line: {}", rawLine);
		String[] tokens = rawLine.split(" ");
		this.httpMethod = HttpMethod.valueOf(tokens[0]);
		HttpRequestUtils.Pair pair = HttpRequestUtils.parseUri(tokens[1]);
		this.path = pair.getKey();
		this.queryParams = new QueryParams(pair.getValue());
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getPath() {
		return path;
	}

	public QueryParams getQueryParms() {
		return queryParams;
	}
}
