package project.server.spring.framework.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanRegistry {
	Map<Class<?>, Object> beans = new HashMap<>();

	public void addBean(Class<?> clazz, Object bean) {
		beans.put(clazz, bean);
	}

	public <T> T getBean(Class<T> clazz) {
		return clazz.cast(beans.get(clazz));
	}

	public Set<Class<?>> getClasses() {
		return beans.keySet();
	}


}

