package com.softdonating.controller;

import java.util.List;

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
import com.softdonating.request.UnconfirmDonateBook;
import com.softdonating.service.AccountService;
import com.softdonating.service.BookService;
import com.softdonating.service.NormalService;

@Controller
public class UserController {

	// 查看图书list
	@ResponseBody
	@RequestMapping("/bookList/{page}")
	public List<Books> getBookList(@PathVariable("page") Integer page){
		return bookService.getBookByPage(page);
	}
	
	// 查看总的图书种类
	@ResponseBody
	@RequestMapping("/numberOfBook")
	public int getNumberOfBook() {
		return bookService.numberOfKindOfBook();
	}
	
	// 登陆、注册
	@ResponseBody
	@RequestMapping("/login")
	public int login() {
		HttpSession session = request.getSession();
		session.setAttribute("userId", 1);
		return 200;
	}
	
	// 通过输入isbn获取图书的具体信息
	@ResponseBody
	@RequestMapping("/getBookData")
	public Books getBookData(@RequestBody Books books) {
		String isbn = books.getIsbn();
		return bookService.findBookByIsbn(isbn);
	}
	
	
	// 捐赠
	@ResponseBody
	@RequestMapping("/donate")
	public int Donate(@RequestBody BookWithNumber data) {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.donateBook(userId, data);
	}
	
	// 获取未确定列表
	@ResponseBody
	@RequestMapping("/unconfirmedDonatingList")
	public List<UnconfirmDonateBook> unconfirmedDonatingList() {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.getUnconfirmedDonate(userId);
	}
	
	// 确定捐赠
	@ResponseBody
	@RequestMapping("/confirmDonating")
	public int donateBook(@RequestBody List<Integer> data) {
		return bookService.confirmDonate(data);
	}
	
	// 加入心愿单，通过isbn
	@ResponseBody
	@RequestMapping("/createWishList")
	public int createWishList(@RequestBody String isbn) {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.createWithList(userId, isbn);
	}
	
	// 删除心愿单
	@ResponseBody
	@RequestMapping("/deleteWishList")
	public int deleteWishList(@RequestBody String isbn) {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.deleteWithList(userId, isbn);
	}
	
	// 获取心愿单
	@ResponseBody
	@RequestMapping("/getWishList")
	public List<Books> getWishList() {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.getWishList(userId);
	}
	
	// 确认拿书
	@ResponseBody
	@RequestMapping("/takeBook")
	public int takeBook(@RequestBody String isbn) {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		return bookService.takeBook(userId, isbn);
	}
	
	// 查看用户个人信息
	@ResponseBody
	@RequestMapping("/getUserData")
	public User getUserData(@RequestBody User user) {
		Integer userId = user.getUserId();
		if (userId != null) {
			return accountService.findUserById(userId);
		}
		return accountService.findUserByCode(user.getCode());
	}
	
	// 查看捐书记录
	
	// 查看拿书记录
	
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
