package library_ex;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import library_ex.dao.Dao;
import library_ex.dao.Database;
import library_ex.vo.Book;

public class Library {

	private String adminId = "1"; // 관리자 ID
	private String adminPw = "1"; // 관리자 PW
	
	List<Book> list = new ArrayList<>();
	Dao dao = new Database();
	
	public Library() {
		list = dao.getList();	// 데이터베이스 내에 도서목록을 가져옵니다.
	}
	public Library(String type) { // App 실행시 선언과 함께 도서목록 불러오기 기능
		list = dao.getList();
		System.out.println(toString());
	}
	
	@Override
	public String toString() { // Library(생성자) - toString 책 목록 불러오기
		System.out.println("[책의 정보를 가져옵니다]");
		String info = "";
		for (Book a : list) {
			info += a.toString() + "\n";
		}
		return info;
	}
	
	public boolean insertBook (int no, String title, String author, String pub_name) { // App - add메소드
		for (Book a : list) {	// 일련번호 중복확인
			if (a.getNo() == no) {
				System.out.println("일련번호가 중복되었습니다.");
				return false;
			}
		}
		list.add(new Book(no, title, author, pub_name, false, null)); // 리스트에 추가
		int i = dao.insert(no, title, author, pub_name); // 데이터베이스로 넘긴다.
		return true;
	}
	
	public boolean deleteBook (int no) {
		for (Book a : list) {	// 일련번호 확인
			if (a.getNo() == no) {
				int res = dao.delete(no);
				list.remove(a);
				return true;
			}
		}
		System.out.println("존재하지 않는 일련번호입니다.");
		return false;
	}
	
	public boolean rentBook (int no, boolean rentSelect) { // 도서대여는 false / 도서반납은 true인 상태
		for (Book a : list) {
			if (a.getNo() == no) { // 일련번호 확인
				if (rentSelect) { // 도서반납일때
					if (a.isRent() == false) { // 대여중이 아니라면
						System.out.println(a.getNo() + "번의 도서는 대여중이 아닙니다.");
						return false;
					}		
				} else { //도서대여일때
					if (a.isRent() == true) { // 이미 대여중이라면
						System.out.println(a.getNo() + "번의 도서는 이미 대여중입니다.");
						return false;
					}
				}
				dao.rent(no, rentSelect);
				// 리스트에 반영
				if (rentSelect) { // 도서반납이 완료되면
					a.setRent(false);
				} else { // 도서대여가 완료되면
					a.setRent(true);
				}
				return true;
			}
		}
		System.out.println("존재하지 않는 일련번호입니다.");
		return false;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminPw() {
		return adminPw;
	}

	public void setAdminPw(String adminPw) {
		this.adminPw = adminPw;
	}
	
	
}
