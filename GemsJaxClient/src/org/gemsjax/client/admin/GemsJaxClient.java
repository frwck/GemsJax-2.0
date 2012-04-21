package org.gemsjax.client.admin;


import java.io.IOException;

import org.gemsjax.client.communication.WebSocketCommunicationConnection;
import org.gemsjax.client.tests.TestRunner;
import org.gemsjax.client.tests.testcases.CollaborationFileMessageTest;
import org.gemsjax.client.util.Console;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.util.KeyCallback;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;


//TODO BrowserSupportCheck

public class GemsJaxClient implements EntryPoint {

	public void onModuleLoad() {
		
		
		onBodyLoaded();
		
	}
	
	
	
	
	private void onBodyLoaded()
	{
		Console.log("OnBodyLoaded");
		
		
		try {
			WebSocketCommunicationConnection webSocket = WebSocketCommunicationConnection.getInstance();
			webSocket.connect();
					
				KeyIdentifier debugKey = new KeyIdentifier();
				debugKey.setAltKey(true);
				debugKey.setKeyName("C");
	
				Page.registerKey(debugKey, new KeyCallback() {
					public void execute(String keyName) {
						SC.showConsole();
						SC.debugger();
					}
				});
				
				
				
				KeyIdentifier testRunnerKey = new KeyIdentifier();
				testRunnerKey.setAltKey(true);
				testRunnerKey.setKeyName("T");
	
				Page.registerKey(testRunnerKey, new KeyCallback() {
					public void execute(String keyName) {
						new TestRunner(new CollaborationFileMessageTest());
					}
				});
				
				
				
				
			GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				
				@Override
				public void onUncaughtException(Throwable e) {
					
					
					Console.logException(e);
					
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
		
		Timer t = new Timer(){

			@Override
			public void run() {
				new TestRunner(new CollaborationFileMessageTest());
			}
			
			
		};
		
		t.schedule(4000);
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
	
