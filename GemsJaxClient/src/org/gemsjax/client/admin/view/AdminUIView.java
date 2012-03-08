package org.gemsjax.client.admin.view;

import com.smartgwt.client.widgets.events.HasClickHandlers;

public interface AdminUIView {

	/**
	 * Get the clickable, for the menu item, which represents the Experiment menu Item
	 * @return
	 */
	public HasClickHandlers getUserMenuExperiments();
	
	/**
	 * Get the clickable menu item, that represents the MetaModels menu item
	 * @return
	 */
	public HasClickHandlers getUserMenuMetaModels();
	
	/**
	 * Get the clickable menu item, that represents the settings menu item
	 * @return
	 */
	public HasClickHandlers getUserMenuSettings();
	
	/**
	 * Get the clickable menu item, that represents the logout menu item
	 * @return
	 */	
	public HasClickHandlers getUserMenuLogout();
	
	
	/**
	 * Get the clickable menu item, that represents the Notifications menu item
	 * @return
	 */
	public HasClickHandlers getUserMenuNotificationRequestCenter();
	
	/**
	 * show the whole user interface
	 */
	public void show();
	
	/**
	 * hide the whole user interface
	 */
	public void hide();
	
	
	/**
	 * Set the username of the user which is already logged in
	 * @param username
	 
	public void setUsername(String username);
	*/
	
}
