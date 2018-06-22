package com.softdonating.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.softdonating.dao.BookDao;
import com.softdonating.domain.Books;
import com.softdonating.util.HibernateUtil;

@Repository
@Qualifier("bookDaoImpl")
public class BookDaoImpl extends HibernateUtil implements BookDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public Books findBookByIsbn(String isbn) {
		String hql = "from Books where isbn = " + isbn;
		Session session = sessionFactory.openSession();
		Books book = null;
		try {
			book = (Books)session.createQuery(hql).uniqueResult();	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return book;
	}

	@Override
	public boolean bookExist(String isbn) {
		return false;
	}

	@Override
	public Books findBookById(Integer bookId) {
		Session session = sessionFactory.openSession();
		Books books = null;
		try {
			books = (Books)session.get(Books.class, bookId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return books;
	}

	@Override
	public List<Books> getBookListByPage(Integer page) {
		String hql = "from Books";
		List<Object> objects = listpage(hql, page, 5);
		List<Books> books = new ArrayList<>();
		for (Object I : objects) {
			Books temp = (Books)I;
			temp.setUsers(null);
			books.add(temp);
		}
		return books;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer numberOfKindOfBook() {
		String hql = "from Books";
		int number = 0;
		Session session = sessionFactory.openSession();
		List<Object> objects = null;
		try {
			objects = (List<Object>)session.createQuery(hql).list();
			number = objects.size();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return number;
	}

	@Override
	public boolean updateBook(Books books) {
		if (books.getFollowNumber() < 0 || books.getNumber() < 0) {
			return false;
		}
		return update(books);
	}

}
