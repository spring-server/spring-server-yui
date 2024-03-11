package project.server.spring.framework.view;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModelMap {
	private final Map<String, Object> map = new LinkedHashMap<>();

	public void addAttribute(String attributeName, Object attributeValue) {
		map.put(attributeName, attributeValue);
	}

	public Object getAttribute(String attributeName) {
		return map.get(attributeName);
	}
}
