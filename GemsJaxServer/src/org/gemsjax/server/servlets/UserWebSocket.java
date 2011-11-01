package org.gemsjax.server.servlets;



import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.eclipse.jetty.websocket.WebSocket;



	/**
	 * A ClientManager manages the server-client connection
	 * and implements the server side communication protocol
	 * @author Hannes Dorfmann
	 *
	 */
	public class UserWebSocket implements WebSocket.OnTextMessage {

		private Connection connection;
		private int counter = 0;
		
		private HttpSession session;
		
		public UserWebSocket(HttpSession session)
		{
			this.session = session;
			connection = null;
		}
		
		@Override
		public void onClose(int arg0, String arg1) {
			System.out.println("on close");
		}

		@Override
		public void onOpen(Connection con) {
			this.connection = con;
			System.out.println("on open");
			
		}

		@Override
		public void onMessage(String data) {
			counter ++;
			System.out.println(session.getId() +" LA "+session.getLastAccessedTime()+" CT "+session.getCreationTime()+" Message: "+data+ " new "+session.isNew());

			if (counter > 100)
				session.invalidate();
			
			try {
				connection.sendMessage("Hello Client : RE : "+data +" "+counter);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		public void send(String message) throws IOException
		{
			connection.sendMessage(message);
		}

}

	

