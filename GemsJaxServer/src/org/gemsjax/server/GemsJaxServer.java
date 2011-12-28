package org.gemsjax.server;


import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.gemsjax.server.communication.servlet.LiveCommunicationWebSocketServlet;
import org.gemsjax.server.communication.servlet.post.RegistrationServlet;





public class GemsJaxServer {
	
	public static void main(String[] args) {

	     Server server = new Server(8081);
         
	     if (args.length !=1 )
	     {
	    	 System.out.println("Wrong argument count ("+args.length+"): \nThe first parameter is the path to the war folder");
	    	 return;
	     }
	     
	     
	     
	     String warUrl = args[0];
	     if (!warUrl.endsWith(""+File.separatorChar))
	    	 warUrl+=File.separatorChar;
	     
	     System.out.println(warUrl);
	    //SSL    
	    /* SslSelectChannelConnector sslConnector = new SslSelectChannelConnector();
	        sslConnector.setPort(8443);
	        sslConnector.setKeystore("keystore");
	        sslConnector.setPassword("teamworkpasswordqwe");
	        sslConnector.setKeyPassword("teamworkpasswordqwe");
	        
	        server.addConnector(sslConnector);
*/
	      // TODO remove this extra info
	        // If you want to use only https remove the first Selector
	      for (Connector c: server.getConnectors())
	    	  System.out.println(c);


	        // Add the GemsJaxServlet (WebSocket Communication)
	        ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
	        servletContext.setContextPath("/servlets");
	        servletContext.addServlet(new ServletHolder( new LiveCommunicationWebSocketServlet()),"/liveCommunication");
	        servletContext.addServlet(new ServletHolder( new RegistrationServlet()),"/registration");
	        
	        
	        // The ResourceHandler to handle static web content
	        ResourceHandler resourceHandler = new ResourceHandler();
	        resourceHandler.setDirectoriesListed(true);
	        resourceHandler.setWelcomeFiles(new String[]{ "GemsJax.html" });
	        
	        // linux dir  "/home/hannes/GemsJaxWorkspace/GemsJaxClient/war/"
	        resourceHandler.setResourceBase(warUrl);
	        
	        
	        ContextHandler resourceContext = new ContextHandler();
	        resourceContext.setContextPath("/");
	        resourceContext.setHandler(resourceHandler);
	        
	        
	        
	        
	        
	        HandlerCollection handlers = new HandlerCollection();

	        
	        handlers.addHandler(resourceContext);
	        handlers.addHandler(servletContext);

	        server.setHandler(handlers);
	        

	        try {
				server.start();
				server.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
			

	}



}
