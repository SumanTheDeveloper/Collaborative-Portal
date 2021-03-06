package com.niit.Controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.niit.Dao.JobDao;
import com.niit.Dao.UserDao;
import com.niit.Models.ErrorClass;
import com.niit.Models.Job;
import com.niit.Models.User;

@RestController  
public class JobController 
{
	@Autowired
	private JobDao jobDao;
	@Autowired
	private UserDao userDao;
	    @RequestMapping(value="/addjob",method=RequestMethod.POST)
		public ResponseEntity<?> addJob(@RequestBody Job job,HttpSession session)
	    {
	    	String email=(String)session.getAttribute("email");
	    	if(email==null){
	    		ErrorClass errorClazz=new ErrorClass(6,"Please login...");
	    		return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);
	    	}
	    	User user=userDao.getUser(email);
	    	if(!user.getRole().equals("ADMIN"))
	    	{
	    		ErrorClass errorClazz=new ErrorClass(7,"Not Authorized to post any job details..");
	    		return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);
	    	}
	    	job.setPostedOn(new Date());
	    	try{
	    	jobDao.addJob(job);
	    	}catch(Exception e){
	    		ErrorClass errorClazz=new ErrorClass(8,"Unable to insert job details.."+e.getMessage());
	    		return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
	    	}
	    	return new ResponseEntity<Job>(job,HttpStatus.OK);    	
		}
	    
	@RequestMapping(value="/alljobs",method=RequestMethod.GET)
    public ResponseEntity<?> getAllJobs(HttpSession session)
	{
    	String email=(String)session.getAttribute("email");
    	if(email==null){
    		ErrorClass errorClazz=new ErrorClass(6,"Please login...");
    		return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);
    	}
    	List<Job> jobs=jobDao.getAllJobs();
    	return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
    }
	
	@RequestMapping(value="/deletejob/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<?> deletejobs(@PathVariable int id,HttpSession session)
	{
		String email=(String)session.getAttribute("email");
    	if(email==null)
    	{
    		ErrorClass errorClazz=new ErrorClass(6,"Please login...");
    		return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);
    	}
        User user=userDao.getUser(email);
	   	if(!user.getRole().equals("ADMIN"))
	   	{
	  		ErrorClass errorClass=new ErrorClass(7,"Not Authorized to post any job details..");
    		return new ResponseEntity<ErrorClass>(errorClass,HttpStatus.UNAUTHORIZED);
    	}	
	   	try {
	   	jobDao.deleteJob(id);
	   	}catch(Exception e)
	   	{
	   		ErrorClass errorClazz=new ErrorClass(9,"Unable to delete job details.."+e.getMessage());
    		return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
	   	}
	   	
	   	return new ResponseEntity<Void>(HttpStatus.OK);
	}
    	
	
}


