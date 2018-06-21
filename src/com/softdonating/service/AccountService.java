package com.softdonating.service;

import com.softdonating.domain.User;

public interface AccountService {

	User findUserById(Integer userId);
	
	User findUserByCode(String code);
}
