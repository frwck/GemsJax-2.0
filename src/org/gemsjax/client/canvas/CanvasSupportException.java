package org.gemsjax.client.canvas;

/**
 * This Exception will be thrown if the HTML5 canvas Tag is not supported by the browser
 * @author Hannes Dorfmann
 *
 */
public class CanvasSupportException extends Exception{

	public CanvasSupportException(String msg)
	{
		super(msg);
	}
	
	
}
