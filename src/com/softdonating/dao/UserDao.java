package com.softdonating.dao;


import com.softdonating.domain.Books;
import com.softdonating.domain.User;

public interface UserDao {

	boolean createUser(User user);
	
	User findUserById(Integer userId);
	
	User findUserByCode(String code);
	
	boolean addWishList(Integer userId, Books books);
	
	boolean deleteWishList(Integer userId, Books books);
	
	boolean updateUser(User user);
}
