package com.softdonating.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softdonating.domain.User;
import com.softdonating.request.BookWithNumber;
import com.softdonating.response.BookDetail;
import com.softdonating.response.BookDetailWithLike;
import com.softdonating.response.BookListData;
import com.softdonating.response.BookRecord;
import com.softdonating.response.UnconfirmDonateBook;
import com.softdonating.service.AccountService;
import com.softdonating.service.BookService;
import com.softdonating.service.NormalService;

@Controller
public class UserController {

	/**
	 * 登陆函数
	 * @param code 
	 * @return map，其中code = 1时，代表为第一次登陆
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Map<String, String> login(@RequestBody String code) {
		return accountService.login(code);
	}
	
	/**
	 * 测试session维持函数
	 * @return 非负数，则代表维持成功
	 */
	@ResponseBody
	@RequestMapping("/loginTest")
	public int loginTest(){
		Integer userId = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("userId")){
				userId = Integer.parseInt(cookie.getValue());
				break;
			}
		}
		// 请求头中没有userId
		if (userId == null) {
			return -1;
		}
		return userId;
	}
	
	/**
	 * 完善用户信息
	 * @param user
	 * @return (404 -> 未登陆)(200 -> 成功)(-1 -> 异常)
	 */
	@ResponseBody
	@RequestMapping("/setUserData")
	public int setUserData(@RequestBody User user){
		Integer userId = getUserId();
		if (userId == null) {
			return -1;
		}
		return accountService.updateUserData(user, userId);
	}
	
	/**
	 * 查看用户信息
	 * @return 用户个人信息
	 */
	@ResponseBody
	@RequestMapping("/getUserData")
	public User getUserData() {
		Integer userId = getUserId();
		if (userId == null) {
			return null;
		}
		return accountService.findUserById(userId);
	}
	
	/**
	 * 查看捐书数目
	 * @return 整数代表数目
	 */
	@ResponseBody
	@RequestMapping("/numberOfDonate")
	public int numberOfDonate() {
		Integer userId = getUserId();
		if (userId == null) {
			return -1;
		}
		return bookService.numberOfDonates(userId);
	}
	
	/**
	 * 查看拿书数目
	 * @return 整数代表数目
	 */
	@ResponseBody
	@RequestMapping("/numberOfTake")
	public int numberOfTake() {
		Integer userId = getUserId();
		if (userId == null) {
			return -1;
		}
		return bookService.numberOfTakes(userId);
	}
	
	/**
	 *  查看捐书记录
	 * @return 捐书记录
	 */
	@ResponseBody
	@RequestMapping("/getDonateRecord")
	public List<BookRecord> getDonateRecord() {
		Integer userId = getUserId();
		if (userId == null) {
			return null;
		}
		return bookService.donateList(userId);
	}
	
	/**
	 *  查看拿书记录
	 * @return 拿书记录
	 */
	@ResponseBody
	@RequestMapping("/getTakeRecord")
	public List<BookRecord> getTakeRecord() {
		Integer userId = getUserId();
		if (userId == null) {
			return null;
		}
		return bookService.takeList(userId);
	}
	
	/**
	 * 获取图书具体信息
	 * @param isbn 图书的ISBN
	 * @return 图书的具体信息，如果该图书未能被找到，则返回Null
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/getBookData")
	public BookDetail getBookData(@RequestBody String isbn) throws Exception {
		Integer userId = getUserId();
		if (userId == null) {
			return null;
		}
		return bookService.findBookByIsbn(isbn, userId);
	}
	
	/**
	 * 通过图书ID查看具体的图书信息
	 * @param bookId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBookDataById")
	public BookDetailWithLike getBookDataById(@RequestBody Integer bookId) {
		Integer userId = getUserId();
		return bookService.findBookByBookId(userId, bookId);
	}
	
	
	/**
	 * 捐赠图书，加入待确定清单
	 * @param data 捐赠的信息，图书的ISBN
	 * @return 整数类型：(404 -> 未登陆)(200 -> 成功)(-1 -> 异常)
	 */
	@ResponseBody
	@RequestMapping("/donate")
	public int Donate(@RequestBody Integer bookId) {
		Integer userId = getUserId();
		return bookService.donateBook(userId, bookId);
	}
	
	/**
	 * 获取未确定列表，自动清除超过3天未确定的捐赠记录
	 * @return List，返回未确定的图书列表
	 */
	@ResponseBody
	@RequestMapping("/unconfirmedDonatingList")
	public List<UnconfirmDonateBook> unconfirmedDonatingList() {
		Integer userId = getUserId();
		return bookService.getUnconfirmedDonate(userId);
	}
	
	/**
	 * 删除未确定捐赠
	 * @return 整数类型(200 -> 成功)(-1 -> 异常)(404 -> 未登陆)
	 */
	@ResponseBody
	@RequestMapping("/deleteUnconfirmedDonatingList")
	public int deleteUnconfirmedDonatingList(@RequestBody Integer donateId) {
		Integer userId = getUserId();
		if (userId == null) {
			return 404;
		}
		return bookService.deleteUnconfirmedDonating(donateId, userId);
	}
	
	/**
	 * 确定捐赠
	 * @param data
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/confirmDonating")
	public int confirmDonating(@RequestBody List<BookWithNumber> data) {
		Integer userId = getUserId();
		return bookService.confirmDonate(data, userId);
	}
	
	/**
	 * 加入心愿单
	 * @param isbn 图书的ISBN
	 * @return 整数类型：(404 -> 未登陆)(200 -> 成功)(-1 -> 异常)
	 */
	@ResponseBody
	@RequestMapping("/createWishList")
	public int createWishList(@RequestBody Integer bookId) {
		Integer userId = getUserId();
		if (userId == null) {
			return 404;
		}
		return bookService.createWithList(userId, bookId);
	}
	
	/**
	 * 删除心愿单
	 * @param isbn 图书的ISBN
	 * @return 整数类型：(404 -> 未登陆)(200 -> 成功)(-1 -> 异常)
	 */
	@ResponseBody
	@RequestMapping("/deleteWishList")
	public int deleteWishList(@RequestBody Integer bookId) {
		Integer userId = getUserId();
		if (userId == null) {
			return 404;
		}
		return bookService.deleteWithList(userId, bookId);
	}
	
	/**
	 * 获取心愿单
	 * @return 心愿单List
	 */
	@ResponseBody
	@RequestMapping("/getWishList")
	public List<BookListData> getWishList() {
		Integer userId = getUserId();
		if (userId == null) {
			return null;
		}
		return bookService.getWishList(userId);
	}
	
	/**
	 * 确认拿书
	 * @param isbn 图书ISBN
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/takeBook")
	public int takeBook(@RequestBody Integer bookId) {
		Integer userId = getUserId();
		return bookService.takeBook(userId, bookId);
	}
	
	
	
	
	
	@Autowired
	@Qualifier("bookServiceImpl")
	private BookService bookService;
	
	@Autowired
	@Qualifier("accountServiceImpl")
	private AccountService accountService;
	
	@Autowired
	@Qualifier("normalServiceImpl")
	private NormalService normalService;
	
	@Autowired
	private HttpServletRequest request;
	
	private Integer getUserId(){
		Integer userId = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("userId")){
				userId = Integer.parseInt(cookie.getValue());
				break;
			}
		}
		return userId;
	}
}
