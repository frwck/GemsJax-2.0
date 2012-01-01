package org.gemsjax.server.communication.servlet;



import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.eclipse.jetty.websocket.WebSocket;
import org.gemsjax.server.communication.channel.UserAuthenticationChannel;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.message.Message;



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
		
		private Set<ClosedListener> closedListeners;
		private Set<EstablishedListener> establishedListeners;
		private Set<ErrorListener> errorListeners;

		
		public UserWebSocket(HttpSession session)
		{
			this.session = session;
			connection = null;
			inputChannels = new LinkedHashSet<InputChannel>();
			closedListeners = new LinkedHashSet<ClosedListener>();
			establishedListeners = new LinkedHashSet<EstablishedListener>();
			errorListeners = new LinkedHashSet<CommunicationConnection.ErrorListener>();
		}
		
		@Override
		public void onOpen(Connection con) {
			this.connection = con;
			
			UserAuthenticationChannel u = new UserAuthenticationChannel(this, session);
			this.registerInputChannel(u);
			
			for (EstablishedListener e: establishedListeners)
				e.onEstablished();
		}

		@Override
		public void onMessage(String data) {
		
			
			System.out.println("Received: "+data);
			
			InputMessage im = new InputMessage(200, data);
			
	    	for (InputChannel c: inputChannels)
	        {
	        	if (c.isMatchingFilter(data))
	        		c.onMessageReceived(im);
	        }
	    	
		}
		
		
		public void send(Message message) throws IOException
		{
			System.out.println("Sending: "+message);
			connection.sendMessage(message.toXml());
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

		@Override
		public void addCloseListener(ClosedListener arg0) {
			closedListeners.add(arg0);
		}

		@Override
		public void addEstablishedListener(EstablishedListener arg0) {
			establishedListeners.add(arg0);
		}

		@Override
		public void removeCloseListener(ClosedListener arg0) {
			closedListeners.remove(arg0);
		}

		@Override
		public void removeEstablishedListener(EstablishedListener arg0) {
			establishedListeners.remove(arg0);
		}
		
		@Override
		public void onClose(int arg0, String arg1) {
			
			for (ClosedListener c: closedListeners)
				c.onClose();
		}

		@Override
		public void addErrorListener(ErrorListener arg0) {
			errorListeners.add(arg0);
		}

		@Override
		public void removeErrorListener(ErrorListener arg0) {
			errorListeners.remove(arg0);
		}

	}

	

