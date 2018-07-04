package com.softdonating.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.softdonating.dao.UserDao;
import com.softdonating.domain.User;
import com.softdonating.service.AccountService;

import net.sf.json.JSONObject;

@Service
@Qualifier("accountServiceImpl")
public class AccountServiceImpl implements AccountService {
	
	
	private final String APPID = "wx4f6638e0c15aefbf";
	private final String APPSECRET = "0b929c6afb6fd739c69ce9b58cf7653a";
	
	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDao;

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
	
	@Override
	public Map<String, String> login(String code) {
		String address = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID 
				+ "&secret=" + APPSECRET + "&js_code=" + code + "&grant_type=authorization_code";
		StringBuilder stringBuilder = new StringBuilder();
		try {
			URL url = new URL(address);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			if (connection == null){
				// 链接错误的时候怎么处理，这个得后期重新写
				return null;
			}
			connection.connect();
			BufferedReader bReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line = null;
			while ((line = bReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			bReader.close();
			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 将JSON文件进行解析
		JSONObject jsonObject = JSONObject.fromObject(stringBuilder.toString());
		String openId = jsonObject.get("openid").toString();
		String sessionKey = jsonObject.get("session_key").toString();
		System.out.println("openId: " + openId);
		System.out.println("sessionKey" + sessionKey);
		
		// 将openId和sessionKey放入到Map中
		Map<String, String> map = new HashMap<>();
		map.put("openid", openId);
		map.put("sessionKey", sessionKey);
		
		// 在这里对openid进行判断
		User user = userDao.findUserByCode(openId);
		if (user == null) {
			// 查不到信息，表示为第一次登陆
			user = new User(0, openId, null, null, null, null, null, null);
			userDao.createUser(user);
			user = userDao.findUserByCode(openId);
			map.put("userId", user.getUserId().toString());
			map.put("code", "1");
		} else {
			// 搜得到信息，并非第一次登陆
			map.put("userId", user.getUserId().toString());
			map.put("code", "100");
		}
		return map;
	}

	@Override
	public Integer updateUserData(User user, Integer userId) {
		User userOld = userDao.findUserById(userId);
		// 判断输入是否正确
		if (userOld == null || user == null){
			return 404;
		}
		// 对数值进行修改
		userOld.setFaculty(user.getFaculty());
		userOld.setGrade(user.getGrade());
		userOld.setName(user.getName());
		userOld.setPhoneNumber(user.getPhoneNumber());
		userOld.setPhoto(user.getPhoto());
		if (userDao.updateUser(userOld)){
			return 200;
		}
		return -1;
	}

}
