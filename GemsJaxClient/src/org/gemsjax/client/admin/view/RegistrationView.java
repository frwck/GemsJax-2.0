package org.gemsjax.client.admin.view;


import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;


/**
 * The view that displays some kind of a form to register new user
 * @author Hannes Dorfmann
 *
 */
public interface RegistrationView {
	
	public abstract HasClickHandlers getSubmitButton();
	
	public abstract String getUsername();
	
	public abstract String getPassword();
	
	public abstract String getPasswordRepeated();
	
	public abstract String getEmail();
	
	public abstract void showErrorMessage(String msg);
	
	
}
