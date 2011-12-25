package org.gemsjax.client.admin.view;


import com.smartgwt.client.widgets.events.HasClickHandlers;


/**
 * The view that displays some kind of a form to register new user
 * @author Hannes Dorfmann
 *
 */
public interface RegistrationView {
	
	public abstract HasClickHandlers getRegisterButton();
	
	public abstract String getUsername();
	
	public abstract String getPassword();
	
	public abstract String getPasswordRepeated();
	
	public abstract String getEmail();
	
	public abstract void showErrorMessage(String msg);
	

}
