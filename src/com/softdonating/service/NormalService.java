package com.softdonating.service;

import java.util.List;

import com.softdonating.response.BookListData;

public interface NormalService {

	List<BookListData> sortBooks(String bookName, Integer userId);
	
	List<String> complete(String bookName);
}
