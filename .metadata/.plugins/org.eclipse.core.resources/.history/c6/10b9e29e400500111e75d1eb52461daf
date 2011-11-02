package org.gemsjax.server;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;



public class CollaborationWebSocketServlet extends WebSocketServlet {


	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest arg0, String arg1) {
		System.out.println("doWebSocketConnect");
		return new ClientSocket();
	}
	
  protected void doGet(HttpServletRequest request, HttpServletResponse response)   throws ServletException ,IOException 
  {
	  ServletContext con = getServletContext();
      RequestDispatcher dp = getServletContext().getNamedDispatcher("default");
      
      dp.forward(request,response);
  }

}
