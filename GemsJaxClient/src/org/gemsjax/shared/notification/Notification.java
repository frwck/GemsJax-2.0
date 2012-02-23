package org.gemsjax.shared.notification;

import java.util.Date;

import org.gemsjax.shared.user.RegisteredUser;

/**
 * This is the base interface for {@link Notification}s.
 * A {@link Notification} is a simple message, generated from the system to inform the User about something.
 * 
 * @author Hannes Dorfmann
 *
 */
public interface Notification {
	
	public long getId();
	public Date getDate();
	public void setDate(Date date);
	
	public void setRead(boolean read);
	public boolean isRead();
	
	public RegisteredUser getReceiver();
	
	
}
