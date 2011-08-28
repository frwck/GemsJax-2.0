package org.gemsjax.client.admin.notification;

/**
 * This Event will be fired by a {@link Notification} to a {@link NotificationHandler} to indicate that something happens with a {@link Notification} {@link #source}
 * @author Hannes Dorfmann
 *
 */
public class NotificationEvent {
	
	public enum NotificationEventType
	{
		/** This type is used to signal that a Notification  is not longer displayed */
		CLOSED
	}
	
	
	private NotificationEventType type;
	

	/** The Notification, which has fired this event */
	private Notification source;
	
	public NotificationEvent(Notification source, NotificationEventType type)
	{
		this.source = source;
		this.type = type;
	}
	
	public NotificationEventType getType() {
		return type;
	}

	public Notification getSource() {
		return source;
	}


}
