package com.niit.Models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="jobs")
public class Job 
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE1")
	@SequenceGenerator(name="SEQUENCE1", sequenceName="SEQUENCE1", allocationSize=1)
private int id;
@Column(nullable=false)
private String jobTitle;
private String skillsrequired;
private String description;
private Date postedOn;
private String location;
private String companyname;
private String exp;
private String salary;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getJobTitle() {
	return jobTitle;
}
public void setJobTitle(String jobTitle) {
	this.jobTitle = jobTitle;
}
public String getSkillsrequired() {
	return skillsrequired;
}
public void setSkillsrequired(String skillsrequired) {
	this.skillsrequired = skillsrequired;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public Date getPostedOn() {
	return postedOn;
}
public void setPostedOn(Date postedOn) {
	this.postedOn = postedOn;
}

public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getCompanyname() {
	return companyname;
}
public void setCompanyname(String companyname) {
	this.companyname = companyname;
}

public String getExp() {
	return exp;
}
public void setExp(String exp) {
	this.exp = exp;
}
public String getSalary() {
	return salary;
}
public void setSalary(String salary) {
	this.salary = salary;
}

}
