package com.robot.autorobot.chatmessage;

import java.util.Date;

public class ChatMessage {

	private String msg;
	private Type type;
	private Date date;
	
	
	public ChatMessage() {
		super();
	}
	public ChatMessage(String msg, Type type, Date date) {
		super();
		this.msg = msg;
		this.type = type;
		this.date = date;
	}
	public enum Type{
		IN,OUT
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
