package org.gemsjax.client.communication;

import java.util.ArrayList;
import java.util.List;

import org.gemsjax.client.communication.exception.WebSocketConnectionException;
import org.gemsjax.client.communication.exception.WebSocketSendException;
import org.gemsjax.server.GemsJaxServer;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.util.SC;

/**
 * {@link WebSocket} is the way how the client communicates with the server.
 * But the client application should not use this {@link WebSocket} directly, but should use the {@link Channel}s to
 * send and receive messages.<br />
 * <b>Notice:</b> This is a singleton, so use {@link #getInstance()} to access the {@link WebSocket}
 * @author Hannes Dorfmann
 *
 */
public class WebSocket {

	/**
	 * The singleton instance
	 */
	private static WebSocket INSTANCE = new WebSocket();
	
	/**
	 * The URL to the server
	 */
	private static final String serverURL="localhost:8443";
	
	/**
	 * The absolute URL to the WebSocket Servlet on the {@link GemsJaxServer}.
	 * <br/><br />
	 * <b>Notice</b> The url is not needed, the server url is stored in {@link #serverURL}
	 */
	private static final String webSocketServletURL = "/collaboration";
	
	
	/**
	 * A list with all registered channels
	 */
	private List<Channel> channels;
	 
    private WebSocket() {
        channels = new ArrayList<Channel>();
        SC.showConsole();
        //connect("wss://"+serverURL+webSocketServletURL);
    }
    
   
    
    
    public static WebSocket getInstance()
    {
    	if (INSTANCE == null)
    		INSTANCE = new WebSocket();
    	
    	
    	return INSTANCE;
    }
    
    public void addChannel(Channel c)
    {
    	if (!channels.contains(c))
    		channels.add(c);
    }
    
    
    public void removeChannel(Channel c)
    {
    	channels.remove(c);
    }
    
    private void onOpen() {
    	SC.logWarn("OnOpen");
    	
    		Timer t = new Timer() {
				
				@Override
				public void run() {
					try {
						send("Hello Server, it's me, Mario");
					} catch (WebSocketSendException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			
			t.scheduleRepeating(1000);
	
    }

    
    private void onClose() {
    	SC.logWarn("OnClose");
    
    }

    
    private void onMessage(String message) {
       
    	for (Channel c: channels)
        {
        	if (message.matches(c.getFilterRegEx()))
        		c.onMessageReceived(message);
        }
    	

    	SC.logWarn("Message: "+message);
    }
    
    private void onWebSocketNotSupported()
    {
    	System.out.println("WebSocket not supported by this Browser");
    }
    
    private void notConnected()
    {
    	
    }
    
    private void onSendError(String msgToSend) throws WebSocketSendException
    {
    	throw new WebSocketSendException(msgToSend);
    }
    
    private void onError()
    {

    	SC.logWarn("Error");
    	System.out.println("An Error occurred");
    }

    
    public static native boolean isSupported() /*-{
	   return window.WebSocket;
	}-*/;
    
    
    private void checkConnection(int readyState) throws WebSocketConnectionException
    {
    	if (readyState == 0 || readyState == 2 || readyState == 3)
    	{
    		throw new WebSocketConnectionException(readyState);
    	}
    	
    }
    
    
    public native void connect(String server) /*-{
        var that = this;
        if (!window.WebSocket) {
            that.@org.gemsjax.client.communication.WebSocket::onWebSocketNotSupported()();
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
             that.@org.gemsjax.client.communication.WebSocket::onOpen()();
             console.log("onopen done");
             
        };


        that._ws.onmessage = function(response) {
            console.log("WebSocket _onmessage() data="+response.data);
           
            if (response.data) {
                that.@org.gemsjax.client.communication.WebSocket::onMessage(Ljava/lang/String;)( response.data );
            }
        };

        that._ws.onclose = function(m) {
             console.log("WebSocket["+server+"]_ws.onclose() state:"+that._ws.readyState);
             that.@org.gemsjax.client.communication.WebSocket::onClose()();
        };
        
        that._ws.onerror = function() {
             console.log("WebSocket["+server+"]_ws.onclose() state:"+that._ws.readyState);
             alert("Error");
             that.@org.gemsjax.client.communication.WebSocket::onError()();
        };
    }-*/;

    public native void send(String message) throws WebSocketSendException /*-{
        if (this._ws) {
            console.log("WebSocket sending:"+message);
           
            this._ws.send(message);
        } else {
            this.@org.gemsjax.client.communication.WebSocket::onSendError(Ljava/lang/String;)(message);
        }
    }-*/;

    public native void close() /*-{
        console.log("WebSocket closing");
        this._ws.close();
        console.log("WebSocket closed");
    }-*/;

}


