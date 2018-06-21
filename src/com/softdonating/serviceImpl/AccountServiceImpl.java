package com.softdonating.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.softdonating.dao.UserDao;
import com.softdonating.domain.User;
import com.softdonating.service.AccountService;

@Service
@Qualifier("accountServiceImpl")
public class AccountServiceImpl implements AccountService {

	@Override
	public User findUserById(Integer userId) {
		if (userId == null) {
			return null;
		}
		User user = userDao.findUserById(userId);
		// 将关注的图书置为null，防止循环读取，如果需要再将这个进行转换
		user.setBooks(null);
		return user;
	}

	@Override
	public User findUserByCode(String code) {
		if (code == null) {
			return null;
		}
		User user = userDao.findUserByCode(code);
		user.setBooks(null);
		return user;
	}
	
	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDao;

}
