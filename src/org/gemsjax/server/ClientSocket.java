package org.gemsjax.server;


import java.io.IOException;
import org.eclipse.jetty.websocket.WebSocket;



	/**
	 * A ClientManager manages the server-client connection
	 * and implements the server side communication protocol
	 * @author Hannes Dorfmann
	 *
	 */
	public class ClientSocket implements WebSocket {

		private Outbound outbound;
		
		
		public ClientSocket()
		{
			super();
			System.out.println("New Socket");
			
		}
		
		@Override
		public void onConnect(Outbound out) {
			outbound = out;
		}

		@Override
		public void onDisconnect() {
			System.out.println("disconnect");
		}

		@Override
		public void onMessage(byte frame, String msg) {
			System.out.println(this+" "+msg);
			try {
				sendMessage("RE: "+msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}

		@Override
		public void onMessage(byte frame, byte[] data, int offset, int length) {
			System.out.println("byte "+data);
		}
		
		
		/**
		 * Send a message over this WebSocket to the Client
		 * @param msg
		 * @throws IOException
		 */
		public void sendMessage(String msg) throws IOException
		{
			outbound.sendMessage(msg);
		}
		
		
		
		

}

	

