package com.chat.services;

import java.util.ArrayList;
import java.util.List;

import com.chat.model.Message;

public class ServiceManager {
private List<Message> messages;
	
	public ServiceManager() {
		this.messages = new ArrayList<>();
	}
	
	public void addMessage(Message msg) {
		messages.add(msg);
	}
	
	public List<Message> getMessages() {
		return messages;
	}
}
