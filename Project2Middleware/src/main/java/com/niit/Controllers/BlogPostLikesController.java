package com.niit.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.Dao.BlogPostLikesDao;
import com.niit.Models.BlogPost;
import com.niit.Models.BlogPostLikes;
import com.niit.Models.ErrorClass;

@Controller
public class BlogPostLikesController 
{
	@Autowired
	private BlogPostLikesDao blogPostLikesDao;
		@RequestMapping(value="/hasuserlikedblogpost/{blogPostId}",method=RequestMethod.GET)
	public ResponseEntity<?> hasUserLikedBlogPost(@PathVariable int blogPostId,HttpSession session){
		String email=(String)session.getAttribute("email");
		//NOT LOGGED IN
		if(email==null){
			ErrorClass errorClazz=new ErrorClass(6,"Please login...");
			return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		BlogPostLikes blogPostLikes=blogPostLikesDao.hasUserLikedBlogPost(blogPostId, email);
		return new ResponseEntity<BlogPostLikes>(blogPostLikes,HttpStatus.OK);
		
		
	}
		@RequestMapping(value="/updatelikes/{blogPostId}",method=RequestMethod.PUT)
		public ResponseEntity<?> updateLikes(@PathVariable int blogPostId,HttpSession session){
			String email=(String)session.getAttribute("email");
			//NOT LOGGED IN
			if(email==null){
				ErrorClass errorClazz=new ErrorClass(6,"Please login...");
				return new ResponseEntity<ErrorClass>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
			}
			BlogPost blogPost=blogPostLikesDao.updateLikes(blogPostId,email);
			return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
		}
}
