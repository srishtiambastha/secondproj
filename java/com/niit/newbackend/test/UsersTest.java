package com.niit.newbackend.test;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.newbackend.dao.UserDAO;
import com.niit.newbackend.model.User;

public class UsersTest {

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();
		
		
	    UserDAO usersDAO= (UserDAO) context.getBean("usersDAO");

		User users=(User) context.getBean("users");

		//users.setId(9);
//		users.setUsername("vinay");
//		users.setPassword("123");
//		users.setEmail("vinay@gmail.com");
//		users.setRole("employee");
//		users.setStatus(true);
//		users.setOnline(false);
//		
//		usersDAO.registerUsers(users);
		

	}

}
