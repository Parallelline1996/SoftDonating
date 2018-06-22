package com.softdonating.daoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.softdonating.dao.BookDao;
import com.softdonating.dao.DonateDao;
import com.softdonating.domain.Books;
import com.softdonating.domain.Donate;
import com.softdonating.util.HibernateUtil;

@Repository
@Qualifier("donateDaoImpl")
public class DonateDaoImpl extends HibernateUtil implements DonateDao {

	@Override
	public boolean createDonate(Donate data) {
		return save(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Donate> getUnconfirmedList(Integer userId) {
		String hql = "from Donate where userId = ? and status = -1";
		Session session = sessionFactory.openSession();
		List<Donate> donates = new ArrayList<>();
		try {
			Query query = session.createQuery(hql).setParameter(0, userId);
			List<Object> objects = (List<Object>)query.list();
			for (Object object : objects) {
				donates.add((Donate)object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return donates;
	}

	@Override
	public boolean confirmDonate(Integer donateId, Integer number) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Donate donate = null;
		boolean temp = false;
		try {
			donate = (Donate)session.get(Donate.class, donateId);
			if (donate.getStatus() == 1) {
				return false;
			}
			donate.setStatus(1);
			Date donateTime = new Date();
			donate.setDonateTime(donateTime);
			Books books = bookDao.findBookById(donate.getBookId());
			books.setNumber(books.getNumber() + number);
			temp = update(donate) && update(books);
			for(int i = 0; i < number - 1; i ++) {
				Date date = new Date(new Date().getTime() + (i + 1) * 1000);
				Donate donateTemp = new Donate(null, donate.getUserId(), donate.getBookId(), 1, date);
				if (!createDonate(donateTemp)) {
					return false;
				}
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			temp = false;
		} finally {
			session.close();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Donate findFirstDonate(String isbn) {
		String hql = "from Donate where bookId = ? and status = 1";
		Books books = bookDao.findBookByIsbn(isbn);
		Session session = sessionFactory.openSession();
		Donate donate = null;
		try {
			List<Object> object = (List<Object>)session.createQuery(hql).setParameter(0, books.getBookId()).list();
			donate = (Donate)object.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return donate;
	}

	@Override
	public boolean deleteDonate(Integer donateId) {
		Donate donate = findDonateById(donateId);
		if (donate == null) {
			return false;
		}
		return delete(donate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Donate> donateList(Integer userId) {
		Session session = sessionFactory.openSession();
		String hql = "from Donate where userId = ?";
		List<Donate> donates = new ArrayList<>();
		List<Object> temp = null;
		try {
			temp = (List<Object>)session.createQuery(hql).setParameter(0, userId).list();
			for (Object object : temp) {
				donates.add((Donate)object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return donates;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Donate findDonate(Integer userId, Integer bookId) {
		Session session = sessionFactory.openSession();
		String hql = "from Donate where userId = ? and bookId = ?";
		Donate donate = null;
		try {
			List<Object> temp = (List<Object>)session.createQuery(hql)
					.setParameter(0, userId).setParameter(1, bookId).list();
			donate = (Donate)temp.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return donate;
	}

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("bookDaoImpl")
	private BookDao bookDao;

	@Override
	public Donate findDonateById(Integer donateId) {
		Session session = sessionFactory.openSession();
		Donate donate = null;
		try {
			donate = (Donate)session.get(Donate.class, donateId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return donate;
	}

}
