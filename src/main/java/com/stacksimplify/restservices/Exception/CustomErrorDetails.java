package com.stacksimplify.restservices.Exception;

import java.util.Date;

public class CustomErrorDetails {

	private Date date;
	private String message;
	private String errordetails;
	
	//Fields Constructor
	public CustomErrorDetails(Date date, String message, String errordetails) {
		super();
		this.date = date;
		this.message = message;
		this.errordetails = errordetails;
	}
	
	//Getter and Setter
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrordetails() {
		return errordetails;
	}
	public void setErrordetails(String errordetails) {
		this.errordetails = errordetails;
	}
	
	
}
