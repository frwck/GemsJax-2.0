package org.gemsjax.client.admin.widgets;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.admin.tabs.TwoColumnLayoutTab;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;

/**
 * This is something like a Button, but its not a HTML Button, but have nearly the same behavior. This GUI widget is considered to use for simple menus, 
 * in example to use within a {@link TwoColumnLayoutTab} to implement the menu. <br />
 * The CSS style classes that are used are: widget-big-menu-button, widget-big-menu-button-mouseover and widget-big-menu-button-active
 * @author Hannes Dorfmann
 *
 */
public class BigMenuButton extends Label{
	
	public interface BigMenuButtonChangedEventHandler
	{
		public void onBigMenuButtonChanged(BigMenuButtonChangedEvent event);
	}
	
	
	/**
	 * This is a enum that represents the type of an {@link BigMenuButtonChangedEvent}
	 * @author Hannes Dorfmann
	 *
	 */
	public enum BigMenuButtonChangedEventType
	{
		/**
		 * The {@link BigMenuButton} has been set as the active one
		 * @see BigMenuButton#setActive(boolean)
		 */
		ACTIVE
		
		/*
		 * The {@link BigMenuButton} has been set to the normal state, so the {@link BigMenuButton} is not longer the active one.
		 * @see BigMenuButton#setActive(boolean)
		 */
		// Not needed yet
		//NOT_LONGER_ACTIVE
	};

	/**
	 * This is a simple Event that will be thrown to inform the {@link BigMenuButtonChangedEventHandler} that the internal state of an {@link BigMenuButton} has changed.
	 * Currently this will be done, when the {@link BigMenuButton} has been set as the active one, or has been set as "normal"
	 * @author Hannes Dorfmann
	 *
	 */
	public class BigMenuButtonChangedEvent
	{
		
		private BigMenuButtonChangedEventType type;
		private BigMenuButton source;
		
		public BigMenuButtonChangedEvent(BigMenuButtonChangedEventType type, BigMenuButton source)
		{
			this.type = type;
			this.source = source;
		}
		
		/**
		 * Get the {@link BigMenuButtonChangedEventType} of this event
		 * @return
		 */
		public BigMenuButtonChangedEventType getType()
		{
			return type;
		}
		
		/**
		 * Get the {@link BigMenuButton} that has fired this event
		 * @return
		 */
		public BigMenuButton getSource()
		{
			return source;
		}
	}
	
	
	
	
	/**
	 * This flag can be set via {@link #isActive}
	 */
	private boolean isActive;
	/**
	 * This is an internal flag to represent the internal state. It's true, when the mouse is over, otherwise false
	 */
	private boolean isMouseOver;
	
	private List<BigMenuButtonChangedEventHandler> changeHandlerList;
	
	public BigMenuButton(String text)
	{
		super();
		isActive = false;
		isMouseOver = false;
		changeHandlerList = new LinkedList<BigMenuButton.BigMenuButtonChangedEventHandler>();
		
		this.setBaseStyle("widget-big-menu-button");
		this.setContents(text);
		this.setValign(VerticalAlignment.CENTER);
		this.setHeight(60);
		
		this.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				isMouseOver = true;
				if (!isActive)
				{
					BigMenuButton.this.setBaseStyle("widget-big-menu-button-mouseover");
				}
			}
		});
		
		
		this.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				
				isMouseOver = false;
				if (!isActive)
				{
					BigMenuButton.this.setBaseStyle("widget-big-menu-button");
		
				}
				
			}
		});
	}
	
	
	public BigMenuButton(String text, ClickHandler clickHandler)
	{
		this(text);
		this.addClickHandler(clickHandler);
	}

	/**
	 * Sets this Element as the Active one, which means that it will be displayed with the style widget-big-menu-button-active
	 * @param active
	 */
	public void setActive(boolean active)
	{
		
		isActive = active;
		
		if (active)
		{
			fireEvent(new BigMenuButtonChangedEvent(BigMenuButtonChangedEventType.ACTIVE, this));
			BigMenuButton.this.setBaseStyle("widget-big-menu-button-active");
			
		}
		else
		{
			if (isMouseOver)
				this.setBaseStyle("widget-big-menu-button-mouseover");
			else
				this.setBaseStyle("widget-big-menu-button");
		
			
			isActive = false;
			// NOT needed yet
			// fireEvent(new BigMenuButtonChangedEvent(BigMenuButtonChangedEventType.NOT_LONGER_ACTIVE, this));
		}
	}
	
	
	/**
	 * Inform all {@link BigMenuButtonChangedEventHandler} that the internal state has changed by firing a {@link BigMenuButtonChangedEvent}
	 * @param event
	 */
	private void fireEvent(BigMenuButtonChangedEvent event)
	{
		for (BigMenuButtonChangedEventHandler handler : changeHandlerList)
			handler.onBigMenuButtonChanged(event);
	}
	
	/**
	 * Add a {@link BigMenuButtonChangedEventHandler}
	 * @param handler
	 */
	public void addBigMenuButtonChangedEventHandler( BigMenuButtonChangedEventHandler handler)
	{
		if (!changeHandlerList.contains(handler))changeHandlerList.add(handler);
	}
	
	/**
	 * Remove a {@link BigMenuButtonChangedEventHandler}
	 * @param handler
	 */
	public void removeBigMenuButtonChangedEventHandler( BigMenuButtonChangedEventHandler handler)
	{
		changeHandlerList.remove(handler);
	}
	
	/**
	 * Check if this Button is Active
	 * @return
	 */
	public boolean isActive()
	{
		return isActive;
	}
}
