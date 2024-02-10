package project.server.spring.app.core.domain.user;

import java.util.Objects;

import project.server.spring.app.core.global.DuplicatedUserException;

public class User {
	private Long id;
	private Name name;
	private Password password;

	private Email email;

	public User(String name, String password, String email) {
		this.name = new Name(name);
		this.password = new Password(password);
		this.email = new Email(email);
	}

	public Long getId() {
		return id;
	}

	public Name getName() {
		return name;
	}

	public Password getPassword() {
		return password;
	}

	public Email getEmail() {
		return email;
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
			throw new DuplicatedUserException("user is duplicated");
		}
		this.id = id;
	}

	public boolean isNew() {
		return this.id == null;
	}

	public boolean hasEmail(String email) {
		return this.email.isSame(email);
	}
}
