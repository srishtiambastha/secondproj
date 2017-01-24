package com.niit.newbackend.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework	.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.newbackend.dao.FriendDAO;
import com.niit.newbackend.model.Friend;
import com.niit.newbackend.model.User;
import com.niit.newbackend.model.Error;

@RestController
public class FriendController {
	@Autowired
private FriendDAO friendDAO;

	public FriendDAO getFriendDAO() {
		return friendDAO;
	}

	public void setFriendDAO(FriendDAO friendDAO) {
		this.friendDAO = friendDAO;
	}
@RequestMapping(value="/getAllFriends",method=RequestMethod.GET)
	public ResponseEntity<?> getAllFriends(HttpSession session){
		User users=(User)session.getAttribute("user");
		if(users!=null){
		List<Friend> friends=friendDAO.getAllFriends(users.getUsername());
		return new ResponseEntity<List<Friend>>(friends,HttpStatus.OK);
		}
		else
			return new ResponseEntity<Error>(new Error(1,"Unauthorized user"),HttpStatus.UNAUTHORIZED);
	}
@RequestMapping(value="/friendRequest",method=RequestMethod.POST)
public ResponseEntity<?> sendFriendRequest(@RequestBody String username,HttpSession session){
	User users=(User) session.getAttribute("user");
	if(users==null)
		return new ResponseEntity<Error>(new Error(1,"Unauthorized user"),HttpStatus.UNAUTHORIZED);
	else{
		friendDAO.sendFriendRequest(users.getUsername(),username);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
@RequestMapping(value="/pendingRequest",method=RequestMethod.GET)
public ResponseEntity<?> getAllPendingRequest(HttpSession session){
	User users=(User)session.getAttribute("user");
	if(users==null)
		return new ResponseEntity<Error>(new Error(1,"Unauthorized users"),HttpStatus.UNAUTHORIZED);
	else{
		List<Friend> pendingRequest=friendDAO.getPendingRequest(users.getUsername());
		return new ResponseEntity<List<Friend>>(pendingRequest,HttpStatus.OK);
	}
}
@RequestMapping(value="/updateFriendRequest/{friendStatus}/{fromId}",method=RequestMethod.PUT)
public ResponseEntity<?> updatePendingRequest(@PathVariable(value="friendStatus") char friendStatus,
		@PathVariable(value="fromId") String fromId,HttpSession session){
	User users=(User)session.getAttribute("user");
	if(users==null)
		return new ResponseEntity<Error>(new Error(1,"Unauthorized users"),HttpStatus.UNAUTHORIZED);
	else{
		friendDAO.updatePendingRequest(friendStatus,fromId,users.getUsername());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
}