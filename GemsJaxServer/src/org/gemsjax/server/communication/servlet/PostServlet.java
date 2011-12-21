package org.gemsjax.server.communication.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is a Servlet that only accepts POST requests, 
 * so you need to override the {@link #doPost(HttpServletRequest, HttpServletResponse)} method
 * @author Hannes Dorfmann
 *
 */
public abstract class PostServlet extends HttpServlet{
	
	public abstract void doPost(HttpServletRequest request,  HttpServletResponse response)   throws ServletException, IOException;
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException
	{
		resp.setContentType("text/html");
		resp.setStatus(403);
	    PrintWriter out = resp.getWriter();
	    
	    out.println("Sorry, the DELETE operation is not allowed. This servlet only accepts POST");
	    out.close();
	    
	}
	
	
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException
	{
		resp.setContentType("text/html");
		resp.setStatus(403);
	    PrintWriter out = resp.getWriter();
	    
	    out.println("Sorry, the OPTIONS operation is not allowed. This servlet only accepts POST");
	    out.close();	
	}
	
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException
	{
		resp.setContentType("text/html");
		resp.setStatus(403);
	    PrintWriter out = resp.getWriter();
	    
	    out.println("Sorry, the PUT operation is not allowed. This servlet only accepts POST");
	    out.close();	
	}

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException
	{
		resp.setContentType("text/html");
		resp.setStatus(403);
	    PrintWriter out = resp.getWriter();
	    
	    out.println("Sorry, the GET operation is not allowed. This servlet only accepts POST");
	    out.close();	
	}
	
	
}

