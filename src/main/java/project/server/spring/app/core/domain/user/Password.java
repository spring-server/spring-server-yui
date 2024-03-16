package project.server.spring.app.core.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.server.spring.app.core.global.error.ErrorMessage;
import project.server.spring.app.core.global.exception.InvalidParameterException;

public record Password(String value) {
	/**
	 * 정규식 : 대문자 1개 이상, 소문자 1개이상, 숫자 1개이상, 8개 이상
	 */
	private static final Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,20}$");

	public Password {
		validate(value);
	}

	private void validate(String value) {
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches()) {
			throw new InvalidParameterException(String.format(ErrorMessage.INVALID_FORMAT.getMessage(), "password"));
		}
	}

	@Override
	public String toString() {
		return value;
	}

	public boolean match(String value) {
		return this.value.equals(value);
	}

	public int getLength() {
		return value.length();
	}
}
