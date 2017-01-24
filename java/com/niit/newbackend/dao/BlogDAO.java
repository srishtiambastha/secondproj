package com.niit.newbackend.dao;

import java.util.List;

import com.niit.newbackend.model.BlogComment;
import com.niit.newbackend.model.BlogPost;
import com.niit.newbackend.model.User;

public interface BlogDAO
{
	List<BlogPost> getBlogPosts();
	BlogPost getBlogPost(int id);
	BlogPost addBlogPost(User users,BlogPost blogPost);
	List<BlogComment> getBlogComments(int blogId);
	BlogPost addBlogPostComment(User users,BlogComment blogComment);
}