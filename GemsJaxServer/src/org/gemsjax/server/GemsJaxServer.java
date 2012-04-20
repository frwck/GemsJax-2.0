package org.gemsjax.server;


import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.gemsjax.server.communication.servlet.FileServlet;
import org.gemsjax.server.communication.servlet.LiveCommunicationWebSocketServlet;
import org.gemsjax.server.communication.servlet.post.RegistrationServlet;
import org.gemsjax.server.communication.servlet.post.SearchServlet;



public class GemsJaxServer {
	
	public static void main(String[] args) {


	     if (args.length !=1 )
	     {
	    	 System.out.println("Wrong argument count ("+args.length+"): \nThe first parameter is the path to the war folder");
	    	 return;
	     }

	     System.setProperty("DEBUG", "true");
	     Server server = new Server(8081);
	     


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
	        servletContext.setContextPath("/");
	        servletContext.addServlet(new ServletHolder(new FileServlet(warUrl,"GemsJax.html")),"/*");
	        servletContext.addServlet(new ServletHolder( new LiveCommunicationWebSocketServlet()),"/servlets/liveCommunication");
	        servletContext.addServlet(new ServletHolder( new RegistrationServlet()),"/servlets/registration");
	        servletContext.addServlet(new ServletHolder( new SearchServlet()),"/servlets/search");
	        


	        server.setHandler(servletContext);


	        try {
				server.start();
				server.join();
			} catch (Exception e) {
				e.printStackTrace();
			}


	}


}
