package org.gemsjax.client.communication;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.message.Message;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

/**
 * This is a {@link CommunicationConnection} to interact with the server via HTTP POST.
 * So data can be posted by via HTTP to the server by calling {@link #send(Message)} which will call
 * {@link Message#toHttpPost()}
 * 
 * @author Hannes Dorfmann
 *
 */
public class HttpPostCommunicationConnection implements CommunicationConnection{

	
	private String serverUrl;
	private RequestBuilder builder;
	private Request request;
	
	private Set<InputChannel> inputChannels;
	private Set<ClosedListener> closedListeners;
	private Set<EstablishedListener> establishedListeners;
	
	
	public HttpPostCommunicationConnection(String serverUrl)
	{
		this.serverUrl = serverUrl;
		
		inputChannels = new LinkedHashSet<InputChannel>();
		closedListeners = new LinkedHashSet<ClosedListener>();
		establishedListeners = new LinkedHashSet<EstablishedListener>();
	}
	
	@Override
	public void addCloseListener(ClosedListener listener) {
		closedListeners.add(listener);
	}


	@Override
	public void addEstablishedListener(EstablishedListener listener) {
		establishedListeners.add(listener);
	}


	@Override
	public void close() throws IOException {
		request.cancel();
		
		for (ClosedListener c: closedListeners)
			c.onClose();
	}


	@Override
	public void connect() throws IOException {
		builder = new RequestBuilder(RequestBuilder.POST, URL.encode(serverUrl));
	}

	@Override
	public void deregisterInputChannel(InputChannel c) {
		inputChannels.remove(c);
	}


	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getRemoteAddress() {
		return serverUrl;
	}


	@Override
	public boolean isClosed() {
		if (request == null)
			return true;
		else
			return !request.isPending();
	}


	@Override
	public boolean isConnected() {
		if (request == null) return false;
		else
			return request.isPending();
	}


	@Override
	public boolean isKeepAlive() {
		return false;
	}


	@Override
	public boolean isSupported() {
		// TODO add support check, but HTTP GET should be Supported by every browser
		return true;
	}


	@Override
	public void registerInputChannel(InputChannel c) {
		inputChannels.add(c);
	}


	@Override
	public void removeCloseListener(ClosedListener listener) {
		closedListeners.remove(listener);
	}


	@Override
	public void removeEstablishedListener(EstablishedListener listener) {
		establishedListeners.remove(listener);
	}
	

	@Override
	public void send(Message message) throws IOException {
		
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "/yourserver/formprocessor");
		builder.setHeader("Content-type", "application/x-www-form-urlencoded");
		try {
		  builder.sendRequest(message.toHttpPost(), new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request request, Response response) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				
			}
		  });
		} catch (RequestException e) {
		  // handle this
		}
		
	}

	@Override
	public void setKeepAlive(boolean keepAlive) {
		throw new UnsupportedOperationException();
	}

}