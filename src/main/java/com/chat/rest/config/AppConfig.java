package com.chat.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.chat.services.ServiceManager;

@ComponentScan("com.chat")
@Configuration
public class AppConfig {
	
	@Bean
	public ServiceManager serviceManager() {
		return new ServiceManager();
	}

}
