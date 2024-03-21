package project.server.spring.app.core.domain.user;

import static project.server.spring.app.core.global.error.ErrorMessage.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.server.spring.app.core.global.exception.InvalidParameterException;

public record PhoneNumber(String value) {
	private static final Pattern pattern = Pattern.compile("^(\\+82-)?0?1[0-9]-\\d{3,4}-\\d{4}$");

	public PhoneNumber {
		validate(value);
	}

	private void validate(String value) {
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches()) {
			throw new InvalidParameterException(String.format(INVALID_FORMAT.getMessage(), "phone number"));
		}
	}

	@Override
	public String toString() {
		return value;
	}

	public static PhoneNumber optional(String value) {
		if (value == null) {
			return null;
		}
		return new PhoneNumber(value);
	}
}
