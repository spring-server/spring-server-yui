package project.server.spring.framework.event.http;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import project.server.spring.framework.http.Cookie;

class AsyncHttpResponseTest {

	@Test
	void addDateHeader() {
		AsyncHttpResponse response = new AsyncHttpResponse();
		response.addDateHeader("test", ZonedDateTime.now());
	}

	@Test
	void sendError() {
		AsyncHttpResponse response = new AsyncHttpResponse();
		try {
			Cookie cookie = new Cookie("euijin", "1234");
			cookie.setDomain("www.naver.com");
			cookie.setMaxAge(1000);
			response.addCookie(cookie);
			response.sendError(400, "euijin");
		} catch (Exception e) {

		}
	}
}
