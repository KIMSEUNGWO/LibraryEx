package library_ex.vo;

import java.util.Date;

public class Book {

	private int no; // 도서번호
	private String title; // 도서명
	private String author; // 저자
	private String pub_name; // 출판사
	private boolean isRent; // 대여여부
	private Date reDate; // 대여일
	
	public Book(int no, String title, String author, String pub_name, boolean isRent, Date reDate) {
		super();
		this.no = no;
		this.title = title;
		this.author = author;
		this.pub_name = pub_name;
		this.isRent = isRent;
		this.reDate = reDate;
	}
	
	@Override
	public String toString() {
		return getNo() +" | " + getTitle() + " | " + getAuthor() + " | " + getPub_name() + " | " + isRent() + " | " + getReDate();
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPub_name() {
		return pub_name;
	}

	public void setPub_name(String pub_name) {
		this.pub_name = pub_name;
	}

	public boolean isRent() {
		return isRent;
	}

	public void setRent(boolean isRent) {
		this.isRent = isRent;
	}

	public Date getReDate() {
		return reDate;
	}

	public void setReDate(Date reDate) {
		this.reDate = reDate;
	}
	

	
}
