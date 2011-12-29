package org.gemsjax.client.admin.view;


import org.gemsjax.client.admin.UserLanguage;

import com.smartgwt.client.widgets.events.HasClickHandlers;


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
	
	public void show();
	
	public void hideIt();
	
	/**
	 * Clears the form, that means, that the input fields will be set to there default value (normally empty).
	 */
	public abstract void clearForm();
	
	public abstract void bringToFront();
	
	public abstract UserLanguage getCurrentLanguage();
	
	/**
	 * Validate the input form with GUI feedback
	 * @return
	 */
	public abstract boolean doGuiValidate();
	
	public abstract void showSuccessfulRegistrationMessage();
	
	/**
	 * Show an error message
	 * @param t
	 */
	public abstract void showUnexpectedError(Throwable t);
	
	
	
}
