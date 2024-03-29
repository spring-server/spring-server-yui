package project.server.spring.framework.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.annotation.Component;
import project.server.spring.framework.exception.DataAccessException;

@Component
public class JdbcTemplate {
	private final Logger log = LoggerFactory.getLogger(JdbcTemplate.class);
	private final DriverManager driverManager;

	public JdbcTemplate(DriverManager driverManager) {
		this.driverManager = driverManager;
	}

	public void update(StatementStrategy strategy) {
		try {
			Connection connection = driverManager.getConnection();
			PreparedStatement preparedStatement = strategy.makePreparedStatement(connection);
			preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			throw new DataAccessException("sql exception occurs", exception);
		}
	}

	public Long create(StatementStrategy strategy) {
		try {
			Connection connection = driverManager.getConnection();
			PreparedStatement preparedStatement = strategy.makePreparedStatement(connection);
			int rowNumber = preparedStatement.executeUpdate();
			ResultSet keys = preparedStatement.getGeneratedKeys();
			if (keys.next()) {
				return keys.getLong(1);
			}
			return null;
		} catch (SQLException exception) {
			throw new DataAccessException("sql exception occurs", exception);
		}
	}

	public <T> T queryForObject(StatementStrategy strategy, @Nullable Object[] args, RowMapper<T> rowMapper) {
		try {
			Connection connection = driverManager.getConnection();
			PreparedStatement statement = strategy.makePreparedStatement(connection);
			ResultSet resultSet = statement.executeQuery();
			return rowMapper.mapRow(resultSet, 1);
		} catch (SQLException exception) {
			throw new DataAccessException("sql exception occurs", exception);
		}
	}
}
