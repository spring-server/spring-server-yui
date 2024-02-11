package project.server.spring.framework.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.utils.HttpRequestUtils;

public class RequestLine {
	private static final String SPACE = " ";
	private final HttpMethod httpMethod;

	private final String path;

	private final QueryParams queryParams;
	private static final Logger log = LoggerFactory.getLogger(RequestLine.class);

	public RequestLine(String rawLine) {
		log.debug("request line: {}", rawLine);
		String[] tokens = rawLine.split(SPACE);
		this.httpMethod = HttpMethod.valueOf(tokens[0]);
		if (hasQueryString(tokens[1])) {
			HttpRequestUtils.Pair pair = getUriPair(tokens[1]);
			this.path = pair.getKey();
			this.queryParams = new QueryParams(pair.getValue());
		} else {
			this.path = tokens[1];
			this.queryParams = null;
		}
	}

	private boolean hasQueryString(String uri) {
		return uri.contains("?");
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

	private HttpRequestUtils.Pair getUriPair(String uri) {
		return HttpRequestUtils.parseUri(uri);
	}
}
