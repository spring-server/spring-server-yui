package project.server.spring.app.core.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record PhoneNumber(String value) {
	private static final Pattern pattern = Pattern.compile("^(\\+82-)?0?1[0-9]-\\d{3,4}-\\d{4}$");

	public PhoneNumber {
		validate(value);
	}

	private void validate(String value) {
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("phone number format is invalid. enter like 010-0000-0000");
		}
	}

	@Override
	public String toString() {
		return value;
	}
}
