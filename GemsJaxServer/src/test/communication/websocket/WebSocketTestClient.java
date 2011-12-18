package test.communication.websocket;

import java.io.IOException;
import java.net.URI;

import net.tootallnate.websocket.WebSocketClient;

import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.message.Message;

public class WebSocketTestClient extends WebSocketClient implements CommunicationConnection
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
		
	}

	@Override
	public void onOpen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deregisterInputChannel(InputChannel arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getRemoteAddress() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerInputChannel(InputChannel arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setKeepAlive(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCloseListener(ClosedListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEstablishedListener(EstablishedListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCloseListener(ClosedListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEstablishedListener(EstablishedListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(Message arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}
