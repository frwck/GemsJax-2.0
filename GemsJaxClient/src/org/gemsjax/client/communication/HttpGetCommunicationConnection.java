package org.gemsjax.client.communication;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

/**
 * This is a {@link CommunicationConnection} that interact with the server via HTTP GET.
 * Since HTTP GET is limited only to retrieve data from the server
 * it is not possible to send data to the server.
 * So calling {@link #send(Message)} will send a HTTP GET to the server with the {@link Message} as attached  URL parameters 
 * 
 * @see{@link Message#toHttpGet()}
 * @author Hannes Dorfmann
 *
 */
public class HttpGetCommunicationConnection implements CommunicationConnection {
	
	private String serverUrl;
	private RequestBuilder builder;
	private Request request;
	
	private Set<InputChannel> inputChannels;
	private Set<ClosedListener> closedListeners;
	private Set<EstablishedListener> establishedListeners;
	private Set<ErrorListener> errorListeners;
	
	private Map<MessageType<?>, Set<InputChannel>> inputChannelMap;
	
	public HttpGetCommunicationConnection(String serverUrl)
	{
		this.serverUrl = serverUrl;
		inputChannels = new LinkedHashSet<InputChannel>();
		closedListeners = new LinkedHashSet<ClosedListener>();
		establishedListeners = new LinkedHashSet<EstablishedListener>();
		errorListeners = new LinkedHashSet<CommunicationConnection.ErrorListener>();
		inputChannelMap = new LinkedHashMap<MessageType<?>, Set<InputChannel>>();
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
			c.onClose(this);
	}


	@Override
	public void connect() throws IOException {
		builder = new RequestBuilder(RequestBuilder.GET, URL.encode(serverUrl));
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
		
		try
		{
			request = builder.sendRequest(null, new RequestCallback() {
			    public void onError(Request request, Throwable exception) {
			    	onRequestError(request, exception);
			    }
			
			    public void onResponseReceived(Request request, Response response) {
			     
			    	onRequestResponseReceived(request, response);
			    }
     
			});
		} catch (RequestException e) {
		  throw new IOException(e);        
		}
	}


	@Override
	public void setKeepAlive(boolean keepAlive) {
		throw new UnsupportedOperationException();
	}
	
	
	
	private void onRequestError(Request request, Throwable exception)
	{
		for (ErrorListener e : errorListeners)
			e.onError(exception);
	}
	
	
	private void onRequestResponseReceived(Request request, Response response)
	{
		InputMessage im = new InputMessage(response.getStatusCode(), response.getText());
		
		for (InputChannel i: inputChannels)
			if (i.isMatchingFilter(response.getText()) )
				i.onMessageReceived(im);
			
	}


	@Override
	public void addErrorListener(ErrorListener listener) {
		errorListeners.add(listener);
	}


	@Override
	public void removeErrorListener(ErrorListener listener) {
		errorListeners.remove(listener);
	}
	
	@Override
	public void registerInputChannel(InputChannel c, MessageType<?> type) {
		
		if (!inputChannelMap.containsKey(type)){ // key is not already there
			Set<InputChannel> channels =  new LinkedHashSet<InputChannel>();
			
			channels.add(c);
			
			inputChannelMap.put(type, channels);
		}
		else
		{	// Key is already present, so append the channel to the list
			Set<InputChannel> channels = inputChannelMap.get(type);
			channels.add(c);
		}
	}

}
