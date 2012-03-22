package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.CriticalErrorHandler;

import com.google.gwt.event.shared.GwtEvent;

/**
 * The Event 
 * @author Hannes Dorfmann
 *
 */
public class CriticalErrorEvent extends GwtEvent<CriticalErrorHandler> {
	
	
	public enum CriticalErrorType{
		/**
		 * The Live connection to the server has been closed
		 */
		LIVE_CONNECTION_CLOSED,
		/**
		 * Get all Friends, which is required at the beginning of the authentication, has failed
		 */
		INITIAL_ALL_FRIENDS,
		/**
		 * Get all Notifications and request went completely wrong
		 */
		INITAIL_NOTIFICATION_REQUEST,
		
		/**
		 * The user is no longer logged in
		 */
		AUTHENTICATION
	}
	
	
	private CriticalErrorType type;
	
	public CriticalErrorEvent(CriticalErrorType t)
	{
		this.type = t;
	}
	
	
	public CriticalErrorType getErrorType()
	{
		return type;
	}
	  
	public static Type<CriticalErrorHandler> TYPE = new Type<CriticalErrorHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CriticalErrorHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CriticalErrorHandler handler) {
	
		handler.onCriticalError(this);
	}
}
