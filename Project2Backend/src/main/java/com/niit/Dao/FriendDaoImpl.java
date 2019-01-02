package com.niit.Dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Models.Friend;
import com.niit.Models.User;

@Repository
@Transactional
public class FriendDaoImpl implements FriendDao
{
	@Autowired
	private SessionFactory sessionFactory;
	public List<User> getAllSuggestedUsers(String email)
	{
		Session session=sessionFactory.getCurrentSession();
		String queryString="select * from User_info where email " 
				+ "in "
				+ "(select email from User_info where email!=:e3 "
				+ "minus "
				+ "(select fromid_email from friend where toid_email=:e1 "
				+ "union "
				+ "select toid_email from friend where fromid_email=:e2))";
		SQLQuery query=session.createSQLQuery(queryString);
		query.setString("e1", email);
		query.setString("e2", email);
		query.setString("e3", email);
		query.addEntity(User.class);
		return query.list();
	}

	public void friendRequest(Friend friend) 
	{
		Session session=sessionFactory.getCurrentSession();
		session.save(friend);
	}

	public List<Friend> pendingRequests(String email)
	{
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Friend where toId.email=:email and status=:status ");
		query.setString("email", email);
		query.setCharacter("status", 'P');
		List<Friend> pendingRequests=query.list();
		return pendingRequests;	
	}

	public void deleteRequest(Friend friendRequest) 
	{
	 Session session = sessionFactory.getCurrentSession();	
	 session.delete(friendRequest);
	}

	public void acceptRequest(Friend friendRequest)
	{
		Session session=sessionFactory.getCurrentSession();
		friendRequest.setStatus('A');
		session.update(friendRequest);
	}

	public List<User> listOfFriends(String email)
	{
		Session session=sessionFactory.getCurrentSession();
		//f is alias
		Query query1=session.createQuery("select f.toId from Friend f where f.fromId.email=:email and f.status=:A");
		query1.setString("email", email);
		query1.setCharacter("A", 'A');
		List<User> list1=query1.list();//list of users who have accepted the request from logged in user
		
		Query query2=session.createQuery("select f.fromId from Friend f where f.toId.email=:email and f.status=:A");
		query2.setString("email", email);
		query2.setCharacter("A", 'A');
		List<User> list2=query2.list();//logged in user accepted the requests from other users
		
		list1.addAll(list2);//list1=list1 U  list2
		
		return list1;
	}

}
