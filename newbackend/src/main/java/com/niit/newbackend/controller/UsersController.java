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
import com.niit.newbackend.dao.UsersDAO;
import com.niit.newbackend.model.Users;
import com.niit.newbackend.model.Error;
import com.niit.newbackend.model.FileUpload;
@RestController
public class UsersController 
{
	Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private FileUploadDAO fileUploadDAO;

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody Users users,HttpSession session ){
		logger.debug("Entering USERCONTROLLER : LOGIN");
		logger.debug("USERNAME:" + users.getUsername() + " PASSWORD " + users.getPassword() );
		Users validUser=usersDAO.authenticate(users);
		if(validUser==null){
			logger.debug("validUser is null");
			Error error=new Error(1,"Username and password doesnt exists...");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED); //401
		}
		else{
			//key [user] value [validUser- an object of type User]
			//user = [james,123,james@xyz.com,student,true,true]
			session.setAttribute("users", validUser);
			validUser.setOnline(true);
			usersDAO.updateUsers(validUser); // to update online status in db
			logger.debug("validUser is not null");
			
			 //select * from proj2_profile_pics where username='adam';
			  FileUpload getUploadFile=fileUploadDAO.getFile(users.getUsername());
			  if(getUploadFile!=null){
		  	String name=getUploadFile.getFileName();
		  	System.out.println(getUploadFile.getData());
		  	byte[] imagefiles=getUploadFile.getData();
		  	try{
		  		String path="D:/proj3/newbackend/src/main/webapp/WEB-INF/resources/image/"+users.getUsername();
		  		File file=new File(path);
		  		//file.mkdirs();
		  		FileOutputStream fos = new FileOutputStream(file);//to Write some data 
		  		fos.write(imagefiles);
		  		fos.close();
		  		}catch(Exception e){
		  		e.printStackTrace();
		  		}
			  }
			
			return new ResponseEntity<Users>(validUser,HttpStatus.OK);//200
		}
	}

	//'?'  - Any Type [User,Error] 
	//ENDPOINT : http://localhost:8080/proj2backend/register 
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseEntity<?> registerUsers(@RequestBody Users users){
		//client will send only username, password, email, role 
		try{
		logger.debug("USERCONTROLLER=>REGISTER " + users);
		users.setStatus(true);
		users.setOnline(false);
		Users savedUser=usersDAO.registerUsers(users);
		logger.debug("User Id generated is " + savedUser.getId());
		if(savedUser.getId()==0){
			Error error=new Error(2,"Couldnt insert user details ");
			return new ResponseEntity<Error>(error , HttpStatus.CONFLICT);
		}
		else
			return new ResponseEntity<Users>(savedUser,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			Error error=new Error(2,"Couldnt insert user details. Cannot have null/duplicate values " + e.getMessage());
			return new ResponseEntity<Error>(error , HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value="/logout",method=RequestMethod.PUT)
	public ResponseEntity<?> logout(HttpSession session){
		Users users=(Users)session.getAttribute("user");
		if(users!=null){
			users.setOnline(false);
			usersDAO.updateUsers(users);
			try{
	                        //change according to your workspace path and project name
		  		String path="D:/proj3/newbackend/src/main/webapp/WEB-INF/resources/image/"+users.getUsername();
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
	public ResponseEntity<?> getAllUsers(HttpSession session){
		Users users=(Users)session.getAttribute("user");
		if(users==null)
		return new	ResponseEntity<Error>(new Error(1,"Unauthorized user"),HttpStatus.UNAUTHORIZED);
		else
		{
			List<Users> users1=usersDAO.getAllUsers(users);
			for(Users u:users1)
				System.out.println("IsONline " + u.isOnline());
			return new ResponseEntity<List<Users>>(users1,HttpStatus.OK);
		}
	}

}