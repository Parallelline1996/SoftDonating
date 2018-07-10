package com.softdonating.service;

import java.util.List;

import com.softdonating.domain.Books;
import com.softdonating.request.BookWithNumber;
import com.softdonating.response.BookDetail;
import com.softdonating.response.BookDetailWithLike;
import com.softdonating.response.BookListData;
import com.softdonating.response.BookRecord;
import com.softdonating.response.UnconfirmDonateBook;

public interface BookService {

	BookDetail findBookByIsbn(String isbn, Integer userId) throws Exception;
	
	Integer donateBook(Integer userId, Integer bookId);
	
	List<UnconfirmDonateBook> getUnconfirmedDonate(Integer userId);
	
	Integer confirmDonate(List<BookWithNumber> data, Integer userId);
	
	Integer createWithList(Integer userId, Integer bookId);
	
	Integer deleteWithList(Integer userId, Integer bookId);
	
	List<BookListData> getWishList(Integer userId);
	
	List<Books> getBookByPage(Integer page);
	
	Integer numberOfKindOfBook();
	
	Integer takeBook(Integer userId, Integer bookId);
	
	Integer numberOfDonates(Integer userId);
	
	Integer numberOfTakes(Integer userId);
	
	List<BookRecord> donateList(Integer userId);
	
	List<BookRecord> takeList(Integer userId);
	
	Integer deleteUnconfirmedDonating(Integer donateId, Integer userId);
	
	List<BookListData> bestBooks(Integer userId);
	
	BookDetailWithLike findBookByBookId(Integer userId, Integer bookId);
}
