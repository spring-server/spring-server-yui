package project.server.spring.framework.configuration;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class ConfigLoader {
	private ConfigMap configMap;

	public ConfigMap getConfigMap() throws IOException {
		if (configMap == null) {
			return loadConfigMap("/application.yml");
		}
		return configMap;
	}

	private ConfigMap loadConfigMap(String resourcePath) throws IOException {
		InputStream inputStream = ConfigLoader.class.
			getResourceAsStream(resourcePath);
		if (inputStream == null) {
			throw new IllegalArgumentException("resource not found " + resourcePath);
		}
		ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
		objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.KebabCaseStrategy());
		return objectMapper.readValue(inputStream, ConfigMap.class);
	}

}
