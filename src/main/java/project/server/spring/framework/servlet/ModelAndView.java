package project.server.spring.framework.servlet;

import project.server.spring.framework.view.ModelMap;

public class ModelAndView {
	private Object view;
	private ModelMap model;

	public ModelAndView() {
	}

	public ModelAndView(String viewName) {
		this.view = viewName;
	}

}
