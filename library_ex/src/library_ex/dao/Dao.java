package library_ex.dao;

import java.util.List;

import library_ex.vo.Book;

public interface Dao {

	List<Book> getList();
	int delete(int no);
	int insert(int no, String title, String author, String pub_name);
	int update(int no);
	boolean rent(int no, boolean rentSelect);
	
	
}
