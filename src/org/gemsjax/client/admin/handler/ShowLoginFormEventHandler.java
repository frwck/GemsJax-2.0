package org.gemsjax.client.admin.handler;

import org.gemsjax.client.admin.event.ShowLoginFormEvent;

import com.google.gwt.event.shared.EventHandler;

public interface ShowLoginFormEventHandler extends EventHandler {

	public void onShowLoginForm(ShowLoginFormEvent event);
}
