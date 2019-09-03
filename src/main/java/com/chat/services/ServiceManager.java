package com.chat.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.chat.model.Config;
import com.chat.model.Message;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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
		
		System.out.println("Config from json " + config);
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
