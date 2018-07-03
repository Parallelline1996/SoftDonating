package com.softdonating.service;

import java.util.List;

import com.softdonating.domain.Books;
import com.softdonating.request.BookWithNumber;
import com.softdonating.response.BookRecord;
import com.softdonating.response.UnconfirmDonateBook;

public interface BookService {

	Books findBookByIsbn(String isbn) throws Exception;
	
	Integer donateBook(Integer userId, BookWithNumber data);
	
	List<UnconfirmDonateBook> getUnconfirmedDonate(Integer userId);
	
	Integer confirmDonate(List<BookWithNumber> data, Integer userId);
	
	Integer createWithList(Integer userId, String isbn);
	
	Integer deleteWithList(Integer userId, String isbn);
	
	List<Books> getWishList(Integer userId);
	
	List<Books> getBookByPage(Integer page);
	
	Integer numberOfKindOfBook();
	
	Integer takeBook(Integer userId, String isbn);
	
	Integer numberOfDonates(Integer userId);
	
	Integer numberOfTakes(Integer userId);
	
	List<BookRecord> donateList(Integer userId);
	
	List<BookRecord> takeList(Integer userId);
	
	Integer deleteUnconfirmedDonating(Integer donateId);
	
	List<Books> bestBooks();
}
