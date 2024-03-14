package project.server.spring.app.core.dto;

import java.util.Map;

import project.server.spring.app.core.domain.user.User;

public class UserInfoDto {
	private final String name;
	private final String email;
	private final String password;
	private final String phoneNumber;

	public UserInfoDto(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getMaskingPassword();
		this.phoneNumber = "010-0000-0000";
	}

	public Map<String, Object> toMap(Map<String, Object> map) {
		map.put("name", name);
		map.put("email", email);
		map.put("password", password);
		map.put("phoneNumber", phoneNumber);
		return map;
	}
}
