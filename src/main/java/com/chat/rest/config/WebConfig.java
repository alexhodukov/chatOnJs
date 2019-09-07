package com.chat.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com.chat.rest.controllers")
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("swagger-ui.html")
	      .addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/");
//		registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
//		registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
	}

}
