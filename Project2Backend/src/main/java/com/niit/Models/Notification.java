package com.niit.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Notification
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE1")
	@SequenceGenerator(name="SEQUENCE1", sequenceName="SEQUENCE1", allocationSize=1)
private int notificationId;
private String blogTitle;
private String status;
private String userToBeNotified;
private boolean viewed;
private String rejectionReason;
public int getNotificationId() {
	return notificationId;
}
public void setNotificationId(int notificationId) {
	this.notificationId = notificationId;
}
public String getBlogTitle() {
	return blogTitle;
}
public void setBlogTitle(String blogTitle) {
	this.blogTitle = blogTitle;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getUserToBeNotified() {
	return userToBeNotified;
}
public void setUserToBeNotified(String userToBeNotified) {
	this.userToBeNotified = userToBeNotified;
}
public boolean isViewed() {
	return viewed;
}
public void setViewed(boolean viewed) {
	this.viewed = viewed;
}
public String getRejectionReason() {
	return rejectionReason;
}
public void setRejectionReason(String rejectionReason) {
	this.rejectionReason = rejectionReason;
}

}
