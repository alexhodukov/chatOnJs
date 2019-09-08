package com.chat.rest.controllers;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.model.Config;
import com.chat.model.Message;
import com.chat.model.User;
import com.chat.model.UserDto;
import com.chat.services.ServiceManager;

@RestController
@RequestMapping("/chat")
public class ChatController {
	
	private ServiceManager serviceManager;
	
	public ChatController(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}
	
	@PostMapping("/config")
	public ResponseEntity<Void> createConfig(@RequestBody Config config) {
		System.out.println("Set Config " + config);
//		config = new Config();
//		config.setTitle("chat");
//		config.setBotName("vasya");
//		config.setCssClass("1.csw");
//		config.setChatUrl("http:");
//		config.setPosition("left");
//		config.setAllowToMinimaize(false);
//		config.setAllowToDrag(false);
//		config.setRequireName(false);
//		config.setShowDateTime(true);
//		config.setNetwork("fetch");
		serviceManager.setConfig(config);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@GetMapping("/config")
	public ResponseEntity<Config> getConfig() {
		Config config = serviceManager.getConfig();
		System.out.println("Get Config " + config);
		return new ResponseEntity<Config>(config, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Integer> registerUser(@Valid @RequestBody UserDto userDto) {
		int id = ServiceManager.generateIdUser();
		User user = new User(id, userDto.getName());
		if ("User".equals(user.getName())) {
			user.setName(user.getName() + id);
		}
		serviceManager.addUser(user);
		System.out.println("Register User " + user);
		return new ResponseEntity<Integer>(id, HttpStatus.CREATED);
	}
	
	@PostMapping("/sendMessage")
	public ResponseEntity<Void> sendMessage(@RequestBody Message message) {
		int id = message.getTo();
		if (id == 1) {
			serviceManager.addMessageForAdmin(message);
		} else {
			serviceManager.addMessageForUser(message);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@GetMapping("/messages/{id}")
	public ResponseEntity<List<Message>> getCorrespondence(@PathVariable("id") Integer id) {
		List<Message> list = new ArrayList<>(serviceManager.getCorrespondence(id));
		System.out.println("Correspondence " + list);
		return new ResponseEntity<List<Message>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		List<User> list = serviceManager.getUsers();
		System.out.println("Users " + list);
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	
}
