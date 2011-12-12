package org.gemsjax.client.admin;


import java.io.IOException;

import org.gemsjax.client.communication.WebSocketCommunicationConnection;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.util.KeyCallback;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;


//TODO BrowserSupportCheck

public class GemsJaxClient implements EntryPoint {

	public void onModuleLoad() {
		
		WebSocketCommunicationConnection webSocket = WebSocketCommunicationConnection.getInstance();
		
		//webSocket.connect("ws://localhost:8080/");
		
		
		try {
			webSocket.connect();
		
		
			if (!GWT.isScript()) {
				KeyIdentifier debugKey = new KeyIdentifier();
				//debugKey.setAltKey(true);
				debugKey.setKeyName("Q");
	
				Page.registerKey(debugKey, new KeyCallback() {
					public void execute(String keyName) {
						SC.showConsole();
						SC.debugger();
					}
				});
				
			}
			
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
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}

}
	
