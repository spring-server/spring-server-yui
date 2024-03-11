package project.server.spring.framework.servlet;

import java.util.Map;

public interface View {
	default String getContentType() {
		return null;
	}

	void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
		throws Exception;
}
