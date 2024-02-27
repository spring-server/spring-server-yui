package project.server.spring.framework.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CookiesTest {

	@Test
	@DisplayName("Cookie 헤더 테스트")
	void createCookieHeader() {
		Cookies cookies = Cookies.create("yummy_cookie=choco; tasty_cookie=strawberry");
		Assertions.assertEquals("choco", cookies.get("yummy_cookie"));
		Assertions.assertEquals("strawberry", cookies.get("tasty_cookie"));
	}

	@Test
	@DisplayName("Set-Cookie 헤더 테스트")
	void sendSetCookieHeader() {
		Cookies cookies = Cookies.create("yummy_cookie=choco; tasty_cookie=strawberry");
		HttpHeaders headers = new HttpHeaders();
		headers.sendCookie(cookies);
		//TODO: Set-Cookie 헤더 2개 이상 설정할 수 있도록 하기
		Assertions.assertEquals("tasty_cookie=strawberry", headers.get("Set-Cookie"));
	}
}
