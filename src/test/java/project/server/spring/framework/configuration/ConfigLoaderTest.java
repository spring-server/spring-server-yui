package project.server.spring.framework.configuration;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConfigLoaderTest {
	@Test
	@DisplayName("configMap 정상 동작 테스트")
	void testLoadConfigMap() throws IOException {
		ConfigLoader configLoader = new ConfigLoader();
		ConfigMap configMap = configLoader.getConfigMap();
		DataSourceConfig datasource = configMap.getSpring().getDatasource();
	}

}
