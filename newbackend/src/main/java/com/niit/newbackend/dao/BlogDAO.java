package com.niit.newbackend.dao;

import java.util.List;

import com.niit.newbackend.model.BlogComment;
import com.niit.newbackend.model.BlogPost;
import com.niit.newbackend.model.Users;

public interface BlogDAO {
	List<BlogPost> getBlogPosts();
	BlogPost getBlogPost(int id);
	BlogPost addBlogPost(Users users,BlogPost blogPost);
	List<BlogComment> getBlogComments(int blogId);
	BlogPost addBlogPostComment(Users users,BlogComment blogComment);
}