package project.server.spring.framework.servlet;

import java.io.IOException;
import java.io.OutputStream;

public interface ServletResponse {
	OutputStream getOutputStream() throws IOException;

	;
}
