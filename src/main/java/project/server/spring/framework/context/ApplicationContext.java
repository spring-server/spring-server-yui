package project.server.spring.framework.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import project.server.spring.framework.annotation.Bean;
import project.server.spring.framework.annotation.Component;
import project.server.spring.framework.annotation.Configuration;

public class ApplicationContext {
	private static final Map<Class<?>, Object> registeredTypeMap = new HashMap<>();
	private static final Map<String, Object> registeredBeanMap = new HashMap<>();
	private static Reflections reflections;

	public ApplicationContext(String basePackage) throws
		Exception {
		reflections = getReflections(basePackage);
		Set<Class<?>> components = reflections.getTypesAnnotatedWith(Component.class);
		Set<Class<?>> configurations = reflections.getTypesAnnotatedWith(Configuration.class);
		componentScan(components);
		registerByConfigurations(configurations);
	}

	private Reflections getReflections(String basePackage) {
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
			.setScanners(Scanners.SubTypes, Scanners.TypesAnnotated);
		configurationBuilder.addUrls(ClasspathHelper.forPackage(basePackage));
		return new Reflections(configurationBuilder);
	}

	private void componentScan(Set<Class<?>> components) throws
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException {
		for (Class<?> component : components) {
			if (isInstance(component)) {
				add(component);
			}
		}
		for (Class<?> instance : components) {
			if (isInstance(instance)) {
				registerByBeanName(instance);
			}
		}
	}

	private void registerByConfigurations(Set<Class<?>> configurations) throws Exception {
		for (Class<?> configClass : configurations) {
			Object configInstance = createConfiguration(configClass);
			Method[] methods = configClass.getDeclaredMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(Bean.class)) {
					processBeanMethod(configInstance, method);
				}
			}
		}
	}

	private Object createConfiguration(Class<?> clazz) throws Exception {
		Constructor<?> constructor = clazz.getDeclaredConstructor();
		constructor.setAccessible(true);
		return constructor.newInstance();
	}

	private void processBeanMethod(Object configInstance, Method method) throws Exception {
		Class<?> returnType = method.getReturnType();
		if (!Modifier.isStatic(method.getModifiers()) && returnType != void.class) {
			Object bean = method.invoke(configInstance);
			registeredTypeMap.put(returnType, bean);
			registeredBeanMap.put(method.getName(), bean);
		}
	}

	private void registerByBeanName(Class<?> clazz) {
		Object instance = registeredTypeMap.get(clazz);
		if (instance != null) {
			registeredBeanMap.put(clazz.getSimpleName(), instance);
		}
	}

	private void add(Class<?> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
		if (registeredTypeMap.containsKey(clazz)) {
			return;
		}
		if (clazz.isInterface()) {
			addInterfaceInstance(clazz);
			return;
		}
		addClassInstance(clazz);
	}

	private void addClassInstance(Class<?> clazz) throws
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException {
		Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		Class<?>[] parameterTypes = constructor.getParameterTypes();
		Object[] params = new Object[parameterTypes.length];
		for (int index = 0; index < parameterTypes.length; index++) {
			Class<?> parameterType = parameterTypes[index];
			if (!registeredTypeMap.containsKey(parameterType)) {
				add(parameterType);
			}
			params[index] = registeredTypeMap.get(parameterType);
		}
		Object instance = constructor.newInstance(params);
		registeredTypeMap.put(clazz, instance);
	}

	private void addInterfaceInstance(Class<?> clazz) throws
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException {
		@SuppressWarnings("unchecked")
		Set<Class<?>> implementations = reflections.getSubTypesOf((Class<Object>)clazz);
		if (implementations.isEmpty()) {
			throw new IllegalStateException("No implementation found for interface: " + clazz.getName());
		}
		Class<?> subTypeClass = implementations.iterator().next();
		add(subTypeClass);
	}

	private boolean isInstance(Class<?> clazz) {
		return !clazz.isAnnotation() && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers());
	}

	public static <T> T getBean(Class<T> clazz) {
		return clazz.cast(registeredTypeMap.get(clazz));
	}

	public static <T> T getBean(String beanName) {
		@SuppressWarnings("unchecked")
		T bean = (T)registeredBeanMap.get(beanName);
		return bean;
	}

	public static Collection<Object> getAllBeans() {
		return registeredBeanMap.values();
	}
}
