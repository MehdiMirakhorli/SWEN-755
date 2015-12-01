package edu.rit.swen755.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Super class for model classes
 * 
 * @author Joanna
 */
public abstract class Model {
	private static final String path = "C:\\apache-tomcat\\webapps\\Assignment4";
	private static final String CONNECTION_URL = "jdbc:sqlite:" + path
			+ "\\assignment4.db";
	protected static Connection connection;

	//estabilish db connection
	static {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(CONNECTION_URL);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(-1);
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(-1);
		}
	}

}
