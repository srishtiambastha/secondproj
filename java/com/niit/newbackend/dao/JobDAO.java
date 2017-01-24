package com.niit.newbackend.dao;


import java.util.List;

import com.niit.newbackend.model.Job;
import com.niit.newbackend.model.User;

public interface JobDAO {
public void postJob(Job job);
public List<Job> getAllJobs();
public void addJob(Job job);
}