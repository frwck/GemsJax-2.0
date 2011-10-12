package org.gemsjax.client.communication;

import java.util.ArrayList;
import java.util.List;

import org.gemsjax.client.communication.exception.WebSocketSendException;

import net.sourceforge.htmlunit.corejs.javascript.ast.ThrowStatement;

public class WebSocket {

	/**
	 * A list with all registered channels
	 */
	private List<Channel> channels;
	 
    public WebSocket() {
        channels = new ArrayList<Channel>();
    }
    
    public WebSocket(String serverURL)
    {
    	this();
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
        System.out.println("Opend");
    }

    
    private void onClose() {
        
    }

    
    private void onMessage(String message) {
        System.out.println("Message received "+ message);
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
    	System.out.println("An Error occurred");
    }

    
    public static native boolean isSupported() /*-{
	   return window.WebSocket;
	}-*/;
    
    
    
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


