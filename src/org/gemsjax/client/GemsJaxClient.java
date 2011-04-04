package org.gemsjax.client;

import org.gemsjax.client.desktopenviroment.DesktopEnviromentUI;
import org.gemsjax.client.websocket.WebSocket;

import com.google.gwt.core.client.EntryPoint;

public class GemsJaxClient implements EntryPoint {

	private DesktopEnviromentUI gemsJaxUI;
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
	
