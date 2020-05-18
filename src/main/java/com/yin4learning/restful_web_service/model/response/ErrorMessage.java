package com.yin4learning.restful_web_service.model.response;

import java.util.Date;

public class ErrorMessage {

	private Date timestamp;
	private String message;
	
	public ErrorMessage() {}
	
	public ErrorMessage(Date date, String message) {
		this.timestamp = timestamp;
		this.message = message;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
