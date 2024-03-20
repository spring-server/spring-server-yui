package project.server.spring.framework.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.server.spring.framework.http.HttpBody;
import project.server.spring.framework.http.MediaType;

public class BodyParser {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static HttpBody readForm(char[] rawbody, MediaType mediaType) {
		if (rawbody == null) {
			throw new IllegalArgumentException("http body is empty");
		}
		String bodyString = new String(rawbody);
		//TODO: UTF_8 인코딩 해결
		String decodedBody;
		try {
			decodedBody = URLDecoder.decode(bodyString, "UTF-8");
		} catch (UnsupportedEncodingException exception) {
			throw new IllegalStateException("http body UTF-8 decoding error");
		}
		if (mediaType.equals(MediaType.APPLICATION_FORM_URLENCODED)) {
			Map<String, String> params = HttpRequestUtils.parseURLencodedKeyValue(decodedBody);
			return new HttpBody(params);
		}
		return null;
	}

	public static <T> T readJson(char[] rawbody, MediaType mediaType, Class<T> valueType) throws
		JsonProcessingException {
		return objectMapper.readValue(new String(rawbody), valueType);

	}

}
