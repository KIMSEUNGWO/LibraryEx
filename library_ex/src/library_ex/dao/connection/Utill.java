package library_ex.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Utill {

	public static Connection getConnection() {
		
		String url = "jdbc:oracle:thin:@localhost:1521:orcl"; 
		String id =  "orauser";
		String pw =  "1234";
		
		Connection conn = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			conn.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConnection(Connection conn, Statement st, ResultSet rs) {
		try {
			if (conn != null && !conn.isClosed()) conn.commit(); conn.close();
			if (st != null && !st.isClosed()) st.close();
			if (rs != null && !rs.isClosed()) rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("리스트를 받아오고 종료하는 중에 오류가 발생했습니다.");
			e.printStackTrace();
		}
	}
		
	public static void closeConnection(Connection conn, Statement st) {
		try {
			if (conn != null && !conn.isClosed()) conn.commit(); conn.close();
			if (st != null && !st.isClosed()) st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("삽입 수정 삭제 후 종료하는 중에 오류가 발생했습니다.");
			e.printStackTrace();
		}
	}
		
}
