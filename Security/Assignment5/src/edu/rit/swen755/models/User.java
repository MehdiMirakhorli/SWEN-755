package edu.rit.swen755.models;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User extends Model {
	private static final String TABLE_NAME = "user";

	private static final int ROLE_ADMIN = 1;
	private static final int ROLE_READER = 2;

	private Integer id;
	private Integer role;
	private String username;

	public User(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.role = rs.getInt("role");
		this.username = rs.getString("username");

	}

	public static String getTableName() {
		return TABLE_NAME;
	}

	public static int getRoleAdmin() {
		return ROLE_ADMIN;
	}

	public static int getRoleReader() {
		return ROLE_READER;
	}

	public Integer getId() {
		return id;
	}

	public Integer getRole() {
		return role;
	}

	public String getUsername() {
		return username;
	}

	public static User get(String username) throws SQLException {

		User user = null;
		String sql = String.format(
				"SELECT id,role,username FROM %s WHERE username = ?",
				TABLE_NAME);
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			user = new User(rs);
		}
		rs.close();

		return user;
	}

	public boolean isAdmin() {
		return role != null && role.equals(ROLE_ADMIN);
	}

	public boolean isReader() {
		return role != null && role.equals(ROLE_READER);
	}

	public static String md5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}
	/**
	 * Verifies whether a combination username/password is valid
	 * @param username account username
	 * @param password account password
	 * @return true if the combination username/password is valid
	 * @throws NoSuchAlgorithmException password could not be hashed using md5
	 * @throws SQLException error when executing query in the DB
	 */
	public static boolean isLoginValid(String username, String password)
			throws NoSuchAlgorithmException, SQLException {

		

		boolean isValid = false;
		String sql = String.format(
				"SELECT * FROM %s WHERE username = ? AND password = ?",
				TABLE_NAME);
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, md5(password));
		ResultSet rs = ps.executeQuery();

		isValid = rs.next();

		rs.close();
		return isValid;
	}
}
