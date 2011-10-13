package org.gemsjax.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;



public class GemsJaxServer {
	
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
	        context.addServlet(new ServletHolder( new CollaborationServlet()),"/GemsJaxServlet");



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
