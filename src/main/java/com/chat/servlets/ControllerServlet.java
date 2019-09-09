package com.chat.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.chat.model.Message;
import com.chat.services.ServiceManager;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ControllerServlet extends HttpServlet {
	
	@Autowired
	private ServiceManager serviceManager;
	
	@Override
	public void init() throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		
		resp.setContentType("application/json");
		PrintWriter out;

		try {	
			out = resp.getWriter();
			Message message = serviceManager.getNewMessage(id);
			ObjectMapper map = new ObjectMapper();
			String json = map.writeValueAsString(message);
			out.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}