package org.gemsjax.client.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gemsjax.client.communication.exception.WebSocketConnectionException;
import org.gemsjax.client.communication.exception.WebSocketSendException;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;

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
		private final String keepAliveProtokoll = "<ping />";

		@Override
		public void run() {
			
			try {
				WebSocketCommunicationConnection.this.send("<ping />");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	private static final String webSocketServletURL = "/servlets/collaboration";
	
	
	private final int port = 8080;
	private final int sslPort=8443;
	
	private final boolean useSsl = false;
	
	private Set<InputChannel> inputChannels;
	
	private KeepAliveTimer keepAliveTimer = null;
	
	private boolean keepAlive = true;
	 
    private WebSocketCommunicationConnection() {
        SC.showConsole();
        inputChannels = new LinkedHashSet<InputChannel>();
    }
    
   
    
    
    public static WebSocketCommunicationConnection getInstance()
    {
    	if (INSTANCE == null)
    		INSTANCE = new WebSocketCommunicationConnection();
    	
    	return INSTANCE;
    }
    
    
    private void onOpen() {
    	
    }

    
    private void onClose() {
    	
    }

    
    private void onMessage(String message) {
       
    	for (InputChannel c: inputChannels)
        {
        	if (message.matches(c.getFilterRegEx()))
        		c.onMessageReceived(message);
        }
    	

    	SC.logWarn("Message: "+message);
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

    	SC.logWarn("Error");
    	System.out.println("An Error occurred");
    }

    
    public static native boolean doIsSupportedCheck() /*-{
	   return window.WebSocket;
	}-*/;
    
    
    private void checkConnection(int readyState) throws WebSocketConnectionException
    {
    	if (readyState == 0 || readyState == 2 || readyState == 3)
    	{
    		throw new WebSocketConnectionException(readyState);
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
            console.log("WebSocket _onmessage() data="+response.data);
           
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
		
		if (keepAlive)
		{
			keepAliveTimer = new KeepAliveTimer();
			keepAliveTimer.scheduleRepeating(keepAliveTimer.timeout);
		}
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
		return (useSsl?"wss://":"ws://")+serverURL + ":"+(useSsl?sslPort:port)+webSocketServletURL;
	}




	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
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
	public void send(String toSend) throws IOException {
		doSend(toSend);
	}




	@Override
	public boolean isSupported() {
		return doIsSupportedCheck();
	}

}


