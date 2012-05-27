package org.gemsjax.server.communication.servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gemsjax.server.persistence.dao.ExperimentDAO;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateExperimentDAO;
import org.gemsjax.shared.ServletPaths;
import org.gemsjax.shared.experiment.ExperimentInvitation;


public class ExperimentServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5105136048287500406L;
	
	private String experimentRegistrationHtmlFile;
	private String experimentHtmlFile;
	
	
	public ExperimentServlet(String warUrl){
		experimentRegistrationHtmlFile = ServletPaths.SERVER_URL+"/ExperimentRegistration.html";
		experimentHtmlFile = ServletPaths.SERVER_URL+"/Experiment.html";
	}

	
	private String getVerificationCode(String url){
		
		if (!url.matches(ServletPaths.SERVER_URL+ServletPaths.EXPERIMENT+"/.*"))
			return null;
		
		
		int index = url.lastIndexOf("/");
		if (index==-1)
			return null;
		
		return url.substring(index+1);
	}
	

	@Override
	public void doGet(HttpServletRequest request,  HttpServletResponse response)   throws ServletException, IOException{
		
		
		
		String verificationCode = getVerificationCode(request.getRequestURL().toString());
		
		if (verificationCode == null){
			response.getWriter().write("Wrong URL. Please only use the URL of your experiment invitation email");
			response.setStatus(400);
		}
		else
		{
			ExperimentDAO dao = new HibernateExperimentDAO();
			
			try {
				ExperimentInvitation inv = dao.getExperimentInvitation(verificationCode);
				
				Date now = new Date();
				
				if (!now.before(inv.getExperimentGroup().getEndDate()))
				{
					response.getWriter().write("Experiment has been finished.");
					response.setStatus(200);
					return;
				}
				
				if (now.before(inv.getExperimentGroup().getStartDate())){
					response.getWriter().write("Experiment has not started yet. Starts on "+inv.getExperimentGroup().getStartDate());
					response.setStatus(200);
					return;
				}
					
				if (inv.hasParticipated())
					dispatchLoginExperiment(response, verificationCode);
				else
					dispatchregisterExperient(response, verificationCode);
				
				
			} catch (MoreThanOneExcpetion e) {
				response.getWriter().write("Wrong URL. Please only use the URL of your experiment invitation email");
				response.setStatus(400);
				e.printStackTrace();
			} catch (NotFoundException e) {
				response.getWriter().write("Wrong URL. Please only use the URL of your experiment invitation email");
				response.setStatus(400);
			}
			
		}
		
		
	}
	
	private void dispatchregisterExperient(HttpServletResponse response, String verificationCode) throws IOException {
		
		
		response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", experimentRegistrationHtmlFile+"?veri="+verificationCode);
		
		/*
		FileReader fr = new FileReader(experimentRegistrationHtmlFile);
	    BufferedReader br = new BufferedReader(fr);

	    String zeile = "";

	    while( (zeile = br.readLine()) != null )
	    {
	    	response.getWriter().write(zeile);
	    }

	    br.close();
		response.setStatus(200);
		*/
	}


	private void dispatchLoginExperiment(HttpServletResponse response, String verificationCode) throws IOException {
		
		
		response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", experimentHtmlFile+"?veri="+verificationCode);
		
		
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
