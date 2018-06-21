package com.softdonating.service;

import java.util.List;

import com.softdonating.domain.Books;
import com.softdonating.request.BookWithNumber;
import com.softdonating.request.UnconfirmDonateBook;

public interface BookService {

	Books findBookByIsbn(String isbn);
	
	Integer donateBook(Integer userId, BookWithNumber data);
	
	List<UnconfirmDonateBook> getUnconfirmedDonate(Integer userId);
	
	Integer confirmDonate(List<Integer> data);
	
	Integer createWithList(Integer userId, String isbn);
	
	Integer deleteWithList(Integer userId, String isbn);
	
	List<Books> getWishList(Integer userId);
	
	List<Books> getBookByPage(Integer page);
	
	Integer numberOfKindOfBook();
	
	Integer takeBook(Integer userId, String isbn);
}
