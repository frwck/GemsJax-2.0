package org.gemsjax.client.admin.widgets;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;

public class OptionButton extends Label{
	
	private boolean isActive;
	private boolean isMouseOver;
	
	
	public OptionButton(String text)
	{
		super();
		isActive = false;
		isMouseOver = false;
		
		this.setHeight(40);
		this.setWidth(50);
		this.setMargin(5);
		
		this.setBaseStyle("widget-option-button");
		this.setContents(text);
		this.setValign(VerticalAlignment.CENTER);
		this.setHeight(60);
		
		this.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				isMouseOver = true;
				if (!isActive)
				{
					OptionButton.this.setBaseStyle("widget-option-button-mouseover");
				}
			}
		});
		
		
		this.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				
				isMouseOver = false;
				if (!isActive)
				{
					OptionButton.this.setBaseStyle("widget-option-button");
		
				}
				
			}
		});
	}
	
	
	public OptionButton(String text, ClickHandler clickHandler)
	{
		this(text);
		this.addClickHandler(clickHandler);
	}

	
	
	
	
	
	/**
	 * Creates a BigMenuButton with an icon
	 * @param text
	 * @param iconURL
	 * @param clickHandler
	 */
	public OptionButton(String text, String iconURL, ClickHandler clickHandler)
	{
		this("<img src=\""+iconURL+"\" alt=\""+text+"\" /><br />"+text);
		this.addClickHandler(clickHandler);
	}
	
	
	/**
	 * Creates a BigMenuButton with an icon
	 * @param text
	 * @param iconURL
	 * @param clickHandler
	 */
	public OptionButton(String text, String iconURL)
	{
		this("<img src=\""+iconURL+"\" alt=\""+text+"\" /><br />"+text);
		
	}
	
	
	public void setText(String text){
		this.setContents(text);
	}
	
	public void setTextIcon(String text, String iconUrl){
		this.setContents("<img src=\""+iconUrl+"\" alt=\""+text+"\" /><br />"+text);
	}


	/**
	 * Sets this Element as the Active one, which means that it will be displayed with the style widget-option-button-active
	 * @param active
	 */
	public void setActive(boolean active)
	{
		
		isActive = active;
		
		if (active)
		{
			this.setBaseStyle("widget-option-button-active");
			
		}
		else
		{
			if (isMouseOver)
				this.setBaseStyle("widget-option-button-mouseover");
			else
				this.setBaseStyle("widget-option-button");
		
			
			isActive = false;
			// NOT needed yet
			// fireEvent(new BigMenuButtonChangedEvent(BigMenuButtonChangedEventType.NOT_LONGER_ACTIVE, this));
		}
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
