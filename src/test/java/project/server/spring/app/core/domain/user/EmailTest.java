package project.server.spring.app.core.domain.user;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import project.server.spring.app.core.global.exception.InvalidParameterException;

@DisplayName("이메일 값 객체 유효성 조건 테스트")
class EmailTest {

	@ParameterizedTest()
	@ValueSource(strings = {"@test.com", "test1234", "사용자@test.com", ""})
	@DisplayName("유효하지 않은 이몌일 형식 테스트")
	void testInvalid(String parameter) {
		assertThatThrownBy(() -> new Email(parameter))
			.isInstanceOf(RuntimeException.class)
			.isExactlyInstanceOf(InvalidParameterException.class);
	}

	@ParameterizedTest()
	@ValueSource(strings = {"sample@test.com", "euijin@sample.kr"})
	@DisplayName("유효한 이메일 테스트")
	void testValid(String parameter) {
		assertEquals(parameter, new Email(parameter).emailAddress());
	}
}
