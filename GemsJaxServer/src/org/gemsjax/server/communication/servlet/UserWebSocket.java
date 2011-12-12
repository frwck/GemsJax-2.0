package org.gemsjax.server.communication.servlet;



import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.eclipse.jetty.websocket.WebSocket;
import org.gemsjax.server.communication.UserAuthenticationChannel;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;



	/**
	 * A ClientManager manages the server-client connection
	 * and implements the server side communication protocol
	 * @author Hannes Dorfmann
	 *
	 */
	public class UserWebSocket implements CommunicationConnection, WebSocket.OnTextMessage {

		private Connection connection;
		private HttpSession session;
		
		private Set<InputChannel> inputChannels;

		
		public UserWebSocket(HttpSession session)
		{
			this.session = session;
			connection = null;
			inputChannels = new HashSet<InputChannel>();
		}
		
		@Override
		public void onClose(int arg0, String arg1) {
			for (InputChannel c: inputChannels)
				c.onClose();
			
		}

		@Override
		public void onOpen(Connection con) {
			this.connection = con;
			
			UserAuthenticationChannel u = new UserAuthenticationChannel(this);
			this.registerInputChannel(u);
		}

		@Override
		public void onMessage(String data) {
		
	    	for (InputChannel c: inputChannels)
	        {
	        	if (data.matches(c.getFilterRegEx()))
	        		c.onMessageReceived(data);
	        }
	    	
		}
		
		
		public void send(String message) throws IOException
		{
			connection.sendMessage(message);
		}

		@Override
		public void close() throws IOException {
			connection.disconnect();
			inputChannels.clear();
		}

		@Override
		public void connect() throws IOException {
			throw new UnsupportedOperationException("The connect() method is not supported on server side, since the client is the one who beginns always with the Handshake");
		}

		@Override
		public void deregisterInputChannel(InputChannel c) {
			inputChannels.remove(c);
		}

		@Override
		public int getPort() {
			return 0;
		}

		@Override
		public String getRemoteAddress() {
			return connection.toString();
		}

		@Override
		public boolean isClosed() {
			return !connection.isOpen();
		}

		@Override
		public boolean isConnected() {
			return connection.isOpen();
		}

		@Override
		public boolean isKeepAlive() {
			// Its up to the client to keep the connection alive by sending a <ping /> from in a periodic time
			return false;
		}

		@Override
		public boolean isSupported() {
			return true;
		}

		@Override
		public void registerInputChannel(InputChannel c) {
			inputChannels.add(c);
		}
		
		@Override
		public void setKeepAlive(boolean keepAlive) {
			throw new UnsupportedOperationException();
		}

}

	

