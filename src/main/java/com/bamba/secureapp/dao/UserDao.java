package com.bamba.secureapp.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.bamba.secureapp.entities.UserEntity;
import com.bamba.secureapp.hibernate.HibernateUtil;

public class UserDao {

//	public boolean add(UserEntity user) {
//		Transaction transaction = null;
//		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//			transaction = session.beginTransaction();
//			session.save(user);
//			transaction.commit();
//			return true;
//		} catch (Exception e) {
//			if (transaction != null) {
//				transaction.rollback();
//			}
//			e.printStackTrace();
//			return false;
//		}
//	}

	public boolean add(UserEntity user) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public UserEntity get(Long id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(UserEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean update(UserEntity user) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.update(user);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(Long id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			UserEntity user = session.get(UserEntity.class, id);
			if (user != null) {
				session.delete(user);
				transaction.commit();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}
}
