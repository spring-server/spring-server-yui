package project.server.spring.server.http;

public enum HttpMethod {
	GET, POST, PUT, DELETE;

	public boolean isPost() {
		return this == POST;
	}

	public boolean isGet() {
		return this == GET;
	}
}
