package com.softdonating.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softdonating.domain.Books;
import com.softdonating.service.BookService;

@Controller
public class NormalController {
	
	
	// 图片、题目、作者、出版社、isbn、库存、是否被收藏、bookId
	// 返回热门推荐的图书 —— 10本
	@ResponseBody
	@RequestMapping("/hotBook")
	public List<Books> getBookList(HttpServletResponse response){
		return bookService.bestBooks();
	}
	
	// 补全


	// 搜索，前后补全
	
	@Autowired
	@Qualifier("bookServiceImpl")
	private BookService bookService;
}
