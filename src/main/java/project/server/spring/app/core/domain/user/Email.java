package project.server.spring.app.core.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Email(String emailAddress) {
	private static final int USERNAME_INDEX = 0;
	private static final int DOMAIN_INDEX = 1;
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

	public Email {
		validate(emailAddress);
	}

	private void validate(String emailAddress) {
		Matcher matcher = EMAIL_PATTERN.matcher(emailAddress);
		if (!matcher.matches()) {
			String message = String.format("invalid email address: %s", emailAddress);
			throw new IllegalArgumentException(message);
		}
	}

	public String getUsername() {
		return emailAddress.split("@")[USERNAME_INDEX];
	}

	public String getDomain() {
		return emailAddress.split("@")[DOMAIN_INDEX];
	}

	@Override
	public String toString() {
		return emailAddress;
	}

	public boolean isSame(String emailAddress) {
		return this.emailAddress.equals(emailAddress);
	}
}
