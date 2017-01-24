package com.niit.newbackend.dao;


import com.niit.newbackend.model.FileUpload;

public interface FileUploadDAO {
	void save(FileUpload fileUpload);
	FileUpload getFile(String username);
}