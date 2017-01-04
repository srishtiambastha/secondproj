package com.niit.newbackend.controller;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.newbackend.dao.BlogDAO;
import com.niit.newbackend.model.BlogComment;
import com.niit.newbackend.model.BlogPost;
import com.niit.newbackend.model.Users;


import com.niit.newbackend.model.Error;
@RestController
@RequestMapping("/blog")
public class BlogController {
	@Autowired
private BlogDAO blogDAO;
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public ResponseEntity<?> getBlogList(HttpSession session){
		Users users=(Users)session.getAttribute("user");
		if(users==null){
			Error error=new Error(1,"Unauthroized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<BlogPost> blogPosts=blogDAO.getBlogPosts();
		return new ResponseEntity<List<BlogPost>>(blogPosts,HttpStatus.OK);
	}
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getBlogPost(@PathVariable(value="id") int id,HttpSession session){
		Users users=(Users)session.getAttribute("user");
		if(users==null){
			Error error=new Error(1,"Unauthroized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		BlogPost blogPost=blogDAO.getBlogPost(id);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addBlogPost( @RequestBody BlogPost blogPost,HttpSession session) {
		Users users=(Users)session.getAttribute("user");
		if(users==null){
			Error error=new Error(1,"Unauthroized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
         BlogPost addedBlogPost= blogDAO.addBlogPost(users, blogPost);
         return new ResponseEntity<BlogPost>(addedBlogPost,HttpStatus.OK);
    }
	@RequestMapping(value="/getcomments/{blogId}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogComments(@PathVariable(value="blogId")int blogId,HttpSession session){
		Users users=(Users)session.getAttribute("user");
		if(users==null){
			Error error=new Error(1,"Unauthroized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<BlogComment> blogComments=blogDAO.getBlogComments(blogId);
		System.out.println("BLOGCOMMENTS::: " + blogComments.size() );
		return new ResponseEntity<List<BlogComment>>(blogComments,HttpStatus.OK);		
	}
	
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResponseEntity<?> addBlogComment( @RequestBody BlogComment blogComment,HttpSession session) {
    	Users users=(Users)session.getAttribute("user");
		if(users==null){
			Error error=new Error(1,"Unauthroized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		System.out.println("BLOG COMMENT is " + blogComment);
		System.out.println("BLOG COMMENT BODY " + blogComment.getBody());
		
		System.out.println("BLOG POST FROM BLOGCOMMENT " + blogComment.getBlogPost());
		BlogPost blogPost=blogDAO.getBlogPost(blogComment.getBlogPost().getId());
		if(blogPost==null){
			Error error=new Error(2,"Blogpost not found");
			return new ResponseEntity<Error>(error,HttpStatus.NOT_FOUND);
		}
        BlogPost createdBlogPost= blogDAO.addBlogPostComment(users, blogComment);
        return new ResponseEntity<BlogPost>(createdBlogPost,HttpStatus.OK);
    }
	
}