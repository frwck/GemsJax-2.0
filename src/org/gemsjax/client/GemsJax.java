package org.gemsjax.client;

import org.gemsjax.client.admin.AdminApplicationController;

import com.google.gwt.core.client.EntryPoint;
<<<<<<< HEAD
=======
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.util.KeyCallback;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;
>>>>>>> 6e39b6f63e9e5ce0f0ec81e97e49f26d82589248

public class GemsJax implements EntryPoint {

	@Override
	public void onModuleLoad() {
		AdminApplicationController.getInstance().start();
<<<<<<< HEAD
	}

=======
		
		
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
	
>>>>>>> 6e39b6f63e9e5ce0f0ec81e97e49f26d82589248
}
