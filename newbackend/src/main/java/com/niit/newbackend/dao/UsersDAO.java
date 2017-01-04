package com.niit.newbackend.dao;
import java.util.List;

import com.niit.newbackend.model.Users;

public interface UsersDAO 
{
	 public void addUsers(Users users);
	public Users authenticate(Users users);
	public void updateUsers(Users users);
	public Users registerUsers(Users users);
	public List<Users> usersList();
	
	public List<Users> getAllUsers(Users users);
}
