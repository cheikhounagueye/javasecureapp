package com.bamba.secureapp.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.Root;

import com.bamba.secureapp.entities.UserEntity;
import com.bamba.secureapp.hibernate.HibernateUtil;

public class UserImpl implements IUser {
	// Commencer une transaction
	Transaction transaction = null;

	@Override
	public List<UserEntity> liste() {
		// Obtenir une session de Hibernate
		Session session = HibernateUtil.getSessionFactory().openSession();
		// Methode 2
		// https://www.baeldung.com/jpql-hql-criteria-query
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
		Root<UserEntity> root = cq.from(UserEntity.class);
		cq.select(root);

		List<UserEntity> results = session.createQuery(cq).getResultList();
		session.close();
		return results;
	}

	@Override
	public boolean delete(long mat) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.delete(session.get(UserEntity.class, mat));
			transaction.commit();
			return true;
		} catch (Exception e2) {
			return false;
		}
	}

	@Override
	public boolean update(UserEntity e) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.merge(e);
			transaction.commit();
			return true;
		} catch (Exception e2) {
			return false;
		}
	}

	@Override
	public UserEntity get(long mat) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (UserEntity) session.get(UserEntity.class, mat);
	}

	@Override
	public boolean add(UserEntity u) {

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.save(u);
			transaction.commit();
			return true;
		} catch (Exception e2) {
			return false;
		}
	}

}
