package test.communication.websocket;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;


import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.CommunicationConnection.ClosedListener;
import org.gemsjax.shared.communication.CommunicationConnection.EstablishedListener;
import org.junit.BeforeClass;
import org.junit.Test;
import org.gemsjax.shared.communication.CommunicationConnection.ErrorListener;
import org.gemsjax.shared.communication.message.system.LoginMessage;


public class AuthenticationTest implements EstablishedListener,ErrorListener, ClosedListener {

	private static TestWebSocketConnection ws;
	
	@BeforeClass
	public static void init()
	{
		
	}
	
	
	
	
	
	
	@Test
	public void test() {
		try {
			ws = new TestWebSocketConnection("ws://localhost:8081/servlets/liveCommunication");
			ws.addEstablishedListener(this);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		try {
			ws.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		System.out.println("Fertig");
		
	}

	
	private void doLogin() throws IOException
	{
		ws.send(new LoginMessage("qwe", "qwe", false));
	}


	@Override
	public void onEstablished() {
		try {
			doLogin();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}






	@Override
	public void onError(Throwable t) {
		t.printStackTrace();
	}






	@Override
	public void onClose(CommunicationConnection connection) {
		System.out.println("Connection closed");
	}


}
