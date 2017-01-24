package com.niit.newbackend.dao;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.newbackend.dao.UserDAO;
import com.niit.newbackend.model.User;



@EnableTransactionManagement
@Repository("userDAO")
public class UserDAOImpl implements UserDAO
{
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	   public void addUser(User user)
	   {
		  logger.debug("Entering to addUser.....");
		  sessionFactory.getCurrentSession().saveOrUpdate(user);
	   }
	
	@Transactional
	public User authenticate(User user) {
		logger.debug("USERDAOIMPL :: AUTHENTICATE");
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery(
		"from User where username=?  and password=?");
		//select * from User where username='smith' and password='123'
		query.setString(0, user.getUsername());
		query.setString(1, user.getPassword());
		User validUser=(User)query.uniqueResult();
		//session.flush();
		//session.close();
		if(validUser!=null)
		logger.debug("VALID USER IS  " + validUser.getUsername() + " " + validUser.getRole() + " " + validUser.isOnline());;
		if(validUser==null)
			logger.debug("Valid USER is null");
		return validUser;
		
	}
	
	@Transactional
	public void updateUser(User user) {
		logger.debug("USERDAOIMPL::UPDATE");
		logger.debug("ISONLINE VALUE IS [BEFORE UPDATE]" + user.isOnline());
		Session session=sessionFactory.getCurrentSession();
		User existingUser=(User)session.get(User.class, user.getId());
		//update online status as true
		existingUser.setOnline(user.isOnline()); 
		
		session.update(existingUser);
		//session.flush();
		//session.close();
		logger.debug("ISONLINE VALUE IS [AFTER UPDATE] " + existingUser.isOnline());
	}
	
	@Transactional
	public User registerUser(User user) {
		logger.debug("USERDAOIMPL - registerUser");
		Session session=sessionFactory.getCurrentSession();
		session.save(user);
		//session.flush();
		//session.close();
		logger.debug("User id in Dao " + user.getId());
		return user;
		
			}
	
	@Transactional
	public List<User> userList()
	{
		List<User> list= (List<User>) sessionFactory.getCurrentSession().createCriteria(User.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return list;
				
	}
	
     @Transactional
	public List<User> getAllUser(User user) {
		Session session=sessionFactory.openSession();
		SQLQuery query=session.createSQLQuery("select * from users where username in (select username from users where username!=? minus(select to_id from friend where from_id=? union select from_id from friend where to_id=?))");
		query.setString(0, user.getUsername());
		query.setString(1, user.getUsername());
		query.setString(2, user.getUsername());
		query.addEntity(User.class);
		List<User> userList=query.list();
		System.out.println(userList);
		session.close();
		return userList;
	}
	
	
}