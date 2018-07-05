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
import com.softdonating.response.BookDetail;
import com.softdonating.response.BookListData;
import com.softdonating.response.BookRecord;
import com.softdonating.response.UnconfirmDonateBook;
import com.softdonating.service.BookService;
import com.softdonating.util.Message;

import net.sf.json.JSONObject;

@Service
@Qualifier("bookServiceImpl")
public class BookServiceImpl implements BookService {

	@Override
	public BookDetail findBookByIsbn(String isbn, Integer userId) throws Exception {
		// 检查输入是否合法
		if (isbn == null || userId == null) {
			return null;
		}
		Books books = bookDao.findBookByIsbn(isbn);
		// 判断书籍是否存在，存在则直接调用查看数据库的数据
		if (books != null) {
			Set<User> users = books.getUsers();
			User user = userDao.findUserById(userId);
			boolean weatherLikeThisBook = users.contains(user);
			BookDetail bookDetail = new BookDetail(books.getBookId(), books.getIsbn(), 
					books.getName(), books.getAuthor(), books.getPublisher(), 
					books.getContent(), books.getPhoto(), books.getNumber(), weatherLikeThisBook);
			return bookDetail;
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
				if (content.length() >= 500) {
					content = (String) content.subSequence(0, 499);					
				}
				JSONObject array = JSONObject.fromObject(jsonObject.get("images").toString());
				// 这里差一个默认图片
				String photo = array.get("small").toString();
				// 照片这里出现问题
				System.out.println(name);
				System.out.println(author);
				System.out.println(publisher);
				System.out.println(content);
				System.out.println(photo);
				books = new Books(0, isbn, name, author, publisher, content, photo, 0, 0, null);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} finally {
				// 关闭链接
				connection.disconnect();
			}
		}
		if (bookDao.insertBook(books)){
			Books temp = bookDao.findBookByIsbn(isbn);
			BookDetail bookDetail = new BookDetail(temp.getBookId(), temp.getIsbn(), 
					temp.getName(), temp.getAuthor(), temp.getPublisher(), temp.getContent(), 
					temp.getPhoto(), temp.getNumber(), false);
			return bookDetail;
		}
		return null;
	}

	@Override
	public Integer donateBook(Integer userId, Integer bookId) {
		if (bookId == null) {
			return 404;
		}
		Books books = bookDao.findBookById(bookId);
		if (books == null) {
			return 404;
		}
		// 创建捐赠信息
		Date donateTime = new Date();
		Donate donate = new Donate(null, userId, bookId, -1, donateTime);
		if (donateDao.createDonate(donate) == false) {
				return -1;
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
						temp.getName(), temp.getAuthor(), temp.getPublisher(), 
						temp.getPhoto(), donate.getDonateId(), donate.getDonateTime());
				books.add(book);
			}
		}
		return books;
	}

	@Override
	public Integer confirmDonate(List<BookWithNumber> data, Integer userId) {
		if (data == null) {
			// 没有可操作的数据
			return 404;
		}
		for (BookWithNumber bookWithNumber : data) {
			Donate donate = donateDao.findDonateById(bookWithNumber.getDonateId());
			if (donate == null | donate.getUserId() != userId){
				return 404;
			}
			// 将原来记录的状态进行修改
			if (!donateDao.confirmDonate(bookWithNumber.getDonateId(), bookWithNumber.getNumber())) {
				return -1;
			}
		}
		return 200;
	}

	@Override
	public Integer createWithList(Integer userId, Integer bookId) {
		if (userId == null || bookId == null) {
			return 404;
		}
		Books books = bookDao.findBookById(bookId);
		if (books == null) {
			return -1;
		}
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
	public Integer deleteWithList(Integer userId, Integer bookId) {
		if (userId == null || bookId == null) {
			return 404;
		}
		Books books = bookDao.findBookById(bookId);
		if (books == null) {
			return -1;
		}
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
	public List<BookListData> getWishList(Integer userId) {
		if (userId == null) {
			return null;
		}
		User user = userDao.findUserById(userId);
		Set<Books> books = user.getBooks();
		List<BookListData> bookListData = new ArrayList<>();
		for (Books i : books) {
			User userTemp = userDao.findUserById(userId);
			boolean weatherLikeThisBook = i.getUsers().contains(userTemp);
			BookListData temp = new BookListData(i, weatherLikeThisBook);
			bookListData.add(temp);
		}
		return bookListData;
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
	public Integer takeBook(Integer userId, Integer bookId) {
		if (userId == null || bookId == null) {
			return 404;
		}
		User user = userDao.findUserById(userId);
		if (user == null) {
			return 404;
		}
		Books books = bookDao.findBookById(bookId);
		if (books == null | books.getNumber() < 1) {
			// 库存不足
			return -2;
		}
		Donate donate = donateDao.findFirstDonate(books.getIsbn());
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
			Set<Books> book = user.getBooks();
			if (book.contains(books)){
				book.remove(books);
				user.setBooks(book);
				userDao.updateUser(user);
				books.setFollowNumber(books.getFollowNumber() - 1);
			}
			books.setNumber(books.getNumber() - 1);
			bookDao.updateBook(books);
			return 200;
		}
		return -1;
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
	public Integer deleteUnconfirmedDonating(Integer donateId, Integer userId) {
		Donate donate = donateDao.findDonateById(donateId);
		if (donate.getStatus() != -1) {
			return -1;
		} 
		if (donate.getUserId() != userId) {
			return 404;
		}
		if (donateDao.deleteDonate(donateId)){
			return 200;
		} else {
			return -1;
		}
	}
	
	@Override
	public List<BookListData> bestBooks(Integer userId) {
		if (userId == null) {
			return null;
		}
		User user = userDao.findUserById(userId);
		if (user == null) {
			return null;
		}
		List<BookListData> bookList = new ArrayList<>();
		List<Books> books = bookDao.allBooks();		
		int booksNumber = bookDao.numberOfKindOfBook();
		// 如果图书数目不够10本，则推荐所有图书
		if (booksNumber <= 10){
			BookListData temp = null;
			Books tempBook = null;
			boolean weatherLikeThisBook = false;
			Set<Books> book = user.getBooks();
			for (int i = 0; i < booksNumber; i++){
				tempBook = books.get(i);
				weatherLikeThisBook = book.contains(tempBook);
				temp = new BookListData(tempBook, weatherLikeThisBook);
				bookList.add(temp);
			}
			return bookList;
		}
		// 图书数目超过10本，通过查看加入心愿单的人数，来进行排名
		int[] temp = new int[booksNumber];
		int[] sort = new int[booksNumber];
		for (int i = 0; i < booksNumber; i++) {
			temp[i] = books.get(i).getFollowNumber();
			sort[i] = i;
		}
		for (int i = 0; i < booksNumber - 1; i++) {
			for (int j = 1; j < booksNumber - i; j++) {
				if (temp[j - 1] < temp[j]){
					int a = temp[j - 1];
					temp[j - 1] = temp[j];
					temp[j] = a;
					a = sort[i - 1];
					sort[i - 1] = sort[i];
					sort[i] = a;
				}
			}
		}
		List<BookListData> answer = new ArrayList<>();
		BookListData tempBookListData = null;
		Books tempBooks = null;
		boolean weatherLikeThisBook = false;
		Set<Books> book = user.getBooks();
		for (int i = 0; i < 10; i++) {
			tempBooks = books.get(sort[i]);
			weatherLikeThisBook = book.contains(tempBooks);
			tempBookListData = new BookListData(tempBooks, weatherLikeThisBook);
			answer.add(tempBookListData);
		}
		return answer;
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
