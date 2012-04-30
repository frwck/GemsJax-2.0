package org.gemsjax.server.communication.servlet;



import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;



public class LiveCommunicationWebSocketServlet extends WebSocketServlet {


	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request, String arg1) {
		System.out.println("WebSocket "+request.getSession(true).getId());
		return new LiveWebSocketConnection(request.getSession());
	}
	
  protected void doGet(HttpServletRequest request, HttpServletResponse response)   throws ServletException ,IOException 
  {
	  ServletContext con = getServletContext();
      RequestDispatcher dp = getServletContext().getNamedDispatcher("default");
      System.out.println("Websocket GET:"+request.getSession(true).getId());
      dp.forward(request,response);
  }

}
