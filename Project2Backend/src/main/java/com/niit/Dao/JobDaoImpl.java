package com.niit.Dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Models.Job;

@Repository
@Transactional
public class JobDaoImpl implements JobDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	public JobDaoImpl()
	{
	 System.out.println("JobDaoImpl Bean is Created");
	}

	public void addJob(Job job) 
	{
		System.out.println(job.getPostedOn());
		Session session=sessionFactory.getCurrentSession();
		session.save(job);

	}

	public List<Job> getAllJobs()
	{
		Session session=sessionFactory.getCurrentSession();
		  Query query=session.createQuery("from Job");
		  List<Job> jobs=query.list();
		  return jobs;	
	}

	public void deleteJob(int id) 
	{
	 Session session = sessionFactory.getCurrentSession();
	 Job job=(Job)session.get(Job.class, id);
	 session.delete(job);
	}

}
