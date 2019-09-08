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
	private Map<Integer, Queue<Message>> messagesToUsers;
	private Map<Integer, Queue<Message>> messagesToAdmin;
	private static AtomicInteger incIdUser = new AtomicInteger(1); 
	
	public ServiceManager() {
		this.users = Collections.synchronizedMap(new HashMap<>());
		this.messagesToUsers = Collections.synchronizedMap(new HashMap<>());
		this.messagesToAdmin = Collections.synchronizedMap(new HashMap<>());
	}
	
	public void addUser(User user) {
		users.put(user.getId(), user);
		Queue<Message> messages = new LinkedList<>();
		messagesToUsers.put(user.getId(), messages);
		messages = new LinkedList<>();
		messagesToAdmin.put(user.getId(), messages);
	}
	
	public void addMessageForUser(Message msg) {
		Queue<Message> que = messagesToUsers.get(msg.getTo());
		if (que != null) {
			synchronized (que) {
				if (que != null) {
					que.add(msg);
					System.out.println("AddMesageForUser.que " + que);
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
	
	public Queue<Message> getNewMessages(int id) {
		Queue<Message> que;
		if (id == 1) {
			que = messagesToAdmin.get(id);
		} else {
			que = messagesToUsers.get(id);	
		}
		System.out.println("que " + que);
		Queue<Message> queResult = new LinkedList<>();
		if (que != null) {
			synchronized (que) {
				if (que != null) {
					if (que.isEmpty()) {
						try {
							que.wait(timeOutWaiting);
							while (!que.isEmpty()) {
								queResult.add(que.poll());
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						while (!que.isEmpty()) {
							queResult.add(que.poll());
						}
					}
				}
				if (!queResult.isEmpty()) {
					users.get(id).addMessages(queResult);	
				}
			}	
		}	
		return queResult;
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
