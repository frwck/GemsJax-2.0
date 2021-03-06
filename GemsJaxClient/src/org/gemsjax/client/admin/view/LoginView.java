package org.gemsjax.client.admin.view;

import org.gemsjax.client.admin.presenter.AuthenticationPresenter;
import org.gemsjax.shared.communication.message.system.LoginMessage;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;

/**
 * This View is used to show the Login Dialog
 * @author Hannes Dorfmann
 *
 */
public interface LoginView {
	

	/**
	 * Get the Login button, where the {@link AuthenticationPresenter} can register an {@link ClickHandler} for it.
	 * @return
	 */
	public HasClickHandlers getLoginButton();
	
	/**
	 * Get the new registration button, where the {@link AuthenticationPresenter} can register an {@link ClickHandler} for it.
	 * @return
	 */
	public HasClickHandlers getNewRegistrationButton();
	
	/**
	 * Get the forgot password button, where the {@link AuthenticationPresenter} can register an {@link ClickHandler} for it.
	 * @return
	 */
	public HasClickHandlers getForgotPasswordButton();
	
	
	/**
	 * Get the Username
	 * @return
	 */
	public String getUsername();
	
	/**
	 * Get the Password
	 * @return
	 */
	public String getPassword();
	
	
	/**
	 * Get the View as {@link Widget}. So you can simply add / or remove this view from the {@link RootPanel}
	 * @return
	 */
	public Widget asWidget();
	
	
	/**
	 * Bring this View in Front and block all other visual widgets by adding an overlay
	 */
	public void bringToFront();
	
	/**
	 * Hide the LoginView. This should be normally be done, when the Login was successful
	 */
	public void hide();
	
	
	/**
	 * Set the username which should be displayed
	 * @param username
	 */
	public void setUsername(String username);
	
	/**
	 * Set the Focus on the username field
	 */
	public void setFocusOnUsernameField();
	
	/**
	 * Set the Focus on the password field
	 */
	public void setFocusOnPasswordField();
	
	
	/**
	 * Set the login button to be clickable or not.
	 * @param enable
	 */
	public void setLoginButtonEnabled(boolean enable);
	
	/**
	 * Reset the view. That means, that all input fields will be set to the default value
	 */
	public void resetView();
	
	/**
	 * Display a error message, if sending a {@link LoginMessage} to the server has failed
	 */
	public void showSendError();
	
	/**
	 * Display an error message, beacause the login with the submited username and password has failed.
	 */
	public void showLoginFailed();
	
	/**
	 * Display a message, that says that an unexpected error has occurred
	 */
	public void showUnexpectedError();
	
}
