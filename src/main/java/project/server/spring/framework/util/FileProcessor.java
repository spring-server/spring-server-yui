package project.server.spring.framework.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.server.RequestHandler;

public class FileProcessor {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	public byte[] read(String path) throws IOException {
		InputStream fis = getClass().getClassLoader().getResourceAsStream(path);
		log.info("fis : {}", fis);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line).append("\n");
		}
		String index = stringBuilder.toString();
		byte[] buffer = index.getBytes(StandardCharsets.UTF_8);
		return buffer;
	}

	public void test(String path) throws IOException {
		InputStream fis = getClass().getClassLoader().getResourceAsStream(path);
		BufferedImage image = ImageIO.read(fis);
	}
}
