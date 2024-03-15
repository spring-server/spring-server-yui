package project.server.spring.framework.http;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("세션 만료 단위 테스트")
class SessionStoreTest {
	@Test
	@DisplayName("유효 세션 테스트")
	void validSessionTest() {
		long now = System.currentTimeMillis();
		long past = System.currentTimeMillis() - 1_000;
		Session session = new Session("test", past, now);
		assertFalse(session.isExpired());
	}

	@Test
	@DisplayName("만료 세션 테스트")
	void expiredSessionTest() {
		long current = Instant.now().minusSeconds(601).toEpochMilli();
		long past = Instant.now().minusSeconds(1000).toEpochMilli();
		Session session = new Session("test", past, current);
		assertTrue(session.isExpired());
	}
}
