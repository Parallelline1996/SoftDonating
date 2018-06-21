package com.softdonating.daoImpl;

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

}
