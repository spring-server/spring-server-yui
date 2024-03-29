package project.server.spring.app.core.repository.user;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.app.core.domain.user.User;
import project.server.spring.framework.annotation.Repository;
import project.server.spring.framework.jdbc.JdbcTemplate;
import project.server.spring.framework.jdbc.RowMapper;

@Repository
public class JdbcUserRepository implements UserRepository {
	private final Logger log = LoggerFactory.getLogger(JdbcUserRepository.class);
	private final JdbcTemplate jdbcTemplate;

	public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public User save(User user) {
		if (user.isNew()) {
			return create(user);
		}
		return update(user);
	}

	private User create(User user) {
		Long id = jdbcTemplate.create(connection -> {
			PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO user (name, email, phoneNumber, password) VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPhoneNumber());
			statement.setString(4, user.getPassword());
			return statement;
		});
		user.initializeId(id);
		//TODO: user Id 할당
		return user;
	}

	private User update(User user) {
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(
				"UPDATE user SET name = ?, email = ?, phoneNumber = ?, password = ? WHERE id = ?");
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPhoneNumber());
			statement.setString(4, user.getPassword());
			statement.setLong(5, user.getId());
			return statement;
		});
		return user;
	}

	@Override
	public void delete(User user) {
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
			statement.setLong(1, user.getId());
			return statement;
		});
	}

	@Override
	public Optional<User> findById(Long id) {
		String sql = "SELECT * FROM user WHERE id = ?";
		RowMapper<User> rowMapper = getUserRowMapper();
		User user = jdbcTemplate.queryForObject(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			return statement;
		}, new Object[] {id}, rowMapper);
		return Optional.ofNullable(user);
	}

	private RowMapper<User> getUserRowMapper() {
		return (resultSet, rowNum) -> {
			if (resultSet.next()) {
				Long userId = resultSet.getLong("id");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				String password = resultSet.getString("password");
				String phoneNumber = resultSet.getString("phoneNumber");
				return new User(userId, name, password, email, phoneNumber);
			}
			return null;
		};
	}

	@Override
	public Optional<User> findByEmail(String email) {
		String sql = "SELECT * FROM user WHERE email = ?";
		RowMapper<User> rowMapper = getUserRowMapper();
		User user = jdbcTemplate.queryForObject(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			return statement;
		}, new Object[] {email}, rowMapper);
		return Optional.ofNullable(user);
	}

	@Override
	public boolean existsById(Long id) {
		return findById(id).isPresent();
	}

	@Override
	public boolean existsByEmail(String email) {
		return findByEmail(email).isPresent();
	}
}
