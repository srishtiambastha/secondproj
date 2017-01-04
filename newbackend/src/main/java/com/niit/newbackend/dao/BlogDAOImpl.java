package com.niit.newbackend.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.newbackend.dao.BlogDAO;
import com.niit.newbackend.model.BlogComment;
import com.niit.newbackend.model.BlogPost;
import com.niit.newbackend.model.Users;
@Repository
public class BlogDAOImpl implements BlogDAO 
{
	@Autowired
private SessionFactory sessionFactory;
	
	@Transactional
	public List<BlogPost> getBlogPosts() {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from BlogPost");
		List<BlogPost> blogPosts=query.list();
		session.close();
		return blogPosts;
	}

	@Transactional
	public BlogPost getBlogPost(int id) {
		Session session=sessionFactory.openSession();
		BlogPost blogPost=(BlogPost)session.get(BlogPost.class, id);
		session.close();
		return blogPost;
	}

	@Transactional
	public BlogPost addBlogPost(Users users, BlogPost blogPost) {
		Session session=sessionFactory.getCurrentSession();
		blogPost.setCreatedBy(users);
		blogPost.setCreatedOn(new Date());
		session.save(blogPost);
		session.flush();
		BlogPost addedBlogPost=(BlogPost)session.get(BlogPost.class, blogPost.getId());
		return addedBlogPost;
	}

	@Transactional
	public BlogPost addBlogPostComment(Users users, BlogComment blogComment) {
		Session session=sessionFactory.openSession();
	 blogComment.setCreatedBy(users);
	 blogComment.setCreatedOn(new Date());
	 BlogPost blogPost=(BlogPost)session.get(BlogPost.class, blogComment.getBlogPost().getId());
			 blogComment.setBlogPost(blogPost);
	 session.merge(blogComment);
	 //session.flush();
	 //session.close();
	 return blogPost;
	 
	}

	@Transactional
	public List<BlogComment> getBlogComments(int blogId) {
		Session session=sessionFactory.openSession();
		BlogPost blogPost=(BlogPost)session.get(BlogPost.class, blogId);
		List<BlogComment> blogComments=blogPost.getComments();
		session.close();
		return blogComments;
	}

}