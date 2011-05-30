package org.gemsjax.client.admin.notification;

import com.smartgwt.client.widgets.layout.VStack;

/**
 * The basic class for {@link Notification}s
 * @author Hannes Dorfmann
 *
 */
public class Notification extends VStack{
	
	public enum NotificationPosition
	{
		CENTER,
		BOTTOM_CENTERED,
		BOTTOM_LEFT,
		BOTTOM_RIGHT
	}

	
	public Notification()
	{
		super();
	}
	
}
