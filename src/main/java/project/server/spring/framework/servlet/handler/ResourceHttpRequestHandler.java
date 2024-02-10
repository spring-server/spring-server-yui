package project.server.spring.framework.servlet.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.servlet.HttpRequestHandler;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.HttpServletResponse;
import project.server.spring.server.http.HttpStatus;

public class ResourceHttpRequestHandler implements HttpRequestHandler {
	private static final ResourceHttpRequestHandler SINGLE_INSTANCE = new ResourceHttpRequestHandler();
	private static final Logger log = LoggerFactory.getLogger(ResourceHttpRequestHandler.class);
	private static final int BUFFER_SIZE = 1024;
	private static final int BASE_OFFSET = 0;
	private static final int EOF = -1;
	private static final String BASEDIRECTORY = "./";

	public static ResourceHttpRequestHandler getInstance() {
		return SINGLE_INSTANCE;
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uri = request.getRequestURI();
		InputStream inputStream = getInputStream(uri);
		if (inputStream == null) {
			log.debug("Resource not found");
			response.sendError(HttpStatus.BAD_REQUEST);
			return;
		}
		byte[] bytes = readStream(inputStream);
		//TODO: byte Array로 바꾸기
		//ByteBuffer buffer = ByteBuffer.wrap(bytes);
		// OutputStream out = new FileOutputStream("./sample.txt");
		response.getOutputStream().write(bytes);
		// OutputStreamWriter writer = new OutputStreamWriter(out);
		// out.write(bytes);
	}

	private InputStream getInputStream(String uri) {
		String path = getResourcePath(uri);
		InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(path);
		return resourceAsStream;
	}

	private String getResourcePath(String uri) {
		return null;
	}

	private byte[] readStream(InputStream inputStream) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytes;
		while ((bytes = inputStream.read(buffer)) != EOF) {
			byteArrayOutputStream.write(buffer, BASE_OFFSET, bytes);
		}
		return byteArrayOutputStream.toByteArray();
	}
}
