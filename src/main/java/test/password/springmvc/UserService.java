package test.password.springmvc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory
			.getLogger(UserService.class);
	private JdbcTemplate jdbcTemplate;
	private final static UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<User> getAllUser() {
		return this.jdbcTemplate.query("select * from User",
				UserService.USER_ROW_MAPPER);
	}

	public void createUser(User user) {
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		this.jdbcTemplate.update(
				"INSERT INTO User (username,password) VALUES (?,?)",
				user.getUsername(), user.getPassword());
		logger.info("New username : " + user.getUsername() + "\tpassword : "
				+ user.getPassword());
		user.setId(this.jdbcTemplate.queryForInt(
				"select id from User where username = ?", user.getUsername()));
	}

	public User checkUser(User loginUser) {
		String hashed = (String) this.jdbcTemplate.queryForObject(
				"select password from User where username = ?", String.class,
				loginUser.getUsername());
		if (BCrypt.checkpw(loginUser.getPassword(), hashed)) {
			loginUser.setPassword(hashed);
			return loginUser;
		}
		return null;
	}

	public boolean findUserByUsername(User user) {
		int count = this.jdbcTemplate.queryForInt(
				"select count(*) from User where username = ?",
				user.getUsername());
		return count == 1;
	}

	private static class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet resultSet, int line) throws SQLException {
			User user = new User();
			user.setId(resultSet.getInt("id"));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			return user;
		}
	}
}
