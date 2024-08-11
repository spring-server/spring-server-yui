package project.server.spring.app.core.global.auth;

import project.server.spring.framework.annotation.Configuration;
import project.server.spring.framework.mvc.InterceptorRegistry;
import project.server.spring.framework.mvc.WebMvcConfigurer;

@Configuration
public class AuthConfig implements WebMvcConfigurer {
	private final AuthenticationInterceptor authenticationInterceptor;

	public AuthConfig(AuthenticationInterceptor authenticationInterceptor) {
		this.authenticationInterceptor = authenticationInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor)
			.addUris("/profile")
			.order(1);
	}
}
