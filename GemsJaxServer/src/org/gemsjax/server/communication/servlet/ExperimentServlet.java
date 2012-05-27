package org.gemsjax.server.communication.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExperimentServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5105136048287500406L;


	@Override
	public void doGet(HttpServletRequest request,  HttpServletResponse response)   throws ServletException, IOException{
		
		System.out.println(request.getRequestURL());
		
		
	}
	
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException
	{
		System.out.println("Registration "+req.getSession(true).getId());
		
		resp.setContentType("text/html");
		resp.setStatus(403);
	    PrintWriter out = resp.getWriter();
	    
	    out.println("Sorry, the GET operation is not allowed. This servlet only accepts POST");
	    out.close();	
	}

}
