package project.server.spring.framework.context;

import java.util.List;

import project.server.spring.framework.mvc.InterceptorRegistry;
import project.server.spring.framework.mvc.WebMvcConfigurer;

public class WebMvcConfigurerComposite {
	private final List<WebMvcConfigurer> configurers;

	public WebMvcConfigurerComposite() {
		configurers = ApplicationContext.getBeans(WebMvcConfigurer.class);
		InterceptorRegistry interceptorRegistry = ApplicationContext.getBean(InterceptorRegistry.class);
		for (WebMvcConfigurer configurer : configurers) {
			configurer.addInterceptors(interceptorRegistry);
		}
	}
}
