package com.softdonating.daoImpl;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.softdonating.dao.UserDao;
import com.softdonating.domain.Books;
import com.softdonating.domain.User;
import com.softdonating.util.HibernateUtil;

@Repository
@Qualifier("userDaoImpl")
public class UserDaoImpl extends HibernateUtil implements UserDao {

	@Override
	public boolean createUser(User user) {
		return save(user);
	}

	@Override
	public User findUserById(Integer userId) {
		Session session = sessionFactory.openSession();
		User user = null;
		try {
			user = (User)session.get(User.class, userId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

	@Override
	public User findUserByCode(String code) {
		String hql = "from User where code = ?";
		Session session = sessionFactory.openSession();
		User user = null;
		try {
			user = (User)session.createQuery(hql).setParameter(0, code).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public boolean addWishList(Integer userId, Books books) {
		User user = findUserById(userId);
		Set<Books> set = user.getBooks();
		if (set.contains(books)) {
			return false;
		}
		set.add(books);
		user.setBooks(set);
		return update(user);
	}

	@Override
	public boolean deleteWishList(Integer userId, Books books) {
		// 删除是多对多关系集里面的一条关系
		User user = findUserById(userId);
		Set<Books> setBook = user.getBooks();
		if (!setBook.contains(books)) {
			return false;
		}
		setBook.remove(books);
		user.setBooks(setBook);
		return update(user);
	}

	@Override
	public boolean updateUser(User user) {
		return update(user);
	}

}
