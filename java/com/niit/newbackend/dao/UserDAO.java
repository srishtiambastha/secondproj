package com.niit.newbackend.dao;
import java.util.List;

import com.niit.newbackend.model.User;

public interface UserDAO 
{
	 public void addUser(User user);
	public User authenticate(User user);
	public void updateUser(User user);
	public User registerUser(User user);
	public List<User> userList();
	
	public List<User> getAllUser(User user);
}
 