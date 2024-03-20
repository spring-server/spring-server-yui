package project.server.spring.framework.view;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModelMap {
	private Map<String, Object> map = new LinkedHashMap<>();

	public ModelMap() {
	}

	public ModelMap(Map<String, Object> map) {
		this.map = map;
	}

	public void addAttribute(String attributeName, Object attributeValue) {
		map.put(attributeName, attributeValue);
	}

	public Object getAttribute(String attributeName) {
		return map.get(attributeName);
	}

	public Collection<String> getAttributeNames() {
		return map.keySet();
	}
}
