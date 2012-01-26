package test.communication;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

import net.tootallnate.websocket.WebSocketClient;
import net.tootallnate.websocket.drafts.Draft_10;
import net.tootallnate.websocket.drafts.Draft_17;

import org.gemsjax.shared.ServletPaths;
import org.junit.Test;

public class WebSocketTest {


	
	
	public static void main(String args[])
	{
		test();
	}
	
	
	
	
	
	
	public static void test() {
		
		
		
	
		try {
			WebSocketClient cc = new WebSocketClient( new URI( "ws://localhost:8081"+ ServletPaths.LIVE_WEBSOCKET), new Draft_10() ) {

				public void onMessage( String message ) {
					System.out.println( "got: " + message + "\n" );
					
				}

				public void onOpen() {
					System.out.println( "You are connected to Server: " + getURI() + "\n" );
					/*try {
						this.send("<sys> ");
					} catch (NotYetConnectedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					*/
					
				}

				public void onClose() {
					System.out.println( "You have been disconnected from: " + getURI() + "\n" );
					
				}

				public void onError( Exception ex ) {
					System.out.println( "Exception occured ...\n" + ex + "\n" );
				}
			};
			
			
			cc.connect();
			//cc.send("Hello");
			System.out.println("Do sein mor");
			
			cc.close();
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotYetConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

}
