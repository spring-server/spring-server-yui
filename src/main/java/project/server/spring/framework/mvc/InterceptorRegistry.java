package project.server.spring.framework.mvc;

import java.util.ArrayList;
import java.util.List;

import project.server.spring.framework.annotation.Component;
import project.server.spring.framework.servlet.handler.HandlerInterceptor;

@Component
public class InterceptorRegistry {

	List<InterceptorRegistration> registrations;

	public InterceptorRegistry() {
		this.registrations = new ArrayList<>();
	}

	public InterceptorRegistration addInterceptor(HandlerInterceptor interceptor) {
		InterceptorRegistration registration = new InterceptorRegistration(interceptor);
		registrations.add(registration);
		return registration;
	}

	public List<InterceptorRegistration> getInterceptors() {
		return registrations;
	}
}
