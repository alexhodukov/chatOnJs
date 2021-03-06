package com.chat.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import com.chat.model.Config;
import com.chat.model.Message;
import com.chat.model.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ServiceManager {
	private static final int timeOutWaiting = 10_000;
	private Map<Integer, User> users;
	private Queue<User> newUsers;
	private Map<Integer, Queue<Message>> messagesToUsers;
	private Map<Integer, Queue<Message>> messagesToAdmin;
	private static AtomicInteger incIdUser = new AtomicInteger(1); 
	
	public ServiceManager() {
		this.users = Collections.synchronizedMap(new HashMap<>());
		this.messagesToUsers = Collections.synchronizedMap(new HashMap<>());
		this.messagesToAdmin = Collections.synchronizedMap(new HashMap<>());
		this.messagesToAdmin.put(1, new LinkedList<>());
		this.newUsers = new LinkedList<>();
	}
	
	public void addUser(User user) {
		users.put(user.getId(), user);
		Queue<Message> messages = new LinkedList<>();
		messagesToUsers.put(user.getId(), messages);
//		synchronized (newUsers) {
//			newUsers.add(user);
//			newUsers.notifyAll();
//		}
	}
	
//	public User getNewUser() {
//		User user = null;
//		synchronized (newUsers) {
//			if (newUsers.isEmpty()) {
//				try {
//					newUsers.wait(timeOutWaiting);
//					if (!newUsers.isEmpty()) {
//						
//						user = newUsers.poll();
//					}
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			} else {
//				if (!newUsers.isEmpty()) {
//					user = newUsers.poll();
//				}
//			}
//		}
//		return user;
//	}
	
	public void addMessageForUser(Message msg) {
		Queue<Message> que = messagesToUsers.get(msg.getTo());
		if (que != null) {
			synchronized (que) {
				if (que != null) {
					que.add(msg);
					que.notifyAll();
					
				}
			}	
		}	
	}
	
	public void addMessageForAdmin(Message msg) {
		Queue<Message> que = messagesToAdmin.get(msg.getTo());
		if (que != null) {
			synchronized (que) {
				if (que != null) {
					que.add(msg);
					que.notifyAll();
				}
			}	
		}
	}
	
	public Message getNewMessage(int id) {
		Queue<Message> que;
		if (id == 1) {
			que = messagesToAdmin.get(id);
		} else {
			que = messagesToUsers.get(id);	
		}
		Message message = null;
		if (que != null) {
			synchronized (que) {
				if (que != null) {
					if (que.isEmpty()) {
						try {
							que.wait(timeOutWaiting);
							if (!que.isEmpty()) {
								message = que.poll();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						if (!que.isEmpty()) {
							message = que.poll();
						}
					}
				}
				if (id == 1 && message != null) {
					users.get(message.getFromId()).addMessage(message);
				} else if (id > 1 && message != null) {
					users.get(id).addMessage(message);	
				}
			}	
		}	
		return message;
	}
	
	public Queue<Message> getCorrespondence(int id) {
		return users.get(id).getMessages();
	}
	
	public List<User> getUsers() {
		return new ArrayList<User>(users.values());
	}
	
	public static int generateIdUser() {
		return incIdUser.incrementAndGet();
	}
	
	public Config getConfig() {
		byte[] jsonData;
		ObjectMapper objectMapper = new ObjectMapper();
		Config config = null;
		
		try {
			jsonData = Files.readAllBytes(Paths.get("config.cf"));
			config = objectMapper.readValue(jsonData, Config.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return config;
	}
	
	public void setConfig(Config config) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		
		try (PrintWriter writer = new PrintWriter("config.cf")) {
			objectMapper.writeValue(writer, config);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}			
