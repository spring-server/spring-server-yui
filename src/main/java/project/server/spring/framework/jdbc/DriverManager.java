package project.server.spring.framework.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import project.server.spring.framework.annotation.Component;
import project.server.spring.framework.configuration.DataSourceConfig;

@Component
public class DriverManager {
	private final DataSource dataSource;

	public DriverManager(DataSourceConfig dataSourceConfig) {
		this.dataSource = createDataSource(dataSourceConfig);
	}

	private DataSource createDataSource(DataSourceConfig dataSourceConfig) {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(dataSourceConfig.getDriverClassName());
		basicDataSource.setUrl(
			dataSourceConfig.getUrl());
		basicDataSource.setUsername(dataSourceConfig.getUsername());
		basicDataSource.setPassword(dataSourceConfig.getPassword());
		return basicDataSource;
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}
