package com.niit.newbackend.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.newbackend.dao.FileUploadDAO;
import com.niit.newbackend.model.FileUpload;
import com.niit.newbackend.model.User;


@Repository("fileUploadDAO")
@EnableTransactionManagement
public class FileUploadDAOImpl implements FileUploadDAO {
	@Autowired
	private SessionFactory sessionFactory;
	public FileUploadDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	@Transactional
	public void save(FileUpload fileUpload) {
		Session session=sessionFactory.getCurrentSession();
		session.save(fileUpload);
		//session.flush();
		//session.close();
	}

	@Transactional
	public FileUpload getFile(String username) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from FileUpload where username=?");
		query.setParameter(0, username);
        FileUpload fileUpload=(FileUpload)query.setMaxResults(1).uniqueResult();
		//session.close();
		return fileUpload;
	}

}