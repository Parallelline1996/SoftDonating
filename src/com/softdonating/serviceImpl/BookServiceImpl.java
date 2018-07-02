package com.softdonating.serviceImpl;

 import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
import com.softdonating.response.BookRecord;
import com.softdonating.response.UnconfirmDonateBook;
import com.softdonating.service.BookService;
import com.softdonating.util.Message;

import net.sf.json.JSONObject;

@Service
@Qualifier("bookServiceImpl")
public class BookServiceImpl implements BookService {

	@Override
	public Books findBookByIsbn(String isbn) throws Exception {
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
			// 如果书籍不存在，调用豆瓣开发工具找:
			String temp = "https://api.douban.com/v2/book/isbn/";
			temp = temp + isbn;
			BufferedReader bReader = null;
			StringBuilder stringBuilder = new StringBuilder(); 
			HttpURLConnection connection = null;
			try {
				// 创建URL
				URL url = new URL(temp);
				// 创建链接
				connection = (HttpURLConnection)url.openConnection();
				if (connection == null){
					return null;
				}
				// 进行链接
				connection.connect();
				// 打开流
				bReader = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = bReader.readLine()) != null) {
					stringBuilder.append(line);
				}
				// 关闭流
				bReader.close();
				JSONObject jsonObject = JSONObject.fromObject(stringBuilder.toString());
				String name = jsonObject.get("subtitle").toString();
				String author = jsonObject.get("author").toString();
				String publisher = jsonObject.get("publisher").toString();
				String content = jsonObject.get("summary").toString();
				content = (String) content.subSequence(0, 499);
				JSONObject array = JSONObject.fromObject(jsonObject.get("images").toString());
				// 这里差一个默认图片
				String photo = array.get("small").toString();
				// 照片这里出现问题
				System.out.println(name);
				System.out.println(author);
				System.out.println(publisher);
				System.out.println(content);
				System.out.println(photo);
				books = new Books(0, isbn, name, author, publisher, content, null, 0, 0, null);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} finally {
				// 关闭链接
				connection.disconnect();
			}
		}
		if (bookDao.insertBook(books)){
			Books temp = bookDao.findBookByIsbn(isbn);
			return temp;
		}
		return null;
	}

	

	@Override
	public Integer donateBook(Integer userId, BookWithNumber data) {
		if (userId == null) {
			return 404;
		}
		// 这里暂时改为不维护数目
		int number = 1;//data.getNumber();
		Books books = bookDao.findBookByIsbn(data.getIsbn());
		// 对同一种书的多本书，转成多条记录进行保存
		for (int i = 0; i < number; i++) {
			Date donateTime = new Date();
			Donate donate = new Donate(null, userId, books.getBookId(), -1, donateTime);
			if (donateDao.createDonate(donate) == false) {
				return -1;
			}
		}
		return 200;
	}
	
	@Override
	public List<UnconfirmDonateBook> getUnconfirmedDonate(Integer userId) {
		if (userId == null) {
			return null;
		}
		List<Donate> donates = donateDao.getUnconfirmedList(userId);
		List<UnconfirmDonateBook> books = new ArrayList<>();
		for (Donate donate : donates) {
			Date date = new Date(new Date().getTime() - 3 * 86400000);
			// 如果捐赠时间超过3天，被自动清除
			if (date.after(donate.getDonateTime())) {
				donateDao.deleteDonate(donate.getDonateId());
				continue;
			} else {
				Books temp = bookDao.findBookById(donate.getBookId());
				UnconfirmDonateBook book = new UnconfirmDonateBook(temp.getBookId(), temp.getIsbn(), 
						temp.getName(), temp.getAuthor(), temp.getPublisher(), temp.getContent(), 
						temp.getPhoto(), donate.getDonateId(), donate.getDonateTime());
				books.add(book);
			}
		}
		return books;
	}

	@Override
	public Integer confirmDonate(List<BookWithNumber> data, Integer userId) {
		if (data == null || userId == null) {
			// 没有可操作的数据
			return 404;
		}
		for (BookWithNumber bookWithNumber : data) {
			Books books = bookDao.findBookByIsbn(bookWithNumber.getIsbn());
			// 找到donate
			Donate donate = donateDao.findDonate(userId, books.getBookId());
			// 将原来记录的状态进行修改
			if (!donateDao.confirmDonate(donate.getDonateId(), bookWithNumber.getNumber())) {
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
			books.setFollowNumber(books.getFollowNumber() + 1);
			if(bookDao.updateBook(books)) {
				return 200;				
			} else {
				userDao.deleteWishList(userId, books);
				return -1;
			}
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
			books.setFollowNumber(books.getFollowNumber() - 1);
			if (bookDao.updateBook(books)) {
				return 200;				
			} else {
				userDao.deleteWishList(userId, books);
				return -1;
			}
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
			Books temp = bookDao.findBookById(donate.getBookId());
			User tempUser = userDao.findUserById(donate.getUserId());
			Message.sendMs(tempUser.getPhoneNumber(), temp.getName());
			return 200;
		}
		return -2;
	}

	@Override
	public Integer numberOfDonates(Integer userId) {
		if (userId == null) {
			return -1;
		}
		return takeDao.numberOfDonate(userId);
	}

	@Override
	public Integer numberOfTakes(Integer userId) {
		if (userId == null) {
			return -1;
		}
		return takeDao.numberOfTake(userId);
	}

	@Override
	public List<BookRecord> donateList(Integer userId) {
		if (userId == null) {
			return null;
		}
		List<BookRecord> donateList = new ArrayList<>();
		List<Take> takes = takeDao.donateList(userId);
		List<Donate> donates = donateDao.donateList(userId);
		for (Donate donate : donates) {
			Books books = bookDao.findBookById(donate.getBookId());
			donateList.add(new BookRecord(books.getBookId(), books.getIsbn(), 
					books.getName(), books.getAuthor(), books.getPublisher(), 
					books.getPhoto(), donate.getDonateTime()));
		}
		for (Take take : takes) {
			Books books = bookDao.findBookById(take.getBookId());
			donateList.add(new BookRecord(books.getBookId(), books.getIsbn(), 
					books.getName(), books.getAuthor(), books.getPublisher(), 
					books.getPhoto(), take.getDonateTime()));
		}
		return donateList;
	}

	@Override
	public List<BookRecord> takeList(Integer userId) {
		if (userId == null) {
			return null;
		}
		List<BookRecord> takeList = new ArrayList<>();
		List<Take> takes = takeDao.takeList(userId);
		for (Take take : takes) {
			Books books = bookDao.findBookById(take.getBookId());
			takeList.add(new BookRecord(books.getBookId(), books.getIsbn(), 
					books.getName(), books.getAuthor(), books.getPublisher(), 
					books.getPhoto(), take.getTakeTime()));
		}
		return takeList;
	}

	@Override
	public Integer deleteUnconfirmedDonating(Integer donateId) {
		Donate donate = donateDao.findDonateById(donateId);
		if (donate.getStatus() != -1) {
			return -1;
		} 
		if (donateDao.deleteDonate(donateId)){
			return 200;
		} else {
			return -1;
		}
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
}
