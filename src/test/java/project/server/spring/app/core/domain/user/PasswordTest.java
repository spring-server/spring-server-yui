package project.server.spring.app.core.domain.user;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import project.server.spring.app.core.global.exception.InvalidParameterException;

@DisplayName("암호 값 객체 유효성 조건 테스트")
class PasswordTest {

	@ParameterizedTest()
	@ValueSource(strings = {"1234a", "abcdefg", "123456789", "test12345", ""})
	@DisplayName("유효하지 않은 암호 형식 테스트")
	void testInvalid(String parameter) {
		assertThatThrownBy(() -> new Password(parameter))
			.isInstanceOf(RuntimeException.class)
			.isExactlyInstanceOf(InvalidParameterException.class);
	}

	@ParameterizedTest()
	@ValueSource(strings = {"Test12345", "12345Test"})
	@DisplayName("유효한 암호 형식 테스트")
	void testValid(String parameter) {
		assertEquals(parameter, new Password(parameter).value());
	}

}
