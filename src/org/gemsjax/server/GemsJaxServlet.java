package org.gemsjax.server;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;



public class GemsJaxServlet extends WebSocketServlet {


	@Override
	protected WebSocket doWebSocketConnect(HttpServletRequest arg0, String arg1) {
		System.out.println("doWebSocketConnect");
		return new ClientSocket();
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException 
	{
		System.out.println("GET");
		
	};

	


	
	public static void main(String[] args) {
		
	     Server server = new Server(8080);
        
	    //SSL    
	     SslSelectChannelConnector sslConnector = new SslSelectChannelConnector();
	        sslConnector.setPort(8443);
	        sslConnector.setKeystore("keystore");
	        sslConnector.setPassword("teamworkpasswordqwe");
	        sslConnector.setKeyPassword("teamworkpasswordqwe");
	        server.addConnector(sslConnector);
	 
	      // TODO remove this extra info
	        // If you want to use only https remove the first Selector
	      for (Connector c: server.getConnectors())
	    	  System.out.println(c);

	        
	        
	        // Add the GemsJaxServlet (WebSocket Communication)
	        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
	        context.setContextPath("/");
	        context.addServlet(new ServletHolder( new GemsJaxServlet()),"/GemsJaxServlet");
	       
	        
	        
	        // The ResourceHandler to handle static web content
	        ResourceHandler resourceHandler = new ResourceHandler();
	        resourceHandler.setDirectoriesListed(true);
	        resourceHandler.setWelcomeFiles(new String[]{ "GemsJax.html" });
	        resourceHandler.setResourceBase("./war/");
	    
	        HandlerList handlers = new HandlerList();
	       
	        handlers.addHandler(context);
	        handlers.addHandler(resourceHandler);
	        
	        server.setHandler(handlers);
	        
	        try {
				server.start();
				server.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	
	}
	
	
	
}
