package test.communication.websocket;

import java.io.IOException;
import java.net.URI;

import org.gemsjax.server.communication.parser.SystemMessageParser;
import org.junit.Test;

import net.tootallnate.websocket.WebSocketClient;


class WebSocketTestClient extends WebSocketClient
{

	public WebSocketTestClient(URI serverAddress) {
		super(serverAddress);
		
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIOError(IOException arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpen() {
		// TODO Auto-generated method stub
		
	}
	
}



public class CommunicationWebSocketTest {
	
	
	

}
