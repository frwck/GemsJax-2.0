package org.gemsjax.client.admin;

import org.gemsjax.client.websocket.WebSocket;

import com.google.gwt.core.client.EntryPoint;


//TODO BrowserSupportCheck

public class GemsJaxClient implements EntryPoint {

	private WebSocket webSocket;
	
	public void onModuleLoad() {
		/*
		webSocket = new WebSocket();
		webSocket.connect("wss://localhost:8443/GemsJaxServlet");
		*/
		// gemsJaxUI = DesktopEnviromentUI.getInstance();
		
		
		AdminApplicationController adminApplicationcontroller = AdminApplicationController.getInstance();
		adminApplicationcontroller.start();
		
		
		
		
	}
		

}
	
