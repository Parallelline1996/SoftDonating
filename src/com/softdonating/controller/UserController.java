package com.softdonating.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softdonating.domain.Books;
import com.softdonating.domain.User;
import com.softdonating.request.BookWithNumber;
import com.softdonating.response.BookRecord;
import com.softdonating.response.UnconfirmDonateBook;
import com.softdonating.service.AccountService;
import com.softdonating.service.BookService;
import com.softdonating.service.NormalService;


@Controller
public class UserController {
	
	/**
	 * 分页查看图书信息
	 * @param page 页码
	 * @return 图书列表
	 */
	@ResponseBody
	@RequestMapping("/bookList/{page}")
	public List<Books> getBookList(@PathVariable("page") Integer page){
		return bookService.getBookByPage(page);
	}
	
	/**
	 * 查看图书现有种类
	 * @return 返回数目
	 */
	@ResponseBody
	@RequestMapping("/numberOfBook")
	public int getNumberOfBook() {
		return bookService.numberOfKindOfBook();
	}

	/**
	 * 登陆函数
	 * @param code 
	 * @return map类，其中code = 1时，代表为第一次登陆
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Map<String, String> login(@RequestBody String code) {
		return accountService.login(code);
	}

	
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
	@RequestMapping("/userData")
	public int UserData(@RequestBody User user){
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
		return accountService.updateUserData(user, userId);
	}
	
	
	/**
	 * 获取图书具体信息
	 * @param isbn 图书的ISBN
	 * @return 图书的具体信息，如果该图书未能被找到，则返回Null
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/getBookData")
	public Books getBookData(@RequestBody String isbn) throws Exception {
		return bookService.findBookByIsbn(isbn);
	}
	
	
	/**
	 * 捐赠图书，加入待确定清单
	 * @param data 捐赠的信息，图书的ISBN
	 * @return 整数类型：(404 -> 未登陆)(200 -> 成功)(-1 -> 异常)
	 */
	@ResponseBody
	@RequestMapping("/donate")
	public int Donate(@RequestBody BookWithNumber data) {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.donateBook(userId, data);
	}
	
	/**
	 * 获取未确定列表，自动清除超过3天未确定的捐赠记录
	 * @return List，返回未确定的图书列表
	 */
	@ResponseBody
	@RequestMapping("/unconfirmedDonatingList")
	public List<UnconfirmDonateBook> unconfirmedDonatingList() {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.getUnconfirmedDonate(userId);
	}
	
	/**
	 * 删除未确定捐赠
	 * @return 整数类型(200 -> 成功)(-1 -> 异常)
	 */
	@ResponseBody
	@RequestMapping("/deleteUnconfirmedDonatingList")
	public int deleteUnconfirmedDonatingList(@RequestBody Integer donateId) {
		return bookService.deleteUnconfirmedDonating(donateId);
	}
	
	/**
	 * 确定捐赠
	 * @param data
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/confirmDonating")
	public int donateBook(@RequestBody List<BookWithNumber> data) {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.confirmDonate(data, userId);
	}
	
	/**
	 * 加入心愿单
	 * @param isbn 图书的ISBN
	 * @return 整数类型：(404 -> 未登陆)(200 -> 成功)(-1 -> 异常)
	 */
	@ResponseBody
	@RequestMapping("/createWishList")
	public int createWishList(@RequestBody String isbn) {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.createWithList(userId, isbn);
	}
	
	/**
	 * 删除心愿单
	 * @param isbn 图书的ISBN
	 * @return 整数类型：(404 -> 未登陆)(200 -> 成功)(-1 -> 异常)
	 */
	@ResponseBody
	@RequestMapping("/deleteWishList")
	public int deleteWishList(@RequestBody String isbn) {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.deleteWithList(userId, isbn);
	}
	
	/**
	 * 获取心愿单
	 * @return 心愿单List
	 */
	@ResponseBody
	@RequestMapping("/getWishList")
	public List<Books> getWishList() {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.getWishList(userId);
	}
	
	/**
	 * 确认拿书
	 * @param isbn 图书ISBN
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/takeBook")
	public int takeBook(@RequestBody String isbn) {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.takeBook(userId, isbn);
	}
	
	/**
	 * 查看用户信息
	 * @return 用户个人信息
	 */
	@ResponseBody
	@RequestMapping("/getUserData")
	public User getUserData() {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return accountService.findUserById(userId);
	}
	
	/**
	 * 查看捐书数目
	 * @return 整数代表数目
	 */
	@ResponseBody
	@RequestMapping("/numberOfDonate")
	public int numberOfDonate() {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.numberOfDonates(userId);
	}
	
	/**
	 * 查看拿书数目
	 * @return 整数代表数目
	 */
	@ResponseBody
	@RequestMapping("/numberOfTake")
	public int numberOfTake() {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.numberOfTakes(userId);
	}
	
	/**
	 *  查看捐书记录
	 * @return 捐书记录
	 */
	@ResponseBody
	@RequestMapping("/getDonateRecord")
	public List<BookRecord> getDonateRecord() {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.donateList(userId);
	}
	
	/**
	 *  查看拿书记录
	 * @return 拿书记录
	 */
	@ResponseBody
	@RequestMapping("/getTakeRecord")
	public List<BookRecord> getTakeRecord() {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.takeList(userId);
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
}
