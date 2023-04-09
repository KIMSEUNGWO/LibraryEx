package library_ex.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import library_ex.dao.connection.Utill;
import library_ex.vo.Book;

public class Database implements Dao{

	@Override
	public List<Book> getList() { // App 실행 시 Library 기본생성자 호출하면서 도서목록을 받아온다.
		
		List<Book> list = new ArrayList<>();
		try {
			Connection conn = Utill.getConnection();
			Statement stmt = conn.createStatement();
			String str = "select * from my_library order by no"; // 쿼리 명령문
			ResultSet rs = stmt.executeQuery(str);
			while (rs.next()) { // next()는 한 줄씩 읽어오고 없다면 false 리턴
				int no = rs.getInt("NO"); // 도서번호
				String title = rs.getString("TITLE"); // 도서명 
				String author = rs.getString("AUTHOR"); // 저자 
				String pub_name = rs.getString("PUB_NAME"); // 출판사
				String isRentStr = rs.getString("ISRENT"); // 대여여부
				boolean isRent = ("Y".equals(isRentStr))?true:false;
				Date retime = rs.getDate("RETIME") ; // 대여일시
				list.add(new Book(no, title, author, pub_name, isRent, retime));
			}
			Utill.closeConnection(conn, stmt, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public int delete(int no) {
		int res = 0;
		
		try {
			Connection conn = Utill.getConnection();
			Statement st = conn.createStatement();
			String str = "delete from my_library where no = " + no;
			res = st.executeUpdate(str);
			if (res >= 1) {
				System.out.println(res + "건이 삭제되었습니다.");
			} else {
				System.err.println("정상적으로 삭제가 되지 않았습니다.");
			}
			Utill.closeConnection(conn, st);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public int insert(int no, String title, String author, String pub_name) {
		int res = 0;
		try {
			Connection conn = Utill.getConnection();
			Statement st = conn.createStatement();
			String str = "INSERT INTO MY_LIBRARY VALUES ("+ no + ", '" + title + "', '" + author + "', '" + pub_name + "', 'N', NULL)";
			res = st.executeUpdate(str);
			System.out.println(res + "건을 생성하였습니다.");
			
			Utill.closeConnection(conn, st);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int update(int no) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean rent(int no, boolean rentSelect) { // 도서대여는 false / 도서반납은 true인 상태
		String str = ""; // 도서반납 명령어
		try {
			Connection conn = Utill.getConnection();
			Statement st = conn.createStatement();
			if (rentSelect) { // 도서반납
				str = "update my_library set isrent = 'N', retime = null where no = " + no;				
			} else { // 도서대여
				str = "update my_library set isrent = 'Y', retime = sysdate where no = " + no;				
			}
			int res = st.executeUpdate(str);
			if (res >= 1) {
				if (rentSelect) { // 도서반납
					System.out.println(res + "건이 반납되었습니다.");
				} else { // 도서대여
					System.out.println(res + "건을 대여했습니다.");
				}
			} else {
				if (rentSelect) { // 도서반납
					System.err.println("반납이 처리되지않았습니다.");
				} else { // 도서대여
					System.err.println("대여업무가 처리되지않았습니다.");
				}
			}
			
			Utill.closeConnection(conn, st);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

}
