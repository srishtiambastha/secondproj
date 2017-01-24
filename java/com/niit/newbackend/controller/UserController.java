package com.niit.newbackend.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.newbackend.dao.FileUploadDAO;
import com.niit.newbackend.dao.UserDAO;
import com.niit.newbackend.model.User;
import com.niit.newbackend.model.Error;
import com.niit.newbackend.model.FileUpload;
@RestController
public class UserController 
{
	Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private FileUploadDAO fileUploadDAO;
	
	
	@RequestMapping("/")
	public String getLanding()
	{
		System.out.println("Landing Page.....");
		return "index";
	}

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody User user,HttpSession session ){
		logger.debug("Entering USERCONTROLLER : LOGIN");
		logger.debug("USERNAME:" + user.getUsername() + " PASSWORD " + user.getPassword() );
		User validUser=userDAO.authenticate(user);
		if(validUser==null){
			logger.debug("validUser is null");
			Error error=new Error(1,"Username and password doesnt exists...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED); //401
		}
		else{
			//key [user] value [validUser- an object of type User]
			//user = [james,123,james@xyz.com,student,true,true]
			session.setAttribute("user", validUser);
			validUser.setOnline(true);
			userDAO.updateUser(validUser); // to update online status in db
			logger.debug("validUser is not null");
			
			 //select * from proj2_profile_pics where username='adam';
			  FileUpload getUploadFile=fileUploadDAO.getFile(user.getUsername());
			  if(getUploadFile!=null){
		  	String name=getUploadFile.getFileName();
		  	System.out.println(getUploadFile.getData());
		  	byte[] imagefiles=getUploadFile.getData();
		  	try{
		  		String path="D:/proj3/newbackend/src/main/webapp/WEB-INF/resources/image/"+user.getUsername();
		  		File file=new File(path);
		  		//file.mkdirs();
		  		FileOutputStream fos = new FileOutputStream(file);//to Write some data 
		  		fos.write(imagefiles);
		  		fos.close();
		  		}catch(Exception e){
		  		e.printStackTrace();
		  		}
			  }
			
			return new ResponseEntity<User>(validUser,HttpStatus.OK);//200
		}
	}

	//'?'  - Any Type [User,Error] 
	//ENDPOINT : http://localhost:8080/proj2backend/register 
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user){
		//client will send only username, password, email, role 
		try{
		logger.debug("USERCONTROLLER=>REGISTER " + user);
		user.setStatus(true);
		user.setOnline(false);
		User savedUser=userDAO.registerUser(user);
		logger.debug("User Id generated is " + savedUser.getId());
		if(savedUser.getId()==0){
			Error error=new Error(2,"Couldnt insert user details ");
			return new ResponseEntity<Error>(error , HttpStatus.CONFLICT);
		}
		else
			return new ResponseEntity<User>(savedUser,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			Error error=new Error(2,"Couldnt insert user details. Cannot have null/duplicate values " + e.getMessage());
			return new ResponseEntity<Error>(error , HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value="/logout",method=RequestMethod.PUT)
	public ResponseEntity<?> logout(HttpSession session){
		User user=(User)session.getAttribute("user");
		if(user!=null){
			user.setOnline(false);
			userDAO.updateUser(user);
			try{
	                        //change according to your workspace path and project name
		  		String path="D:/proj3/newbackend/src/main/webapp/WEB-INF/resources/image/"+user.getUsername();
		  		File file=new File(path);
		  		System.out.println(file.delete());
		  		
		  		}catch(Exception e){
		  		e.printStackTrace();
		  		}
		}
		session.removeAttribute("user");
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getUsers",method=RequestMethod.GET)
	public ResponseEntity<?> getAllUser(HttpSession session){
		User user=(User)session.getAttribute("user");
		if(user==null)
		return new	ResponseEntity<Error>(new Error(1,"Unauthorized user"),HttpStatus.UNAUTHORIZED);
		else
		{
			List<User> listUser=userDAO.getAllUser(user);
			for(User u:listUser)
				System.out.println("IsONline " + u.isOnline());
			return new ResponseEntity<List<User>>(listUser,HttpStatus.OK);
		}
	}

}