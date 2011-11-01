package org.gemsjax.server;


import java.io.IOException;
import org.eclipse.jetty.websocket.WebSocket;



	/**
	 * A ClientManager manages the server-client connection
	 * and implements the server side communication protocol
	 * @author Hannes Dorfmann
	 *
	 */
	public class ClientSocket implements WebSocket.OnTextMessage {

		private Connection connection;
		
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
			System.out.println("Message: "+data);
			try {
				connection.sendMessage("Ans from server: RE "+data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}

	

