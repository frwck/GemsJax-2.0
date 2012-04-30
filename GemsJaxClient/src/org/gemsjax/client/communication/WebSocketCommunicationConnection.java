package org.gemsjax.client.communication;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.gemsjax.shared.ServletPaths;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;
import org.gemsjax.shared.communication.message.system.KeepAliveMessage;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.util.SC;

/**
 * {@link WebSocketCommunicationConnection} is the way how the client communicates with the server.
 * But the client application should not use this {@link WebSocketCommunicationConnection} directly, but should use the {@link InputChannel}s to
 * send and receive messages.<br />
 * <b>Notice:</b> This is a singleton, so use {@link #getInstance()} to access the {@link WebSocketCommunicationConnection}
 * @author Hannes Dorfmann
 *
 */
public class WebSocketCommunicationConnection implements CommunicationConnection{
	
	
	/**
	 * This is a simple {@link Timer} that will keep the connection alive by 
	 * sending a simple ping (see communication protocol specification)
	 * @author Hannes Dorfmann
	 *
	 */
	private class KeepAliveTimer extends Timer
	{
		private int timeout = 20*1000;
		private final KeepAliveMessage keepAliveMessage= new KeepAliveMessage();

		@Override
		public void run() {
			
			try {
				WebSocketCommunicationConnection.this.send(keepAliveMessage);
			} catch (IOException e) {
				onError(e);
			}
		}
		
	}
	

	/**
	 * The singleton instance
	 */
	private static WebSocketCommunicationConnection INSTANCE = new WebSocketCommunicationConnection();
	
	/**
	 * The URL to the server without ws:// 
	 */
	private static final String serverURL="localhost";
	
	/**
	 * The absolute URL to the WebSocket Servlet on the {@link GemsJaxServer}.
	 * <br/><br />
	 * <b>Notice</b> The url is not needed, the server url is stored in {@link #serverURL}
	 */
	private static final String webSocketServletURL = ServletPaths.LIVE_WEBSOCKET;
	
	
	private final int port = 8081;
	private final int sslPort=8443;
	
	private final boolean useSsl = false;
	
	@Deprecated
	private Set<InputChannel> inputChannels;
	private Set<ClosedListener> closedListeners;
	private Set<EstablishedListener> establishedListeners;
	private Set<ErrorListener> errorListeners;
	
	private Map<MessageType<?>, Set<InputChannel>> inputChannelMap;
	
	private KeepAliveTimer keepAliveTimer = null;
	
	private boolean keepAlive = true;
	private boolean isConnected = false;
	 
    private WebSocketCommunicationConnection() {
        inputChannels = new LinkedHashSet<InputChannel>();
        establishedListeners = new LinkedHashSet<EstablishedListener>();
        closedListeners = new LinkedHashSet<ClosedListener>();
        errorListeners = new LinkedHashSet<CommunicationConnection.ErrorListener>();
        
        inputChannelMap = new LinkedHashMap<MessageType<?>, Set<InputChannel>>();
        
    }
    
   
    
    
    public static WebSocketCommunicationConnection getInstance()
    {
    	if (INSTANCE == null)
    		INSTANCE = new WebSocketCommunicationConnection();
    	
    	return INSTANCE;
    }
    
    
    private void onOpen() {
    	
    	isConnected = true;
    	
    	
    	if (keepAlive)
		{
    		if (keepAliveTimer!=null)
    			keepAliveTimer.cancel();
    		
			keepAliveTimer = new KeepAliveTimer();
			keepAliveTimer.scheduleRepeating(keepAliveTimer.timeout);
		}
    	
    	
    	for (EstablishedListener e : establishedListeners)
    		e.onEstablished();
    
    	
    }

    
    private void onClose() {
    	isConnected = false;
    	
    	if (keepAliveTimer!=null)
    		keepAliveTimer.cancel();
    	
    	for (ClosedListener c: closedListeners)
    		c.onClose(this);
    }

    
    private void onMessage(String message) {
       
    	InputMessage im = new InputMessage(200, message);
    	
    	for (InputChannel c: inputChannels)
        {
    		if (c.isMatchingFilter(message))
    			c.onMessageReceived(im);
        }
    	
    }
    
