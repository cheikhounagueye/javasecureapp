package com.bamba.secureapp.dao;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bamba.secureapp.entities.UserEntity;
import com.bamba.secureapp.hibernate.HibernateUtil;

public class LoginDao {
	Logger logger = LoggerFactory.getLogger(LoginDao.class);

	public Optional<UserEntity> log(String email, String pwd) {
		UserEntity result = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<UserEntity> cr = cb.createQuery(UserEntity.class);
		Root<UserEntity> user = cr.from(UserEntity.class);

		Predicate predicateEmail = cb.equal(user.get("email"), email);
		Predicate predicatePwd = cb.equal(user.get("password"), pwd);
		Predicate finalPredicate = cb.and(predicateEmail, predicatePwd);

		cr.select(user);
		cr.where(finalPredicate);

		try {
			result = session.createQuery(cr).getSingleResult();
			logger.info("Connexion ok");
		} catch (Exception e) {
			logger.error("Error :", e);
		} finally {
			session.close();
		}
		return Optional.ofNullable(result);
	}

	public Optional<UserEntity> logException(String email, String pwd) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<UserEntity> cr = cb.createQuery(UserEntity.class);
		Root<UserEntity> user = cr.from(UserEntity.class);
		// Predicate pour la clause where
		Predicate predicateEmail = cb.equal(user.get("email"), email);
		Predicate predicatePwd = cb.equal(user.get("password"), pwd);
		Predicate finalPredicate = cb.and(predicateEmail, predicatePwd);

		cr.select(user);
		cr.where(finalPredicate);

		return Optional.ofNullable(session.createQuery(cr).getSingleResult());
	}

	public Optional<UserEntity> loginUser(String email, String pwd) {
		UserEntity userEntity = null;
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			userEntity = (UserEntity) session.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email")
					.setParameter("email", email).uniqueResult();
			if (userEntity != null && userEntity.getPassword().equals(pwd)) {
				return Optional.ofNullable(userEntity);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				// transaction.rollback();
			}
			logger.error("Error ", e);
		}
		return Optional.ofNullable(userEntity);
	}

	public Optional<UserEntity> login(String email, String password) {

		if (email.equals("contact@samanecorporation.com") && password.equals("passer123@")) {
			UserEntity user = new UserEntity();
			user.setEmail(email);
			user.setPassword(password);

			return Optional.of(user);
		} else {
			return Optional.ofNullable(null);
		}

	}


}