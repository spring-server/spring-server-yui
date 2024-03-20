package project.server.spring.app.core.domain.user;

import static project.server.spring.app.core.global.error.ErrorMessage.*;

import java.util.Objects;

import project.server.spring.app.core.global.exception.AuthenticationException;
import project.server.spring.app.core.global.exception.DuplicatedUserException;

public class User {
	private static final String EMPTY_STRING = "";
	private static final String MASKING = "*";
	private Long id;
	private Name name;
	private Password password;

	private PhoneNumber phoneNumber;

	private Email email;

	public User(String name, String password, String email) {
		this.name = new Name(name);
		this.password = new Password(password);
		this.email = new Email(email);
	}

	public Long getId() {
		return id;
	}

	public String getPhoneNumber() {
		if (this.phoneNumber != null) {
			return phoneNumber.value();
		}
		return EMPTY_STRING;
	}

	public String getName() {
		if (this.name != null) {
			return name.value();
		}
		return EMPTY_STRING;
	}

	public String getPassword() {
		if (this.password != null) {
			return password.value();
		}
		return EMPTY_STRING;
	}

	public String getMaskingPassword() {
		if (this.password != null) {
			int length = password.getLength();
			return MASKING.repeat(length);
		}
		return EMPTY_STRING;
	}

	public String getEmail() {
		if (this.email != null) {
			return email.emailAddress();
		}
		return EMPTY_STRING;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof User user)) {
			return false;
		}
		return getId().equals(user.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return String.format("id:%s, name:%s", id, name);
	}

	public void initializeId(Long id) {
		if (!isNew()) {
			throw new DuplicatedUserException(USER_DUPLICATED.getMessage());
		}
		this.id = id;
	}

	public boolean isNew() {
		return this.id == null;
	}

	public boolean hasEmail(String email) {
		return this.email.isSame(email);
	}

	public void validatePassword(String password) {
		if (!this.password.match(password)) {
			throw new AuthenticationException(INVALID_PASSWORD.getMessage());
		}
	}

	public void update(String name, String password, String email, String phoneNumber) {
		this.name = new Name(name);
		this.password = new Password(password);
		this.email = new Email(email);
		this.phoneNumber = new PhoneNumber(phoneNumber);
	}
}
