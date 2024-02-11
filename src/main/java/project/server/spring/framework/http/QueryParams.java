package project.server.spring.framework.http;

import java.util.Map;

import project.server.spring.framework.utils.HttpRequestUtils;

public class QueryParams {
	private final Map<String, String> queryParamsMap;

	public QueryParams(String queryString) {
		queryParamsMap = HttpRequestUtils.parseURLencodedKeyValue(queryString);
	}

	public String get(String key) {
		return queryParamsMap.get(key);
	}
}
