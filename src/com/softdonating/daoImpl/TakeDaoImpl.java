package com.softdonating.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Repository;

import com.softdonating.dao.DonateDao;
import com.softdonating.dao.TakeDao;
import com.softdonating.domain.Take;
import com.softdonating.util.HibernateUtil;

@Repository
@Qualifier("takeDaoImpl")
public class TakeDaoImpl extends HibernateUtil implements TakeDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("donateDaoImpl")
	private DonateDao donateDao;
	
	@Override
	public boolean createTake(Take take, Integer donateId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		boolean status = false;
		try {
			session.save(session.merge(take));
			donateDao.deleteDonate(donateId);
			tx.commit();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
		return status;
	}

	@Override
	public Integer numberOfTake(Integer userId) {
		String hql = "from Take where userId = ?";
		return numberOfResult(hql, userId);
	}

	@Override
	public Integer numberOfDonate(Integer userId) {
		String hql = "from Take where donateUserId = ?";
		String hql1 = "from Donate where userId = ?";
		return numberOfResult(hql1, userId) + numberOfResult(hql, userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Take> takeList(Integer userId) {
		Session session = sessionFactory.openSession();
		String hql = "from Take where userId = ?";
		List<Take> takes = new ArrayList<>();
		List<Object> temp = null;
		try {
			temp = (List<Object>)session.createQuery(hql).setParameter(0, userId).list();
			for (Object object : temp) {
				takes.add((Take)object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return takes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Take> donateList(Integer userId) {
		Session session = sessionFactory.openSession();
		String hql = "from Take where donateUserId = ?";
		List<Take> takes = new ArrayList<>();
		List<Object> temp = null;
		try {
			temp = (List<Object>)session.createQuery(hql).setParameter(0, userId).list();
			for (Object object : temp) {
				takes.add((Take)object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return takes;
	}

}
