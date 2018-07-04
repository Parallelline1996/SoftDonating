package com.softdonating.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softdonating.domain.Books;
import com.softdonating.response.BookListData;
import com.softdonating.service.BookService;

@Controller
public class NormalController {
	
	
	// 图片、题目、作者、出版社、isbn、库存、是否被收藏、bookId
	// 返回热门推荐的图书 —— 10本
	@ResponseBody
	@RequestMapping("/hotBook")
	public List<BookListData> getBookList(HttpServletResponse response){
		Integer userId = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("userId")){
				userId = Integer.parseInt(cookie.getValue());
				break;
			}
		}
		return bookService.bestBooks(userId);
	}
	
	// 补全


	// 搜索，前后补全
	
	
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
	@Qualifier("bookServiceImpl")
	private BookService bookService;
	
	@Autowired
	private HttpServletRequest request;
}
