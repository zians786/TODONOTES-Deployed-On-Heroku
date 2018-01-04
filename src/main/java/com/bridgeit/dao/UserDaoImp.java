package com.bridgeit.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.model.User;

@Repository("userDao")
public class UserDaoImp implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public Boolean loginValidate(User user) {
		Query query = getSession().createQuery("select email from User where userName='" + user.getUserName()
				+ "' and password='" + user.getPassword() + "'and active=true");
		String result = (String) query.uniqueResult();
		Boolean status = false;
		if (result != null) {
			status = true;
		}
		return status;
	}

	public Boolean emailValidaton(String email) {

		Query query = getSession().createQuery("select userName from User where email='" + email + "'");
		String result = (String) query.uniqueResult();
		Boolean status;

		if (result != null) {
			status = true;
		} else {
			status = false;
		}
		return status;

	}

	public int activateUser(int userId) {
		Query query = getSession().createQuery("update User set active=:a where userId=:e");
		query.setParameter("a", true);
		query.setParameter("e", userId);
		int result = query.executeUpdate();
		return result;
	}

	public void registerUser(User user) {
		if(user.getActive()==true) {
			getSession().save(user);
		}else {
			user.setActive(false);
			getSession().save(user);	
		}
		
	}

	public String getPassword(String email) {

		Query query = getSession().createQuery("select password from User where email='" + email + "'");
		String result = (String) query.uniqueResult();
		return result;
	}

	public int getUserId(String userName) {
		Query query = getSession().createQuery("select userId from User where userName='" + userName + "'");
		int result = (int) query.uniqueResult();
		return result;
	}

	@Override
	public User getUserByEmailId(String email) {
		User user = null;
		Query query = getSession().createQuery("from User where email='" + email + "'");
		user = (User) query.uniqueResult();
		return user;
	}

	@Override
	public void resetPassword(User user) {
			getSession().update(user);
		}

	@Override
	public User getUserByUserName(String userName) {
	Query query=getSession().createQuery("from User where userName='"+userName+"'");
	User user=(User) query.uniqueResult();
		return user;
	}

	@Override
	public User getUserByUserId(int userId) {
		Query query=getSession().createQuery("from User where userId='"+userId+"'");
		User user=(User) query.uniqueResult();
			return user;
	}

}
