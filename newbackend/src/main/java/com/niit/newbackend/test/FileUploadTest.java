/*package com.niit.newbackend.test;

import java.util.Date;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.newbackend.dao.FileUploadDAO;
import com.niit.newbackend.model.FileUpload;


public class FileUploadTest
{
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();
		
		
		
		FileUploadDAO fileUploadDAO= (FileUploadDAO) context.getBean("fileUploadDAO");
		FileUpload fileUpload=(FileUpload) context.getBean("fileUpload");
		
		fileUpload.setFileName("arun");
		fileUpload.setUsername("arun123");
		String s="image";
		byte b[]=s.getBytes();
		fileUpload.setData(b);
	
		fileUploadDAO.save(fileUpload);
	}

}
*/