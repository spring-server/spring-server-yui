package project.server.spring.framework.servlet;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HandlerExecutionChain {
	private static final Log log = LogFactory.getLog(HandlerExecutionChain.class);
	private final Object handler;
	private final List<HandlerInterceptor> interceptorList = new ArrayList<>();

	public HandlerExecutionChain(Object handler) {
		this.handler = handler;
	}

	public Object getHandler() {
		return handler;
	}
}
