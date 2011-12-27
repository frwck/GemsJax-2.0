package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.LoadingAnimationEventHandler;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * A LoadingEvent inform {@link LoadingAnimationEventHandler}s that a loading Overlay should displayed
 * @author Hannes Dorfmann
 *
 */
public class LoadingAnimationEvent extends GwtEvent<LoadingAnimationEventHandler> {
	  
	public static Type<LoadingAnimationEventHandler> TYPE = new Type<LoadingAnimationEventHandler>();
	  
	
	/**
	 * A {@link LoadingAnimationEvent} can a {@link #SHOW}-Event or a {@link #HIDE}-Event
	 * @author Hannes Dorfmann
	 *
	 */
	public enum LoadingAnimationEventType
	{
		/**
		 * Show the loading ovelay
		 */
		SHOW,
		
		/**
		 * Hide the loading overlay
		 */
		HIDE
	}
	
	private LoadingAnimationEventType type;
	private Object source;
	private String displayingMessage;
	
	
	public LoadingAnimationEvent (LoadingAnimationEventType type, Object source)
	{
		this.type = type;
		this.source = source;
		this.displayingMessage = "";
	}
	
	public LoadingAnimationEvent (LoadingAnimationEventType type, Object source, String displayingMessage)
	{
		this.type = type;
		this.source = source;
		this.displayingMessage = displayingMessage;
	}
	
	/**
	 * Get the source {@link Object} that has fired this event
	 */
	public Object getSource()
	{
		return source;
	}
	
	
	/**
	 * Get the {@link LoadingAnimationEventType}
	 * @return
	 */
	public LoadingAnimationEventType getType()
	{
		return type;
	}
	
	
	/**
	 * Get the Message which should be displayed in the LoadingView
	 * @return
	 */
	public String getDisplayingMessage()
	{
		return displayingMessage;
	}



	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoadingAnimationEventHandler> getAssociatedType() {
		return TYPE;
	}



	@Override
	protected void dispatch(LoadingAnimationEventHandler handler) {
		handler.onLoadingAnimationEvent(this);
	}

}
