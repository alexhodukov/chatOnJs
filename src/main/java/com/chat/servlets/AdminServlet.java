package com.chat.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.chat.model.User;
import com.chat.services.ServiceManager;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdminServlet extends HttpServlet{
	@Autowired
	private ServiceManager serviceManager;
	
	@Override
	public void init() throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		PrintWriter out;

		try {	
			out = resp.getWriter();
//			User user = serviceManager.getNewUser();
//			ObjectMapper map = new ObjectMapper();
//			String json = map.writeValueAsString(user);
//			out.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
