package org.gemsjax.client;

import org.gemsjax.client.admin.AdminApplicationController;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.util.KeyCallback;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;

public class GemsJax implements EntryPoint {

	@Override
	public void onModuleLoad() {
		AdminApplicationController.getInstance().start();
		
		
		if (!GWT.isScript()) {
			KeyIdentifier debugKey = new KeyIdentifier();
			debugKey.setCtrlKey(true);
			debugKey.setKeyName("Q");

			Page.registerKey(debugKey, new KeyCallback() {
				public void execute(String keyName) {
					SC.showConsole();
					SC.debugger();
				}
			});
	}
		
	}
	
}
