package project.server.spring.server.http;

import java.util.Map;

import project.server.spring.server.common.utils.HttpRequestUtils;

public class QueryParams {
	private final Map<String, String> queryParamsMap;

	public QueryParams(String queryString) {
		queryParamsMap = HttpRequestUtils.parseQueryString(queryString);
	}

	public String get(String key) {
		return queryParamsMap.get(key);
	}
}
