package project.server.spring.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.RequestHandler;
import project.server.spring.framework.servlet.ModelAndView;

public class PageProcessor {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

	public String read(String path) throws IOException {
		InputStream fis = getClass().getClassLoader().getResourceAsStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line).append("\n");
		}
		String page = stringBuilder.toString();
		return page;
	}

	public String changeAttributes(ModelAndView modelAndView, String content) {
		for (String key : modelAndView.getAttributeNames()) {
			String keyIndex = "$" + key;
			log.info("key : {} key Index : {}", key, keyIndex);
			content = content.replace(keyIndex, (String)modelAndView.getAttribute(key));
		}
		return content;
	}
}
