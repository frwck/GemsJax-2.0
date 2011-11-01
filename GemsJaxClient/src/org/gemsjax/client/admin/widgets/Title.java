package org.gemsjax.client.admin.widgets;

import com.smartgwt.client.widgets.Label;
/**
 * This is a Title Widget. It should be used to display a section title/caption.
 * See the CSS style class widget-title
 * @author Hannes Dorfmann
 *
 */
public class Title extends Label{

	public Title(String title)
	{
		super(title);
		this.setBaseStyle("widget-title");
		this.setHeight(25);
	}
	
	/**
	 * Set the title
	 * @see #setTitle(String)
	 */
	public void setTitle(String title)
	{
		this.setContents(title);
	}
	
	/**
	 * Get the title
	 * @see #getContents()
	 */
	public String getTitle()
	{
		return this.getContents();
	}
	
	
}
