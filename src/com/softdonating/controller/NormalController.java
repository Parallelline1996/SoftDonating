package com.softdonating.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softdonating.domain.Books;
import com.softdonating.response.BookListData;
import com.softdonating.service.BookService;
import com.softdonating.service.NormalService;

@Controller
public class NormalController {
	
	

	/**
	 * 热门图书推荐(推荐被加入心愿单人数最多的十本书)
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/hotBook")
	public List<BookListData> getBookList(HttpServletResponse response){
		Integer userId = getUserId();
		return bookService.bestBooks(userId);
	}
	
	// 补全


	/**
	 * 搜索图书信息
	 * @param bookName 搜索的图书名
	 * @return 匹配到的图书列表
	 */
	@ResponseBody
	@RequestMapping("/sortBooks")
	public List<BookListData> sortBooks(@RequestBody Books bookName){
		Integer userId = getUserId();
		return normalService.sortBooks(bookName.getName(), userId);
	}
	
	
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
	
	@Autowired
	@Qualifier("normalServiceImpl")
	private NormalService normalService;
	
	@Autowired
	@Qualifier("bookServiceImpl")
	private BookService bookService;
	
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
