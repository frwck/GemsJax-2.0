package org.gemsjax.client.admin.notification;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * The basic class for {@link Notification}s.
 * It also implements functionality to add and remove {@link NotificationHandler} and a method
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
	
	
	private String text;
	private String title;
	private Img image;
	

	private NotificationPosition position;
	
	
	private List<NotificationHandler> handlers;
	
	public Notification()
	{
		super();
		handlers = new ArrayList<NotificationHandler>();
	}
	
	/**
	 * Add a {@link NotificationHandler}. If a {@link NotificationHandler} was already added, the same {@link NotificationHandler} will not be added a second time
	 * @param h
	 */
	public void addNotificationHandler( NotificationHandler h)
	{
		if (!handlers.contains(h))
			handlers.add(h);
	}
	
	/**
	 * Remove a {@link NotificationHandler}. If the handler was not registered previous to this {@link Notification}, nothing will be done.
	 * @param h
	 */
	public void removeNotificationHandler(NotificationHandler h)
	{
		handlers.remove(h);
	}
	
	/**
	 * Inform all {@link NotificationHandler}s ({@link #handlers} by firing a {@link NotificationEvent} 
	 * @param event
	 */
	protected void fireNotificationEvent(NotificationEvent event)
	{
		for (NotificationHandler h : handlers)
			h.onTipNotificationEvent(event);
	}
	
	/**
	 * To compare two Notifications use this method
	 * @param other
	 * @return
	 */
	public boolean isSameAs(Notification other)
	{
		if (this.text == null && other.text!= null) 
			return false;
		
		if (this.text!=null && other.text==null)
			return false;
		
		if ((this.image == null && other.image!= null) || (this.image!=null && other.image==null))
			return false;
		
		if (this.image != null && !this.image.getSrc().equals(other.image.getSrc()))
			return false;
		
		
		if (!this.title.equals(other.title))
			return false;
		
		if (this.text != null && other.text!=null)
			if (!this.text.equals(other.text))
				return false;
		
		
		return true;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Img getImage() {
		return image;
	}

	public void setImage(Img image) {
		this.image = image;
	}

	public NotificationPosition getNotificationPosition() {
		return position;
	}

	public void setPosition(NotificationPosition position) {
		this.position = position;
	}
	
	
}
