package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnUtil {

	private Connection conn;
	
	public String urlstrSql = "jdbc:sqlserver://localhost:1433;databaseName=EmploymentServGrant;user=watcher;password=1234;encrypt=true;trustServerCertificate=true";
	public String urlstrMysql = "jdbc:mysql://localhost:3306/EmploymentServGrant?user=watcher&password=1234&serverTimezone=UTC";

	public Connection createConn() throws Exception {// sql server
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		conn = DriverManager.getConnection(urlstrSql);
		System.out.println("Connection Status:" + !conn.isClosed());
		return conn;
	}
	
	public Connection createConn2() throws Exception {// mysql
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(urlstrMysql);
		System.out.println("Connection Status2:" + !conn.isClosed());
		return conn;
	}

	public void closeConn() throws SQLException {// close connection
		if (conn != null) {
			conn.close();
		}
		System.out.println("Connection Closed.");
	}

}
