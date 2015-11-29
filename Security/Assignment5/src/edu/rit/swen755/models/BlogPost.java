package edu.rit.swen755.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlogPost extends Model {
	private static final String TABLE_NAME = "blog_post";
	private Integer id;
	private Integer creator_id;
	private String title;
	private String content;

	public BlogPost(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.creator_id = rs.getInt("creator_id");
		this.title = rs.getString("title");
		this.content = rs.getString("content");
	}

	public BlogPost(Integer id, Integer creator_id, String title, String content) {
		super();
		this.id = id;
		this.creator_id = creator_id;
		this.title = title;
		this.content = content;
	}

	public BlogPost(Integer creator_id, String title, String content) {
		super();
		this.creator_id = creator_id;
		this.title = title;
		this.content = content;
	}

	
	public int save() throws SQLException {
		String insertTableSQL = "INSERT INTO " + TABLE_NAME
				+ "(creator_id, title, content) VALUES (?,?,?)";
		PreparedStatement preparedStatement = connection
				.prepareStatement(insertTableSQL);
		preparedStatement.setInt(1, creator_id);
		preparedStatement.setString(2, title);
		preparedStatement.setString(3, content);

		// execute insert SQL stetement
		int affectedRows = preparedStatement.executeUpdate();
		preparedStatement.close();
		return affectedRows;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreatorId() {
		return creator_id;
	}

	public void setCreatorId(Integer creator_id) {
		this.creator_id = creator_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static List<BlogPost> getAll() throws SQLException {

		List<BlogPost> queue = new ArrayList<BlogPost>();
		String sql = String.format("SELECT * FROM %s ORDER BY id", TABLE_NAME);
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			BlogPost post = new BlogPost(rs);
			queue.add(post);
		}
		rs.close();

		return queue;
	}

	public static BlogPost get(String id) throws SQLException {

		BlogPost post = null;
		String sql = String.format("SELECT * FROM %s WHERE id = ?", TABLE_NAME);
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			post = new BlogPost(rs);
		}
		rs.close();

		return post;
	}
}
