package org.gemsjax.client;

import org.gemsjax.client.desktopenviroment.DesktopEnviromentUI;
import org.gemsjax.client.event.LanguageChangedEvent;
import org.gemsjax.client.event.LanguageConfigLoadEvent;
import org.gemsjax.client.event.LanguageLoadEvent;
import org.gemsjax.client.handler.LanguageChangeHandler;
import org.gemsjax.client.handler.LanguageConfigLoadHandler;
import org.gemsjax.client.handler.LanguageLoadHandler;
import org.gemsjax.client.model.language.LanguageManager;
import org.gemsjax.client.presenter.LoginPresenter;
import org.gemsjax.client.websocket.WebSocket;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.RequestException;

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
	
