package com.softdonating.service;

import java.util.Map;

import com.softdonating.domain.User;

public interface AccountService {

	User findUserById(Integer userId);
	
	User findUserByCode(String code);
	
	Map<String, String> login(String code);
	
	Integer updateUserData(User user, Integer userId);
}
