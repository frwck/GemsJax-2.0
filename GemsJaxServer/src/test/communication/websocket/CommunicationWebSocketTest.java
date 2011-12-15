package test.communication.websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.gemsjax.shared.communication.message.system.LoginMessage;
import org.junit.BeforeClass;
import org.junit.Test;



public class CommunicationWebSocketTest {
	
	
	private static WebSocketTestClient client;
	
	@BeforeClass
	public static void initClass() throws URISyntaxException
	{
		client = new WebSocketTestClient(new URI("ws://127.0.0.1:8080/servlets/liveCommunication"));
		client.connect();
	}
	
	
	@Test
	public void loginTest() throws IOException
	{
		
		LoginMessage m = new LoginMessage("username", "password", false);
		
		client.send(m.toXml());
		
		
	}
	
	
	
	
}
