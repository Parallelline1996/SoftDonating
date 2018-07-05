package com.softdonating.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.softdonating.dao.BookDao;
import com.softdonating.dao.UserDao;
import com.softdonating.domain.Books;
import com.softdonating.domain.User;
import com.softdonating.response.BookListData;
import com.softdonating.service.NormalService;

@Service
@Qualifier("normalServiceImpl")
public class NormalServiceImpl implements NormalService {

	@Override
	public List<BookListData> sortBooks(String bookName, Integer userId) {
		if (bookName == null || userId == null) {
			return null;
		}
		List<Books> books = bookDao.sortBooks(bookName);
		if (books == null){
			return null;
		}
		List<BookListData> datas = new ArrayList<>();
		User user = userDao.findUserById(userId);
		Set<Books> book = user.getBooks();
		for (Books b : books) {
			boolean weatherLikeThisBook = book.contains(b);
			BookListData temp = new BookListData(b, weatherLikeThisBook);
			datas.add(temp);
		}
		return datas;
	}

	@Override
	public List<String> complete(String bookName) {
		return null;
	}
	
	@Autowired
	@Qualifier("bookDaoImpl")
	private BookDao bookDao;
	
	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDao;

}
