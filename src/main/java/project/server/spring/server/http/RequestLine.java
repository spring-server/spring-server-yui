package project.server.spring.server.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.server.common.utils.HttpRequestUtils;

public class RequestLine {
	private final HttpMethod httpMethod;

	private final String path;

	private Map<String, String> queryParms;

	private static final Logger log = LoggerFactory.getLogger(RequestLine.class);
	public RequestLine(String rawLine) {
		log.debug("request line: {}", rawLine);
		String[] tokens = rawLine.split(" ");
		this.httpMethod = HttpMethod.valueOf(tokens[0]);
		String[] uri = tokens[1].split("\\?");
		this.path = uri[0];

		if (uri.length == 2) {
			log.info("check");
			queryParms = new HashMap<>();
			String[] keyAndValues = uri[1].split("&");
			for (String keyAndValue : keyAndValues) {
				String[] keyAndValueArray = keyAndValue.split("=");
				String key = keyAndValueArray[0];
				String value = keyAndValueArray[1];
				HttpRequestUtils.parseQueryString(keyAndValue);
				queryParms.put(key, value);
			}
		}
		else {
			queryParms = null;
		}
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getPath() {
		return path;
	}

	public Map<String, String> getQueryParms() {
		if (queryParms == null) {
			return null;
		}
		return new HashMap<>(queryParms);
	}

	public Set<String> getQueryParamKey(){
		return queryParms.keySet();
	}

	public String getQueryParamValue(String key) {
		return queryParms.get(key);
	}

	@Override
	public String toString() {
		return "RequestLine{" +
			"httpMethod=" + httpMethod +
			", path='" + path + '\'' +
			", queryParms=" + queryParms +
			'}';
	}
}
