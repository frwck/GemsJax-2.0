package test.communication.websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.LinkedHashSet;
import java.util.Set;

import net.tootallnate.websocket.WebSocketClient;

import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.message.Message;

public class TestWebSocketConnection implements CommunicationConnection {


	
	
	
	
	private WebSocketClient ws;
	
	
	private final int port = 8081;
	private final int sslPort=8443;
	private final boolean useSsl = false;
	
	private Set<InputChannel> inputChannels;
	private Set<ClosedListener> closedListeners;
	private Set<EstablishedListener> establishedListeners;
	private Set<ErrorListener> errorListeners;
	
	
	private boolean keepAlive = true;
	private boolean isConnected = false;
	
	
	public TestWebSocketConnection(String serverUrl) throws URISyntaxException
	{
		
		 inputChannels = new LinkedHashSet<InputChannel>();
	        establishedListeners = new LinkedHashSet<EstablishedListener>();
	        closedListeners = new LinkedHashSet<ClosedListener>();
	        errorListeners = new LinkedHashSet<CommunicationConnection.ErrorListener>();
	        
	        
		ws = new WebSocketClient(new URI(serverUrl)) {
			
			@Override
			public void onOpen() {
				TestWebSocketConnection.this.onOpen();
			}
			
			@Override
			public void onMessage(String arg0) {
				TestWebSocketConnection.this.onMessage(arg0);
			}
			
			@Override
			public void onError(Exception arg0) {
				TestWebSocketConnection.this.onError(arg0);
			}
			
			@Override
			public void onClose() {
				TestWebSocketConnection.this.onClose();
			}
		};
	}
	
	
	
	
	 private void onOpen() {
	    	
	    	isConnected = true;
	    	
	    	for (EstablishedListener e : establishedListeners)
	    		e.onEstablished();
	    
	    	
	    }

	    
	    private void onClose() {
	    	isConnected = false;
	    	
	    	for (ClosedListener c: closedListeners)
	    		c.onClose();
	    }

	    
	    private void onMessage(String message) {
	       
	    	System.out.println("Got: "+message);
	    	
	    	InputMessage im = new InputMessage(200, message);
	    	
	    	for (InputChannel c: inputChannels)
	        {
	    		if (c.isMatchingFilter(message))
	    			c.onMessageReceived(im);
	        }
	    	
	    }
	
	    
	   

	    
	    private void onError(Throwable e)
	    {
	    
	    	for (ErrorListener l : errorListeners)
	    		l.onError(e);
	    }


		@Override
		public void connect() throws IOException {
			ws.connect();
			
		}




		@Override
		public void deregisterInputChannel(InputChannel c) {
			inputChannels.remove(c);
		}




		@Override
		public int getPort() {
			return port;
		}




		@Override
		public String getRemoteAddress() {
			return ws.getURI().toString();
		}




		@Override
		public boolean isClosed() {
			return !isConnected;
		}




		@Override
		public boolean isConnected() {
			return isConnected;
		}




		@Override
		public boolean isKeepAlive() {
			return keepAlive;
		}




		@Override
		public void registerInputChannel(InputChannel c) {
			inputChannels.add(c);
		}




		@Override
		public void setKeepAlive(boolean keepAlive) {
			
		}




		@Override
		public void close() throws IOException {
			ws.close();
		}




		@Override
		public void send(Message message) throws IOException {
			try {
				System.out.println("Sending "+message.toXml());
				ws.send(message.toXml());
			} catch (NotYetConnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IOException(e.getMessage());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				throw new IOException(e.getMessage());
			}
		}




		@Override
		public boolean isSupported() {
			return true;
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
		public void removeCloseListener(ClosedListener listener) {
			closedListeners.remove(listener);
		}




		@Override
		public void removeEstablishedListener(EstablishedListener listener) {
			establishedListeners.remove(listener);
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
