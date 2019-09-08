package com.chat.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
		System.out.println("ControllerServlet.init()");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
//		serviceManager = server.getHttpMsgHandler();
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		
		resp.setContentType("application/json");
		PrintWriter out;

		try {	
			out = resp.getWriter();
			List<Message> listMsg = new ArrayList<>(serviceManager.getNewMessages(id));
			System.out.println("listMsg " + listMsg);
			ObjectMapper map = new ObjectMapper();
			String json = map.writeValueAsString(listMsg);
			out.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//		AsyncContext async = req.startAsync();
//		async.getResponse().setContentType("application/json");
//		Runnable r = () -> {
//			PrintWriter out;
//			try {	
//				out = async.getResponse().getWriter();
//				List<Message> listMsg = new ArrayList<>(httpMsgHandler.getMessages(id));
//
//				for (Message m : listMsg) {
//					m.convertToWeb();
//				}
//				ObjectMapper map = new ObjectMapper();
//				String json = map.writeValueAsString(listMsg);
//				out.write(json);
//			} catch (IOException e) {
//				logger.warn("Something happened with writer response", e);
//			}
//			async.complete();
//		};
//		
//		Thread t = new Thread(r);
//		t.setName("Thread async servlet");
//		ExecutorService execAsync = Executors.newCachedThreadPool();
//		execAsync.execute(t);
	}
}