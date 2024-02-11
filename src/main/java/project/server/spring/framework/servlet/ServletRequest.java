package project.server.spring.framework.servlet;

import project.server.spring.framework.http.MediaType;

public interface ServletRequest {
	int getContentLength();

	MediaType getContentType();
}
