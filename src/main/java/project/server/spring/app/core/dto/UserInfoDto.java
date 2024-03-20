package project.server.spring.app.core.dto;

import java.util.Map;

import project.server.spring.app.core.domain.user.User;

public class UserInfoDto {
	private String name;
	private String email;
	private String password;
	private String phoneNumber;

	public UserInfoDto() {
	}

	public UserInfoDto(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getMaskingPassword();
		this.phoneNumber = user.getPhoneNumber();
	}

	public Map<String, Object> toMap(Map<String, Object> map) {
		map.put("name", name);
		map.put("email", email);
		map.put("password", password);
		map.put("phoneNumber", phoneNumber);
		return map;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
}
