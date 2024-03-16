package project.server.spring.app.core.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.server.spring.app.core.global.exception.InvalidParameterException;

public record Name(String value) {
	/**
	 * 숫자, 한글, 영어만 허용
	 */
	private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9가-힣]+$");

	public Name {
		validate(value);
	}

	private void validate(String value) {
		if (value == null) {
			throw new InvalidParameterException("name is null");
		}
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches()) {
			String message = String.format("name format is invalid: %s", value);
			throw new InvalidParameterException(message);
		}
	}

	@Override
	public String toString() {
		return value;
	}
}
