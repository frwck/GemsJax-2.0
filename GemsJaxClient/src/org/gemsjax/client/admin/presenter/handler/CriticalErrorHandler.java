package org.gemsjax.client.admin.presenter.handler;

import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent;

import com.google.gwt.event.shared.EventHandler;

public interface CriticalErrorHandler extends EventHandler {
	
	public abstract void onCriticalError(CriticalErrorEvent event);

}
