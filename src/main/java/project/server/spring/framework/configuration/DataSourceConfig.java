package project.server.spring.framework.configuration;

public class DataSourceConfig {
	private String driverClassName;
	private String url;
	private String username;
	private String password;

	public DataSourceConfig() {
	}

	public DataSourceConfig(String driverClassName, String url, String username, String password) {
		this.driverClassName = driverClassName;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
