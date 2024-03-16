package project.server.spring.app.core.domain.user;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import project.server.spring.app.core.global.exception.InvalidParameterException;

@DisplayName("전화번호 값 객체 유효성 조건 테스트")
class PhoneNumberTest {
	@ParameterizedTest()
	@ValueSource(strings = {"01031112224", "010-3222", "02)824-2222"})
	@DisplayName("유효하지 않은 전화번호 형식 테스트")
	void testInvalid(String parameter) {
		assertThatThrownBy(() -> new PhoneNumber(parameter))
			.isInstanceOf(RuntimeException.class)
			.isExactlyInstanceOf(InvalidParameterException.class);
	}

	@ParameterizedTest()
	@ValueSource(strings = {"010-1013-2024", "011-322-2222", "010-243-2132"})
	@DisplayName("유효한 전화번호 형식 테스트")
	void testValid(String parameter) {
		assertEquals(parameter, new PhoneNumber(parameter).value());
	}
}
