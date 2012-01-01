package org.gemsjax.server.communication.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.CommunicationConnection.ClosedListener;
import org.gemsjax.shared.communication.CommunicationConnection.ErrorListener;
import org.gemsjax.shared.communication.CommunicationConnection.EstablishedListener;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.message.Message;

/**
 * This is a Servlet that only accepts POST requests, 
 * so you need to override the {@link #doPost(HttpServletRequest, HttpServletResponse)} method
 * @author Hannes Dorfmann
 *
 */
public abstract class HttpPostCommunicationConnection extends HttpServlet implements CommunicationConnection{
	
	private Set<InputChannel> inputChannels;
	private Set<EstablishedListener> establishedListeners;
	private Set<ClosedListener> closedListeners;
//	private Set<ErrorListener> errorListeners;
	
	public HttpPostCommunicationConnection()
	{
		inputChannels = new LinkedHashSet<InputChannel>();
		establishedListeners = new LinkedHashSet<CommunicationConnection.EstablishedListener>();
		closedListeners = new LinkedHashSet<CommunicationConnection.ClosedListener>();
//		errorListeners = new LinkedHashSet<CommunicationConnection.ErrorListener>();
	}
	
	@Override
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
	
	
	

	@Override
	public void close() throws IOException
	{
		throw new UnsupportedOperationException("close() is not supported by the http post servlet");
	}
	
	@Override
	public boolean isClosed()
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void connect() throws IOException{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public  int getPort(){
		throw new UnsupportedOperationException();
	}
	
	@Override
	public  boolean isSupported()
	{
		return true;
	}
	
	@Override
	public String getRemoteAddress(){

		throw new UnsupportedOperationException();
	}
	
	
	public boolean isConnected(){

		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setKeepAlive(boolean  keepAlive)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean isKeepAlive(){
		return false;
	}
	
	@Override
	public abstract void send(Message message) throws IOException;
	
	/**
	 * Call this, to push a {@link InputMessage} to all {@link InputChannel}s, that return ture on calling {@link InputChannel#isMatchingFilter(String)}
	 * @param message The {@link InputMessage} which should pushed
	 */
	protected void fireInputChannelMessage(InputMessage message)
	{
		for (InputChannel i : inputChannels)
			if (i.isMatchingFilter(message.getText()))
				i.onMessageReceived(message);
	}
	
	
	@Override
	public void registerInputChannel(InputChannel c)
	{
		inputChannels.add(c);
	}
	
	@Override
	public void deregisterInputChannel(InputChannel c){
		inputChannels.remove(c);
	}
	
	@Override
	public void addCloseListener(ClosedListener listener)
	{
		closedListeners.add(listener);
	}
	
	@Override
	public void removeCloseListener(ClosedListener listener){
		closedListeners.remove(listener);
	}
	
	@Override
	public void addEstablishedListener(EstablishedListener listener){
		establishedListeners.add(listener);
	}
	
	@Override
	public void removeEstablishedListener(EstablishedListener listener){
		establishedListeners.remove(listener);
	}
	
	@Override
	public void addErrorListener(ErrorListener listener){

		throw new UnsupportedOperationException();
		//errorListeners.add(listener);
	}
	
	@Override
	public void removeErrorListener(ErrorListener listener){

		throw new UnsupportedOperationException();
		//errorListeners.remove(listener);
	}

	
	
}

