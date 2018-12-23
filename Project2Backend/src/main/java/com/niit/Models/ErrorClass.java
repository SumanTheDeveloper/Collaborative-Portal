package com.niit.Models;

public class ErrorClass 
{
 private int errorcode;
 private String message;
public int getErrorcode() {
	return errorcode;
}
public void setErrorcode(int errorcode) {
	this.errorcode = errorcode;
}
public String getmessage() {
	return message;
}
public void setmessage(String message) {
	this.message = message;
}
public ErrorClass(int errorcode, String message) {
	super();
	this.errorcode = errorcode;
	this.message = message;
}
 
}
