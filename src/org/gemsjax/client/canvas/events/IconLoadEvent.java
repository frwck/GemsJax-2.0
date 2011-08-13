package org.gemsjax.client.canvas.events;

import org.gemsjax.client.canvas.IconLoadable;

/**
 * Used to inform
 * @author Hannes Dorfmann
 *
 */
public class IconLoadEvent {

	private String url;
	private IconLoadable source;
	
	public IconLoadEvent(IconLoadable soruce, String url)
	{
		this.url = url;
	}
	
	
	public String getUrl()
	{
		return url;
	}
	
	public IconLoadable getSource()
	{
		return source;
	}
	
}
