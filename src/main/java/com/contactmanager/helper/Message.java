package com.contactmanager.helper;

public class Message {
	private String body;
	private String type;
	@Override
	public String toString() {
		return "Message [body=" + body + ", type=" + type + "]";
	}
	public Message(String body, String type) {
		super();
		this.body = body;
		this.type = type;
	}
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
