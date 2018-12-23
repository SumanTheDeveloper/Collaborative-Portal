package com.niit.Dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Models.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao 
{
	@Autowired
	private   SessionFactory sessionFactory;

    public UserDaoImpl()
    {
	 System.out.println("UserDaoImpl bean is created");
	}
	public void registerUser(User user) 
	{
		Session session=sessionFactory.getCurrentSession();
		session.save(user);
	}
	public boolean isEmailValid(String email)
	{
		Session session=sessionFactory.getCurrentSession();
		User user=(User)session.get(User.class,email);
		if(user==null)
			return true;
		else
			return false;
	}
	public User login(User user)
	{
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User where email=? and password=?");
		query.setString(0, user.getEmail());
		query.setString(1, user.getPassword());
		return (User)query.uniqueResult();
	}
	public void update(User user)
	{
		Session session=sessionFactory.getCurrentSession();
		session.update(user);		
	}
	public User getUser(String email) 
	{
		Session session=sessionFactory.getCurrentSession();
		User user=(User)session.get(User.class, email);
		return user;
	}

}
