package project.server.spring.framework.mvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.handler.HandlerInterceptor;

public class InterceptorRegistration {
	private final HandlerInterceptor interceptor;
	private final List<String> uris;
	private int order = 0;

	public InterceptorRegistration(HandlerInterceptor interceptor) {
		if (interceptor == null) {
			throw new IllegalArgumentException("Interceptor is required");
		}
		this.interceptor = interceptor;
		this.uris = new ArrayList<>();

	}

	public InterceptorRegistration addUris(String... uris) {
		this.uris.addAll(Arrays.asList(uris));
		return this;
	}

	public InterceptorRegistration order(int order) {
		this.order = order;
		return this;
	}

	public boolean matches(HttpServletRequest request) {
		for (String uri : uris) {
			if (request.getRequestURI().contains(uri)) {
				return true;
			}
		}
		return false;
	}

	public HandlerInterceptor getInterceptor() {
		return interceptor;
	}
}
