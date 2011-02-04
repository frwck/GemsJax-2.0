package org.gemsjax.client.desktopenviroment;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VStack;



/**
 * This is a simple class, that realize a Drop-Down-Menu, by moving the mouse over an image.
 * @author Hannes Dorfmann
 *
 */
public class DropDownMenuButton extends Layout {
	
	private DropDownMenu dropDownMenu;
	private boolean mouseOver;
	private String hoverClass;
	private String notHoverClass;
	
	private Canvas buttonElement;
	
	public DropDownMenuButton(Canvas buttonElement)
	{
		this.buttonElement = buttonElement;
		this.addMember(buttonElement);
		configureSettings();
		
	}
	
	
	public Canvas getButtonElement()
	{
		return this.buttonElement;
	}
	
	
	/**
	 * Configure the elementes and set the handlers
	 */
	private void configureSettings()
	{
		hoverClass = null;
		notHoverClass = this.getStyleName();
		
		// settings
		mouseOver = false;
		dropDownMenu = new DropDownMenu();
		
		
		
		// Register mouse Handlers
		this.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				onMouseOverButton(event);
			}
		});
		
		this.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				onMouseOutButton(event);
			}
		});
		
		
		// Register MouseOutHandler for DropDownMenu
		dropDownMenu.addMouseOutHandler(new MouseOutHandler(){

			@Override
			public void onMouseOut(MouseOutEvent event) {
				hideDropDownMenu();
			}});
	}
	
	
	/**
	 * When the mouse is over the Canvas
	 * @param event
	 */
	private void onMouseOverButton(MouseOverEvent event)
	{
		mouseOver = true;
		showDropDownMenu();
	}
	
	
	/**
	 * When the mouse was moved out
	 * @param event
	 */
	private void onMouseOutButton(MouseOutEvent event)
	{
		mouseOver = false;
		hideDropDownMenu();
		
	}
	
	/**
	 * Set the class, which should be displayed, when the mouse is over
	 * @param cssClassName
	 */
	public void setHoverClass(String cssClassName)
	{
		this.hoverClass = cssClassName;
	}
	
	/**
	 * Is the Mouse over the Image
	 * @return
	 */
	public boolean isMouseOver()
	{
		return mouseOver;
	}
	
	
	/**
	 * Show the DropDownMenu and change to hoverClass 
	 * @see #setHoverClass(String)
	 */
	private void showDropDownMenu()
	{
		dropDownMenu.bringToFront();
		dropDownMenu.animateShow(AnimationEffect.SLIDE);
		dropDownMenu.draw();
		
		
		if (hoverClass!=null) 
			this.setStyleName(hoverClass);
	}
	
	/**
	 * Hide the DropDownMenu and removes the hoveClass
	 * @see #setHoverClass(String)
	 */
	private void hideDropDownMenu()
	{
		new Timer (){
			
			@Override
			public void run() 
			{
				
				if (!dropDownMenu.isMouseOver() && !isMouseOver())
				{
					dropDownMenu.animateHide(AnimationEffect.SLIDE);
					DropDownMenuButton.this.setStyleName(notHoverClass);
				}
			}
		}.schedule(300);
		
		
	}
	
	/**
	 * Get the DropDownMenu
	 * @return
	 */
	public DropDownMenu getDropDownMenu()
	{
		return dropDownMenu;
	}
	
	
	
	
	/**
	 * The DropDownMenu that is used by the ImageMenuButton.<br />
	 * <b> Notice:</b> The DropDownMenu will be absolut positioned (CSS).
	 * @author Hannes Dorfmann
	 *
	 */
	public class DropDownMenu extends VStack {
		
		private boolean mouseOver;
		
		public DropDownMenu()
		{
			super();
			
			mouseOver = false;
			
			
			this.setMargin(0);
			this.setMembersMargin(0);
			this.setPadding(0);
			//this.setWidth("auto");
			
			this.addMouseOutHandler(new MouseOutHandler() {
				
				@Override
				public void onMouseOut(MouseOutEvent event) {
					mouseOver = false;
				}
			});
			
			this.addMouseOverHandler(new MouseOverHandler() {
				
				@Override
				public void onMouseOver(MouseOverEvent event) {
					mouseOver = true;
				}
			});
		}
		
		
		public void setPosition(int absolutX, int absolutY)
		{
			this.setPageLeft(absolutX);
			this.setPageTop(absolutY);
		}
		
		/**
		 * @return Is the mouse over this DropDownMenu
		 */
		public boolean isMouseOver()
		{
			return this.mouseOver;
		}
		
		
		/**
		 * Add an Item to this DropDownMenu
		 * @param item
		 */
		public void addMenuItem(Widget item)
		{
			this.addMember(item);
		}
		
		
		/**
		 * Add an Item to this DropDownMenu
		 * @param item
		 */
		public void addMenuItem(Canvas item)
		{
			this.addMember(item);
		}
		
	}
	
	
	 
}
