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
import org.springframework.web.bind.annotation.RestController;

import com.niit.Dao.BlogPostDao;
import com.niit.Dao.NotificationDao;
import com.niit.Dao.UserDao;
import com.niit.Models.BlogPost;
import com.niit.Models.ErrorClass;
import com.niit.Models.Notification;
import com.niit.Models.User;

@RestController
public class BlogPostController 
{
	@Autowired
    private BlogPostDao blogPostDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private NotificationDao  notificationDao;
    @RequestMapping(value="/addblogpost",method=RequestMethod.POST)
	public ResponseEntity<?> addBlogPost(@RequestBody BlogPost blogPost,HttpSession session){
		String email=(String)session.getAttribute("email");
		//NOT LOGGED IN
		if(email==null){
			ErrorClass errorClazz=new ErrorClass(6,"Please login...");
    		return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		blogPost.setPostedOn(new Date());
		User postedBy=userDao.getUser(email);
		blogPost.setPostedBy(postedBy);
		try{
		blogPostDao.addBlogPost(blogPost);
		}catch(Exception e){
			ErrorClass errorClazz=new ErrorClass(8,"Unable to save blog post details.." + e.getMessage());
			return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	@RequestMapping(value="/blogswaitingforapproval",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogsWaitingForApproval(HttpSession session){
		String email=(String)session.getAttribute("email");
		//NOT LOGGED IN
		if(email==null){
			ErrorClass errorClazz=new ErrorClass(6,"Please login...");
    		return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		//ROLE - AUTHORIZATION
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN")){
			ErrorClass errorClazz=new ErrorClass(9,"You are not authorized to view the content..");
			return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<BlogPost> blogsWaitingForApproval=blogPostDao.getBlogsWaitingForApproval();
		return new ResponseEntity<List<BlogPost>>(blogsWaitingForApproval,HttpStatus.OK);
	}
	
	@RequestMapping(value="/blogsapproved",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogsApproved(HttpSession session){
		String email=(String)session.getAttribute("email");
		//NOT LOGGED IN
		if(email==null){
			ErrorClass errorClazz=new ErrorClass(6,"Please login...");
    		return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		
		List<BlogPost> blogsApproved=blogPostDao.getBlogsApproved();
		return new ResponseEntity<List<BlogPost>>(blogsApproved,HttpStatus.OK);
	}
	@RequestMapping(value="/getblog/{blogId}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlog(@PathVariable int blogId,HttpSession session){
		String email=(String)session.getAttribute("email");
		//NOT LOGGED IN
		if(email==null){
			ErrorClass errorClazz=new ErrorClass(6,"Please login...");
    		return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		BlogPost blogPost=blogPostDao.getBlog(blogId);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	
	@RequestMapping(value="/approveblog/{blogId}",method=RequestMethod.PUT)
	public ResponseEntity<?> approveBlog(@PathVariable int blogId,HttpSession session){
		String email=(String)session.getAttribute("email");
		//NOT LOGGED IN
		if(email==null){
			ErrorClass errorClazz=new ErrorClass(6,"Please login...");
    		return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		//ROLE - AUTHORIZATION
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN")){
			ErrorClass errorClazz=new ErrorClass(9,"You are not authorized to view the content..");
			return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		blogPostDao.approveblogPost(blogId);
		
		BlogPost blogPost=blogPostDao.getBlog(blogId);
		Notification notification=new Notification();
		notification.setBlogTitle(blogPost.getBlogTitle());
		notification.setStatus("Approved");//value of status is "Approved"  or "Rejected"
		notification.setUserToBeNotified(blogPost.getPostedBy().getEmail());
		notificationDao.addNotification(notification);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectblog/{rejectionReason}",method=RequestMethod.PUT)
	public ResponseEntity<?> rejectBlog(@PathVariable String rejectionReason,@RequestBody BlogPost blogPost,HttpSession session){
		String email=(String)session.getAttribute("email");
		//NOT LOGGED IN
		if(email==null){
			ErrorClass errorClazz=new ErrorClass(6,"Please login...");
    		return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		//ROLE - AUTHORIZATION
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN")){
			ErrorClass errorClazz=new ErrorClass(9,"You are not authorized to view the content..");
			return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		blogPostDao.rejectblogPost(blogPost);
		
		Notification notification=new Notification();
		notification.setBlogTitle(blogPost.getBlogTitle());
		notification.setStatus("Rejected");
		notification.setUserToBeNotified(blogPost.getPostedBy().getEmail());
		notification.setRejectionReason(rejectionReason);
		notificationDao.addNotification(notification);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateBlog",method=RequestMethod.PUT)
	public ResponseEntity<?> updateBlogPost(@RequestBody BlogPost blogPost,HttpSession session){
		String email=(String)session.getAttribute("email");
		//NOT LOGGED IN
		if(email==null){
			ErrorClass errorClazz=new ErrorClass(6,"Please login...");
    		return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		if(!(blogPost.getPostedBy().getEmail().equals(email)))
		{
			ErrorClass errorClazz=new ErrorClass(9,"You are not authorized to Update this content..");
			return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		blogPostDao.updateapprovedBlog(blogPost);
		return new ResponseEntity<Void>(HttpStatus.OK);
		}
}
