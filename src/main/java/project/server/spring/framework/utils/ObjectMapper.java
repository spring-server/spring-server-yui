package project.server.spring.server.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import project.server.spring.server.http.HttpBody;
import project.server.spring.server.http.MediaType;

public class ObjectMapper {
	public static HttpBody readValue(char[] rawbody, MediaType mediaType) {
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
}
