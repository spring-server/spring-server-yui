package project.server.spring.framework.servlet;

public interface Servlet {
	void init();

	void service(ServletRequest request, ServletResponse response) throws Exception;

	void destroy();
}
