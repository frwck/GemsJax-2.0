package org.gemsjax.client;

import org.gemsjax.client.admin.AdminApplicationController;

import com.google.gwt.core.client.EntryPoint;

public class GemsJax implements EntryPoint {

	@Override
	public void onModuleLoad() {
		AdminApplicationController.getInstance().start();
	}

}
