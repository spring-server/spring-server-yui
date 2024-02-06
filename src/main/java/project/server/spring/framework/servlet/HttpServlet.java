package project.server.spring.framework.servlet;

import project.server.spring.server.http.HttpMethod;

public abstract class HttpServlet implements Servlet {
	@Override
	public void init() {

	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest;
		HttpServletResponse httpServletResponse;
		httpServletRequest = (HttpServletRequest)request;
		httpServletResponse = (HttpServletResponse)response;
		service(httpServletRequest, httpServletResponse);
	}

	@Override
	public void destroy() {

	}

	protected void service(
		HttpServletRequest request,
		HttpServletResponse response
	) throws Exception {
		HttpMethod method = request.getHttpMethod();
		if (method.isGet()) {
			doGet(request, response);
			return;
		}
		if (method.isPost()) {
			doPost(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
	}
}
