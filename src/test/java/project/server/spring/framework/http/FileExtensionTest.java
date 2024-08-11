package project.server.spring.framework.http;

// import static org.assertj.core.api.Java6Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static project.server.spring.framework.http.FileExtension.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class FileExtensionTest {

	@ParameterizedTest
	@ValueSource(strings = {".css", "js", "ico"})
	void testValidExtension(String parameter) {
		assertNotNull(findByType(parameter));
	}

	@ParameterizedTest
	@ValueSource(strings = {".hello", "eui"})
	void testInvalid(String parameter) {
		assertEquals(DEFAULT, findByType(parameter));
	}

	@ParameterizedTest
	@NullAndEmptySource
	void testNull(String parameter) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> findByType(parameter));
	}
}
