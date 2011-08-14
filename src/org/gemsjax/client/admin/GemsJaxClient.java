package org.gemsjax.client.admin;

import org.gemsjax.client.websocket.WebSocket;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.util.SC;


//TODO BrowserSupportCheck

public class GemsJaxClient implements EntryPoint {

	private WebSocket webSocket;
	
	public void onModuleLoad() {
		/*
		webSocket = new WebSocket();
		webSocket.connect("wss://localhost:8443/GemsJaxServlet");
		*/
		// gemsJaxUI = DesktopEnviromentUI.getInstance();
		
		SC.showConsole();

		
		AdminApplicationController adminApplicationcontroller = AdminApplicationController.getInstance();
		adminApplicationcontroller.start();
		
		
		
		
	}
		

}
	
