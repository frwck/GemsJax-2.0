package org.gemsjax.client.view;

import com.google.gwt.user.client.ui.Widget;

/**
 * A LoadingView 
 * @author Hannes Dorfmann
 *
 */
public interface LoadingView {

	/**
	 * Show the loading animation
	 */
	public void bringToFront();
	
	/**
	 * Hide the loading animation and hide the normal
	 */
	public void hideIt();
	
	
	/**
	 * Set the message, which will be displayed while loading
	 * @param text
	 */
	public void setLoadingMessage(String text);
	
	
	
	/**
	 * The view as the widget
	 * @return
	 */
	public Widget asWidget();
}
