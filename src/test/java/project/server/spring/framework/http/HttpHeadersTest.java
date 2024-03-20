package project.server.spring.framework.http;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("Http Header 테스트")
class HttpHeadersTest {
	private Logger logger = LoggerFactory.getLogger(HttpHeadersTest.class);

	@DisplayName("cookie 파싱 확인 용도")
	@ParameterizedTest
	@ValueSource(strings = {"Cookie: name=value; name2=value2; name3=value3"})
	void parse(String headerValue) {
		HttpHeaders headers = new HttpHeaders();
		headers.parse(headerValue);
		List<Cookie> cookies = headers.getCookies();
		for (Cookie cookie : cookies) {
			logger.info("cookie name:{}, cookie value:{}", cookie.getName(), cookie.getValue());
		}
	}
}
