package com.chat.rest.controllers;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.model.Config;
import com.chat.model.Message;
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
	
	
	@PostMapping("/message")
	public ResponseEntity<Void> createMessage(@RequestBody Message msg) {
		System.out.println("Message " + msg);
		serviceManager.addMessage(msg);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@GetMapping("/messages")
	public ResponseEntity<List<Message>> listAllMessages() {
		List<Message> list = serviceManager.getMessages();
		System.out.println("messages " + list);
		return new ResponseEntity<List<Message>>(list, HttpStatus.OK);
	}
	
}
