package org.gemsjax.client.util;

import org.gemsjax.client.communication.WebSocket;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.media.client.Audio;



/**
 * This class is a little helper, that checks if the new HTML 5 elements like Canvas, WebSocket and Audio is
 * supported by the users browser.
 * This checks should be performed, before the normal app starts.
 * @author Hannes Dorfmann
 *
 */
public class BrowserSupportChecker {
	
	private BrowserSupportChecker(){}

	/**
	 * Check if the HTML5 canvas element is supported by the users browser
	 * @return
	 */
	public static boolean isCanvasSupported()
	{
		return Canvas.isSupported();
	}
	
	/**
	 * Check if the HTML5 WebSocket is supported / enabled by the users browser
	 * @return
	 */
	public static boolean isWebSocketSupported()
	{
		return WebSocket.isSupported();
	}
	
	
	/**
	 * Check if the HTML5 Audio Tag is supported by the users brwoser
	 * @return
	 */
	public static boolean isAudioSupported()
	{
		return Audio.isSupported();
	}
	
}
