package project.server.spring.framework.mvc;

import project.server.spring.framework.annotation.Component;

@Component
public interface WebMvcConfigurer {
	default void addInterceptors(InterceptorRegistry registry) {
	}
}
