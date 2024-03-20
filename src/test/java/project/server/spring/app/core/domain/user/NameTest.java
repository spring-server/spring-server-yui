package project.server.spring.app.core.domain.user;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import project.server.spring.app.core.global.exception.InvalidParameterException;

@DisplayName("이름 값 객체 유효성 조건 테스트")
class NameTest {
	@ParameterizedTest()
	@ValueSource(strings = {"$사용자", ""})
	@DisplayName("유효하지 않은 이름 테스트")
	void testInvalid(String parameter) {
		assertThatThrownBy(() -> new Name(parameter))
			.isInstanceOf(RuntimeException.class)
			.isExactlyInstanceOf(InvalidParameterException.class);
	}

	@ParameterizedTest()
	@ValueSource(strings = {"사용자", "kate", "test사용자"})
	@DisplayName("유효한 이름 형식 테스트")
	void testValid(String parameter) {
		assertEquals(parameter, new Name(parameter).value());
	}
}
