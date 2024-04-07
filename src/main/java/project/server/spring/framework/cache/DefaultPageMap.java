package project.server.spring.framework.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DefaultPageMap {
	private static final String EXTENSION = ".html";
	private static final String ERROR_PAGE_PATH = "/assets/error/";
	private final Map<Integer, String> pageMap = new HashMap<>();

	private static final int CLIENT_ERROR_DEFAULT_PAGE = 400;
	private static final int SERVER_ERROR_DEFAULT_PAGE = 500;

	public DefaultPageMap() {
		loadDefaultPages();
	}

	private void loadDefaultPages() {
		loadPage(CLIENT_ERROR_DEFAULT_PAGE);
		loadPage(SERVER_ERROR_DEFAULT_PAGE);
	}

	private void loadPage(int status) {
		try {
			String filePath = ERROR_PAGE_PATH + status + EXTENSION;
			InputStream inputStream = DefaultPageMap.class.getResourceAsStream(filePath);
			if (inputStream == null) {
				throw new IOException();
			}
			byte[] bytes = inputStream.readAllBytes();
			String page = new String(bytes);
			pageMap.put(status, page);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getPage(int status) {
		if (pageMap.get(status) != null) {
			return pageMap.get(status);
		}
		return getDefaultPage(status);
	}

	private String getDefaultPage(int status) {
		int defaultErrorPageCode = (status / 100) * 100;
		return pageMap.get(defaultErrorPageCode);
	}
}
