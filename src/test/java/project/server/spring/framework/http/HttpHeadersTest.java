package project.server.spring.framework.http;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HttpHeadersTest {
	@ParameterizedTest
	@ValueSource(strings = {"Cookie: name=value; name2=value2; name3=value3"})
	void parse(String headerValue) {
		HttpHeaders headers = new HttpHeaders();
		headers.parse(headerValue);
		List<Cookie> cookies = headers.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println(cookie.getName() + " " + cookie.getValue());
		}
	}
}
