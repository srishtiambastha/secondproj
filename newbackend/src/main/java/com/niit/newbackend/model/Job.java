package com.niit.newbackend.model;

import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
@Entity
@Table(name="Job")
@Component
public class Job {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
private int jobId;
private String jobTitle;
private String jobDescription;
private Date postedOn;
private String skillsRequired;
private String salary;
private String location;
public int getJobId() {
	return jobId;
}
public void setJobId(int jobId) {
	this.jobId = jobId;
}
public String getJobTitle() {
	return jobTitle;
}
public void setJobTitle(String jobTitle) {
	this.jobTitle = jobTitle;
}
public String getJobDescription() {
	return jobDescription;
}
public void setJobDescription(String jobDescription) {
	this.jobDescription = jobDescription;
}
public Date getPostedOn() {
	return postedOn;
}
public void setPostedOn(Date postedOn) {
	this.postedOn = postedOn;
}
public String getSkillsRequired() {
	return skillsRequired;
}
public void setSkillsRequired(String skillsRequired) {
	this.skillsRequired = skillsRequired;
}
public String getSalary() {
	return salary;
}
public void setSalary(String salary) {
	this.salary = salary;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}

}
