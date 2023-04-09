package library_ex;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

	static Scanner scan = new Scanner(System.in);
	static Library lib = new Library();
	
	public static void main(String[] args) {
		Library lib = new Library("DB"); // 선언과 함께 도서 목록 불러오기
	
		System.out.println("─────────────────────────────────────────────");
		
		
		Label:while (true) { // 전체메뉴 반복문
			System.out.println("[1.일반회원] [2.관리자모드] [3.프로그램 종료]");
			System.out.print("선택> ");
			switch (intSelect()) {
			case 1:	// 1. 일반회원
				System.out.println("[일반회원]");
				Label2:while (true) { // 일반회원 반복문
					System.out.println("[1.도서대여] [2.도서반납] [3.뒤로가기]");
					switch (intSelect()) {
					case 1: // 1-1. 도서대여
						System.out.println("[도서대여]");
						rent(false);
						lib.list = lib.dao.getList();	// 업데이트된 도서목록을 리스트에 담습니다.
						System.out.println(lib.toString()); // 도서목록을 출력합니다
						continue Label2;
					case 2: // 1-2. 도서반납
						System.out.println("[도서반납]");
						rent(true);
						lib.list = lib.dao.getList();	// 업데이트된 도서목록을 리스트에 담습니다.
						System.out.println(lib.toString()); // 도서목록을 출력합니다
						continue Label2;
					case 3: // 1-3. 뒤로가기
						continue Label; 
					default:
						System.out.println("올바른 번호를 입력해주세요.");
						continue Label2;
					}
				}
			case 2: // 2. 관리자모드
				System.out.println("[관리자모드]");
				if (login()) { // 로그인 성공시 메뉴로 진입
					Label3:while (true) { // 관리자모드 반복문
						System.out.println("[1.도서추가] [2.도서삭제] [3.뒤로가기]");
						switch (intSelect()) {
						case 1: // 2-1. 도서추가
							System.out.println("[도서추가]");
							add();
							lib.list = lib.dao.getList();	// 업데이트된 도서목록을 리스트에 담습니다.
							System.out.println(lib.toString()); // 도서목록을 출력합니다
							break;
						case 2: // 2-2. 도서삭제
							System.out.println("[도서삭제]");
							if (!del()) continue Label3;
							lib.list = lib.dao.getList();	// 업데이트된 도서목록을 리스트에 담습니다.
							System.out.println(lib.toString()); // 도서목록을 출력합니다
							break;
						case 3: // 2-3. 뒤로가기
							continue Label; 
						default:
							System.out.println("올바른 번호를 입력해주세요.");
							continue Label3;
						}
					}
				} else { // 로그인 실패시 최상위 메뉴로 리턴
					continue Label;
				}
			case 3: // 3. 프로그램 종료
				System.out.println("프로그램을 종료합니다");
				System.exit(0);
			default :
				System.out.println("올바른 번호를 입력해주세요.");
				continue;			
			}
		}

	}
	
	public static void rent(boolean rentSelect) { // 도서대여는 false / 도서반납은 true인 상태
		System.out.print("[일련번호] : ");
		int no = scan.nextInt();
		scan.nextLine(); // 잔여 엔터 제거
	
		lib.rentBook(no, rentSelect);
	}

	public static boolean del() { // 도서삭제
		System.out.print("[일련번호] : ");
		int no = scan.nextInt();
		scan.nextLine(); // 잔여 엔터 제거
		if (lib.deleteBook(no)) return true; // 일련번호가 중복되면 false로 리턴되어 Label3인 관리자 모드메뉴로 돌아간다.
		else return false;
	}
	
	public static void add() { // 도서추가
		System.out.print("[일련번호] : ");
		int no = scan.nextInt();
		
		System.out.print("[도서명] : ");
		scan.nextLine(); // 잔여 엔터 제거
		String title = scan.nextLine();
//		scan.nextLine(); // 잔여 엔터 제거
		
		System.out.print("[저자] : ");
		String author = scan.nextLine();
//		scan.nextLine(); // 잔여 엔터 제거
		
		System.out.print("[출판사] : ");
		String pub_name = scan.nextLine();
//		scan.nextLine(); // 잔여 엔터 제거
		
		lib.insertBook(no, title, author, pub_name);
	}
	
	
	/**
	 * 로그인 성공여부 boolean 타입으로 리턴
	 * 관리자 ID,PW는 Library 필드에서 설정
	 * @return 로그인성공시 true / 로그인실패시 false
	 */
	public static boolean login() {
		
		System.out.print("[아이디] : ");
		String id = scan.next();
		System.out.print("[비밀번호] : ");
		String pw = scan.next();
		
		if (lib.getAdminId().equalsIgnoreCase(id)) {
			if (lib.getAdminPw().equalsIgnoreCase(pw)) {
				System.out.println("[로그인 성공]");
				return true;
			} else {
				System.out.println("알맞은 비밀번호가 아닙니다.");
				return false;
			}
		} 
		System.out.println("알맞은 아이디가 아닙니다.");
		return false;
	}
	
	
	/**
	 * 메뉴선택시 번호에 대한 예외처리
	 * @return 숫자만 리턴
	 */
	public static int intSelect() {
		while (true) {
			try {
				int select = scan.nextInt();
				return select;
			} catch (InputMismatchException e) {
				scan.next();
				System.out.println("숫자만 입력해주세요.");
			} catch (Exception e) {
				scan.next(); // 
				// TODO: handle exception
			}			
		}
	}
}
