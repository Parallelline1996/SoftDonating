package com.softdonating.serviceImpl;

 import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.softdonating.dao.BookDao;
import com.softdonating.dao.DonateDao;
import com.softdonating.dao.TakeDao;
import com.softdonating.dao.UserDao;
import com.softdonating.domain.Books;
import com.softdonating.domain.Donate;
import com.softdonating.domain.Take;
import com.softdonating.domain.User;
import com.softdonating.request.BookWithNumber;
import com.softdonating.request.UnconfirmDonateBook;
import com.softdonating.service.BookService;

@Service
@Qualifier("bookServiceImpl")
public class BookServiceImpl implements BookService {

	@Override
	public Books findBookByIsbn(String isbn) {
		// 检查输入是否合法
		if (isbn == null) {
			return null;
		}
		Books books = bookDao.findBookByIsbn(isbn);
		// 判断书籍是否存在，存在则直接调用查看数据库的数据
		if (books != null) {
			books.setUsers(null);
			return books;
		} else {
			
			// 这里待完成，
			return null;
		}
	}

	@Override
	public Integer donateBook(Integer userId, BookWithNumber data) {
		if (userId == null) {
			return 404;
		}
		int number = data.getNumber();
		for (int i = 0; i < number; i++) {
			Date donateTime = new Date();
			Donate donate = new Donate(null, userId, data.getBookId(), -1, donateTime);
			if (donateDao.createDonate(donate) == false) {
				return -1;
			}
		}
		return 200;
	}
	
	@Autowired
	@Qualifier("bookDaoImpl")
	private BookDao bookDao;
	
	@Autowired
	@Qualifier("donateDaoImpl")
	private DonateDao donateDao;
	
	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDao;
	
	@Autowired
	@Qualifier("takeDaoImpl")
	private TakeDao takeDao;

	@Override
	public List<UnconfirmDonateBook> getUnconfirmedDonate(Integer userId) {
		if (userId == null) {
			return null;
		}
		List<Donate> donates = donateDao.getUnconfirmedList(userId);
		List<UnconfirmDonateBook> books = new ArrayList<>();
		for (Donate donate : donates) {
			Books temp = bookDao.findBookById(donate.getBookId());
			UnconfirmDonateBook book = new UnconfirmDonateBook(temp.getBookId(), temp.getIsbn(), 
					temp.getName(), temp.getAuthor(), temp.getPublisher(), temp.getContent(), 
					temp.getPhoto(), donate.getDonateId(), donate.getDonateTime());
			books.add(book);
		}
		return books;
	}

	@Override
	public Integer confirmDonate(List<Integer> data) {
		if (data == null) {
			// 没有可操作的数据
			return 404;
		}
		for (Integer integer : data) {
			if (!donateDao.confirmDonate(integer)) {
				return -1;
			}
		}
		return 200;
	}

	@Override
	public Integer createWithList(Integer userId, String isbn) {
		if (userId == null || isbn == null) {
			return 404;
		}
		Books books = bookDao.findBookByIsbn(isbn);
		if (userDao.addWishList(userId, books)) {
			return 200;
		} else {
			return -1;
		}
	}

	@Override
	public Integer deleteWithList(Integer userId, String isbn) {
		if (userId == null || isbn == null) {
			return 404;
		}
		Books books = bookDao.findBookByIsbn(isbn);
		if (userDao.deleteWishList(userId, books)) {
			return 200;
		} else {
			return -1;
		}
	}

	@Override
	public List<Books> getWishList(Integer userId) {
		if (userId == null) {
			return null;
		}
		User user = userDao.findUserById(userId);
		Set<Books> books = user.getBooks();
		List<Books> wishList = new ArrayList<>();
		for (Books i : books) {
			i.setUsers(null);
			wishList.add(i);
		}
		return wishList;
	}

	@Override
	public List<Books> getBookByPage(Integer page) {
		if (page == null) {
			return null;
		}
		return bookDao.getBookListByPage(page);
	}

	@Override
	public Integer numberOfKindOfBook() {
		return bookDao.numberOfKindOfBook();
	}

	@Override
	public Integer takeBook(Integer userId, String isbn) {
		if (userId == null || isbn == null) {
			return 404;
		}
		Books books = bookDao.findBookByIsbn(isbn);
		if (books.getNumber() < 1) {
			// 库存不足
			return -1;
		}
		Donate donate = donateDao.findFirstDonate(isbn);
		Date takeTime = new Date();
		if (donate == null) {
			return -1;
		}
		Take take = new Take(0, userId, donate.getUserId(), 
				donate.getBookId(), donate.getDonateTime(), takeTime);
		if (takeDao.createTake(take, donate.getDonateId())) {
			return 200;
		}
		return -2;
	}
}
