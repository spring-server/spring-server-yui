package project.server.spring.framework.servlet.handler;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

import lombok.extern.slf4j.Slf4j;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.HttpServletResponse;
import project.server.spring.framework.servlet.ModelAndView;

@Slf4j
public class HandlerExecutionChain {
	private int interceptorIndex = -1;
	private final Object handler;
	private final List<HandlerInterceptor> interceptors;

	public HandlerExecutionChain(Object handler) {
		this.handler = handler;
		this.interceptors = new ArrayList<>();
	}

	public boolean applyPreHandle(
		HttpServletRequest request,
		HttpServletResponse response
	) throws Exception {
		for (int i = 0; i < this.interceptors.size(); i++) {
			HandlerInterceptor interceptor = this.interceptors.get(i);
			if (!interceptor.preHandle(request, response, this.handler)) {
				triggerAfterCompletion(request, response, null);
				return false;
			}
			this.interceptorIndex = i;
		}
		return true;
	}

	public void applyPostHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView)
		throws Exception {
		for (int i = this.interceptors.size() - 1; i >= 0; i--) {
			HandlerInterceptor interceptor = this.interceptors.get(i);
			interceptor.postHandle(request, response, this.handler, modelAndView);
		}
	}

	private void triggerAfterCompletion(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		for (int i = this.interceptorIndex; i >= 0; i--) {
			HandlerInterceptor interceptor = this.interceptors.get(i);
			try {
				interceptor.afterCompletion(request, response, this.handler, exception);
			} catch (Throwable throwable) {
				log.error("HandlerInterceptor.afterCompletion threw exception", throwable);
			}
		}
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		HandlerExecutionChain that = (HandlerExecutionChain)object;
		return Objects.equal(handler, that.handler);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(handler);
	}

	@Override
	public String toString() {
		return String.format("%s", handler);
	}

	public Object getHandler() {
		return handler;
	}

	public void addInterceptor(HandlerInterceptor interceptor) {
		this.interceptors.add(interceptor);
	}

}