    private void onWebSocketNotSupported() throws IOException
    {
    	throw new IOException("WebSockets are not supported by this Browser");
    }
    
    private void notConnected()
    {
    	
    }
    
    private void generateSendError(String msgToSend) throws IOException
    {
    	throw new IOException("Could not send: "+msgToSend);
    }
    
    private void onError()
    {
    	Throwable t = new Throwable("WebSocket onError() is called");
    	
    	for (ErrorListener l : errorListeners)
    		l.onError(t);
    }

    
    private void onError(Throwable e)
    {
    
    	for (ErrorListener l : errorListeners)
    		l.onError(e);
    }
    
    public static native boolean doIsSupportedCheck() /*-{
	   return window.WebSocket;
	}-*/;
    
    
    private void checkConnection(int readyState) throws IOException
    {
    	if (readyState == 0 || readyState == 2 || readyState == 3)
    	{
    		throw new IOException("Connection not established");
    	}
    	
    }
    
    
    public native void doConnect(String server) throws IOException /*-{
        var that = this;
        if (!window.WebSocket) {
            that.@org.gemsjax.client.communication.WebSocketCommunicationConnection::onWebSocketNotSupported()();
            return;
        }
        
    
        console.log("WebSocket connecting to "+server);
        that._ws=new WebSocket(server);
        
        console.log("WebSocket connected "+that._ws.readyState);

        that._ws.onopen = function() {
            if(!that._ws) {
                console.log("WebSocket not really opened?");
                console.log("WebSocket["+server+"]._ws.onopen()");
                return;
            }
             console.log("onopen, readyState: "+that._ws.readyState);
             that.@org.gemsjax.client.communication.WebSocketCommunicationConnection::onOpen()();
             console.log("onopen done");
             
        };


        that._ws.onmessage = function(response) {
            console.log("WebSocket received: "+response.data);
           
            if (response.data) {
                that.@org.gemsjax.client.communication.WebSocketCommunicationConnection::onMessage(Ljava/lang/String;)( response.data );
            }
        };

        that._ws.onclose = function(m) {
             console.log("WebSocket["+server+"]_ws.onclose() state:"+that._ws.readyState);
             that.@org.gemsjax.client.communication.WebSocketCommunicationConnection::onClose()();
        };
        
        that._ws.onerror = function() {
             console.log("WebSocket["+server+"]_ws.onclose() state:"+that._ws.readyState);
             alert("Error");
             that.@org.gemsjax.client.communication.WebSocketCommunicationConnection::onError()();
        };
    }-*/;

    private native void doSend(String message) throws IOException /*-{
        if (this._ws) {
            console.log("WebSocket sending:"+message);
           
            this._ws.send(message);
        } else {
            this.@org.gemsjax.client.communication.WebSocketCommunicationConnection::generateSendError(Ljava/lang/String;)(message);
        }
    }-*/;

    private native void doClose() /*-{
        console.log("WebSocket closing");
        this._ws.close();
        console.log("WebSocket closed");
    }-*/;




	@Override
	public void connect() throws IOException {
		doConnect(getRemoteAddress());
		
	}




	@Override
	public void deregisterInputChannel(InputChannel c) {
		inputChannels.remove(c);
		
		for (Map.Entry<MessageType<?>, Set<InputChannel>> e : inputChannelMap.entrySet())
			e.getValue().remove(c);
	}




	@Override
	public int getPort() {
		return port;
	}




	@Override
	public String getRemoteAddress() {
		return (useSsl?"wss://":"ws://")+serverURL + ":"+(useSsl?sslPort:port)+webSocketServletURL;
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
		if (keepAlive)
		{	if (!this.keepAlive)
			{
				if (keepAliveTimer != null) 
					keepAliveTimer.cancel();
				
				keepAliveTimer = new KeepAliveTimer();
				keepAliveTimer.scheduleRepeating(keepAliveTimer.timeout);
			}
		}
		else
			if(this.keepAlive)
			{
				if (keepAliveTimer != null)
					keepAliveTimer.cancel();
			}
		
		this.keepAlive = keepAlive;
	}




	@Override
	public void close() throws IOException {
		doClose();
	}




	@Override
	public void send(Message message) throws IOException {
		doSend(message.toXml());
	}




	@Override
	public boolean isSupported() {
		return doIsSupportedCheck();
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


