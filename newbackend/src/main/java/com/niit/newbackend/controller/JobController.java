package com.niit.newbackend.controller;

import java.util.Date;
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

import com.niit.newbackend.dao.JobDAO;
import com.niit.newbackend.model.Job;
import com.niit.newbackend.model.Users;
import com.niit.newbackend.model.Error;
@RestController
public class JobController
{
	Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
private JobDAO jobDAO;
	
	@Autowired
	Users users;
	
    @RequestMapping(value="/postJob",method=RequestMethod.POST)
	public ResponseEntity<?> postJob(@RequestBody Job job,HttpSession session){
		Users users=(Users)session.getAttribute("user");	
		if(users==null){	
			Error error=new Error(1,"Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
			//   return response object with data [error], status [401]
			// in front end we can get error message by response.data.message
		}
		else{
			logger.debug(users.getUsername());//NullPointerException is user is null
			System.out.println(" Role of User " + users.getRole());
	                job.setPostedOn(new Date());
					jobDAO.postJob(job);
				return new ResponseEntity<Void>(HttpStatus.OK);
			
	}
	
}
    @RequestMapping(value="/getAllJobs",method=RequestMethod.GET)
    public ResponseEntity<?> getAllJobs(HttpSession session){
    	Users users=(Users)session.getAttribute("user");
    	if(users==null){
    		System.out.println("USER is null");
    		Error error=new Error(1,"Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
    	}
    	System.out.println("USER OBJECT " + users.getRole());
    	List<Job> jobs=jobDAO.getAllJobs();
    	return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
    	//response 
    }
}