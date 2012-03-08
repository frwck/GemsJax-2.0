package org.gemsjax.client.admin.presenter.handler;

import org.gemsjax.client.admin.presenter.event.ShowNotificationRequestCenterRequiredEvent;

import com.google.gwt.event.shared.EventHandler;

public interface ShowNotificationRequestCenterHandler extends EventHandler{
	
	
	public void onShowNotificationRequestCenterRequired(ShowNotificationRequestCenterRequiredEvent event);

}
