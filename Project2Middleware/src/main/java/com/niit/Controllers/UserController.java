package com.niit.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.Dao.UserDao;
import com.niit.Models.ErrorClass;
import com.niit.Models.User;

@RestController  
public class UserController
{
	@Autowired
	private UserDao userDao;
		
		public UserController()
		{
			System.out.println("userController bean is created");
		}
		
		@RequestMapping(value="/register",method=RequestMethod.POST)
		public   ResponseEntity<?>  registration(@RequestBody User user)
		{
			if(!userDao.isEmailValid(user.getEmail()))
			{
				ErrorClass errorClass=new ErrorClass(2,"Email already exists.. please choose different email id");
				return new ResponseEntity<ErrorClass>(errorClass,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			try{
			userDao.registerUser(user);
			}
			catch(Exception e)
			{
				ErrorClass errorClass=new ErrorClass(1,"Unable to register user details.."+e.getMessage());
				return new ResponseEntity<ErrorClass>(errorClass,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		@RequestMapping(value="/login",method=RequestMethod.PUT)
		public ResponseEntity<?> login(@RequestBody User user,HttpSession session)
		{
			User validUser=userDao.login(user);
			if(validUser==null)
			{
				ErrorClass errorClass=new ErrorClass(5,"Email or password is incorrect..please enter valid credentials..");
				return new ResponseEntity<ErrorClass>(errorClass,HttpStatus.UNAUTHORIZED);
			}
			session.setAttribute("email", user.getEmail());
			
			validUser.setOnline(true);
			userDao.update(validUser);
			return new ResponseEntity<User>(validUser,HttpStatus.OK);
		}
		
		@RequestMapping(value="/logout",method=RequestMethod.PUT)
	    public ResponseEntity<?> logout(HttpSession session)
		{	
	    	String email=(String)session.getAttribute("email");
	    	if(email==null)
	    	{
	    		ErrorClass errorClass=new ErrorClass(6,"Please login...");
	    		return new ResponseEntity<ErrorClass>(errorClass,HttpStatus.UNAUTHORIZED);
	    	}
	    	User user=userDao.getUser(email);
	    	user.setOnline(false);
	    	userDao.update(user);
	    	session.removeAttribute("email");
	    	session.invalidate();
	    	return new ResponseEntity<Void>(HttpStatus.OK);
	    }
		@RequestMapping(value="/getuser",method=RequestMethod.GET)
		public ResponseEntity<?> getUser(HttpSession session)
		{
			String email=(String)session.getAttribute("email");
			if(email==null)
			{
				ErrorClass errorClass=new ErrorClass(6,"Please login...");
	    		return new ResponseEntity<ErrorClass>(errorClass,HttpStatus.UNAUTHORIZED);
			}
			User user=userDao.getUser(email);
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		
		@RequestMapping(value="/updateprofile",method=RequestMethod.PUT)
		public ResponseEntity<?> updateUserProfile(@RequestBody User user,HttpSession session)
		{
			String email=(String)session.getAttribute("email");
			if(email==null){
				ErrorClass errorClass=new ErrorClass(6,"Please login...");
	    		return new ResponseEntity<ErrorClass>(errorClass,HttpStatus.UNAUTHORIZED);
			}
			try{
			userDao.update(user);
			}
			catch(Exception e)
			{
				ErrorClass errorClass=new ErrorClass(7,"Unable to update user details.."+e.getMessage());
				return new ResponseEntity<ErrorClass>(errorClass,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
}
