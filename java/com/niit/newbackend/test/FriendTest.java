package com.niit.newbackend.test;

import java.util.Date;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.newbackend.dao.FriendDAO;
import com.niit.newbackend.model.Friend;

public class FriendTest 
{
	 
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();
		
		
	    FriendDAO friendDAO= (FriendDAO) context.getBean("friendDAO");

			Friend friend=(Friend) context.getBean("friend");
					

					/*
					friend.setFromId("123");
					friend.setStatus('p');
					friend.setToId("student");
				
					friendDAO.sendFriendRequest("vinay", "rahul");*/
				//friendDAO.updatePendingRequest('A', "vinay", "rahul");
					
					System.out.println(friendDAO.getAllFriends("vinay"));

	}

}