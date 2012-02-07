package org.gemsjax.client.communication;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.util.Console;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.message.Message;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

/**
 * This is a {@link CommunicationConnection} to interact with the server via HTTP POST.
 * So a {@link Message} can be posted by via HTTP to the server by calling {@link #send(Message)} which will 
 * transform the passed {@link Message} to a HTTP POST expected paramatername-parametervalue pair which looks as follows:
 * <br />
 * {@link Message#POST_PARAMETER_NAME}={@link Message#toXml()}
 * <br /><br />
 * 
 * The server can simply reach the xml representation of the sent message by retrieving the sent POST parameter with the name
 * {@link Message#POST_PARAMETER_NAME} containing {@link Message#toXml()} as value (for this parameter name).
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
	private Set<ErrorListener> errorListeners;
	
	public HttpPostCommunicationConnection(String serverUrl)
	{
		this.serverUrl = serverUrl;
		
		inputChannels = new LinkedHashSet<InputChannel>();
		closedListeners = new LinkedHashSet<ClosedListener>();
		establishedListeners = new LinkedHashSet<EstablishedListener>();
		errorListeners = new LinkedHashSet<CommunicationConnection.ErrorListener>();
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
		builder = new RequestBuilder(RequestBuilder.POST, URL.encode(serverUrl));
		builder.setHeader("Content-type", "application/x-www-form-urlencoded");
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
		
		
		try {
		  String postParameter=Message.POST_PARAMETER_NAME+"="+URL.encode(message.toXml());
		  //TODO remove console
		  Console.log("SEND HTTP Post: "+message.toXml());
		  
		  builder.sendRequest(postParameter, new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request request, Response response) {
				//TODO remove console
				Console.log("RECEIVED HTTP Post: "+response.getText());
				onRequestResponseReceived(request, response);
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				onRequestError(request, exception);
			}
		  });
		} catch (RequestException e) {
		  
			throw new IOException(e.getMessage());
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
			if (i.isMatchingFilter(response.getText()))
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
}
