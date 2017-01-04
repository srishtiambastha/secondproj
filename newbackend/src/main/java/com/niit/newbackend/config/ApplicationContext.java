package com.niit.newbackend.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.newbackend.model.Friend;
import com.niit.newbackend.model.Job;
import com.niit.newbackend.model.FileUpload;
import com.niit.newbackend.model.Users;
import com.niit.newbackend.model.BlogPost;
import com.niit.newbackend.model.BlogComment;



@Configuration
@ComponentScan("com.niit")
@EnableTransactionManagement
public class ApplicationContext 
{
	@Bean(name="dataSource")
	public DataSource getDataSource()
	{
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
		dataSource.setUsername("system");
		dataSource.setPassword("system");
		return dataSource;
	}
	
	private Properties getHibernateProperties()//properties is used to change database through dialect name
	{
		Properties properties =new Properties();
		properties.put("hibernate.show_sql","true");
		properties.put("hibernate.dialect","org.hibernate.dialect.Oracle10gDialect");
		return properties;
	}
	
	@Autowired
	@Bean(name="sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource){
		LocalSessionFactoryBuilder sessionBuilder=new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.addProperties(getHibernateProperties());
		
		//sessionBuilder.setProperties("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		
		sessionBuilder.setProperty(
				"hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		sessionBuilder.setProperty("hibernate.hbm2ddl.auto", "update");
		sessionBuilder.setProperty("hibernate.show_sql", "true");
		sessionBuilder.addAnnotatedClass(Users.class);
		sessionBuilder.addAnnotatedClass(Job.class);
		sessionBuilder.addAnnotatedClass(FileUpload.class);
		sessionBuilder.addAnnotatedClass(Friend.class);
		sessionBuilder.addAnnotatedClass(BlogPost.class);
		sessionBuilder.addAnnotatedClass(BlogComment.class);

		


		//sessionBuilder.addAnnotatedClass(UserDetails.class);
		System.out.println("connected");
		return sessionBuilder.buildSessionFactory();
			
	}
		
	@Autowired
	@Bean(name="transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory)
	{
		HibernateTransactionManager transactionManager=new HibernateTransactionManager(sessionFactory);
		System.out.println("Transaction...");
		return transactionManager;
		
	}
}