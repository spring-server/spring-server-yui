package project.server.spring.framework.servlet;

import java.util.Collection;
import java.util.Map;

import project.server.spring.framework.view.ModelMap;

public class ModelAndView {
	private String view;
	private ModelMap model;

	public ModelAndView() {
	}

	public ModelAndView(String viewName) {
		this.view = viewName;
	}

	public void addAttribute(String attributeName, Object attributeValue) {
		if (model == null) {
			model = new ModelMap();
		}
		model.addAttribute(attributeName, attributeValue);
	}

	public Object getAttribute(String attributeName) {
		if (model == null) {
			return null;
		}
		return model.getAttribute(attributeName);
	}

	public String getView() {
		return view;
	}

	public void setModelMap(Map<String, Object> map) {
		model = new ModelMap(map);
	}

	public boolean hasModelMap() {
		return model != null;
	}

	public Collection<String> getAttributeNames() {
		if (model == null) {
			return null;
		}
		return model.getAttributeNames();
	}
}
