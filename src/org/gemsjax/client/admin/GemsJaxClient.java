package org.gemsjax.client.admin;


import org.gemsjax.client.communication.WebSocket;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
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

		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void onUncaughtException(Throwable e) {
				
				String msg =e.toString() + " "+e.getLocalizedMessage() +" "+ e.getMessage()+" \n";
				
				for (StackTraceElement el : e.getStackTrace())
					msg += el.toString() + "\n";
				
				
				e.printStackTrace();
				SC.say(msg);
				SC.logWarn(msg);

			}
		})
		;
		
		AdminApplicationController adminApplicationcontroller = AdminApplicationController.getInstance();
		adminApplicationcontroller.start();
		
		
		
		
	}

}
	
