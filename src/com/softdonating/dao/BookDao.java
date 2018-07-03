package com.softdonating.dao;

import java.util.List;

import com.softdonating.domain.Books;

public interface BookDao {

	Books findBookByIsbn(String isbn);
	
	boolean bookExist(String isbn);
	
	Books findBookById(Integer bookId);
	
	List<Books> getBookListByPage(Integer page);
	
	Integer numberOfKindOfBook();
	
	boolean updateBook(Books books);
	
	boolean insertBook(Books books);
	
	List<Books> allBooks();
}
