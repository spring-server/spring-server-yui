package project.server.spring.framework.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
	T mapRow(ResultSet rs, int rowNum) throws SQLException;

}
