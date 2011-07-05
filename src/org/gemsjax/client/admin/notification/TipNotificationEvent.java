package org.gemsjax.client.admin.notification;

/**
 * This Event will be fired by a {@link Notification} to a {@link TipNotificationHandler} to indicate that something happens with a {@link Notification} {@link #source}
 * @author Hannes Dorfmann
 *
 */
public class TipNotificationEvent {
	
	public enum TipNotificationEventType
	{
		/** This type is used to signal that a Notification  is not longer displayed */
		CLOSED
	}
	
	
	private TipNotificationEventType type;
	

	/** The Notification, which has fired this event */
	private TipNotification source;
	
	public TipNotificationEvent(TipNotification source, TipNotificationEventType type)
	{
		this.source = source;
		this.type = type;
	}
	
	public TipNotificationEventType getType() {
		return type;
	}

	public TipNotification getSource() {
		return source;
	}


}
