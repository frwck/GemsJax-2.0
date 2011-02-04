package org.gemsjax.client.view;

import org.gemsjax.client.presenter.LoginPresenter;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;

/**
 * This View is used to show the Login Dialog
 * @author Hannes Dorfmann
 *
 */
public interface LoginView extends LanguageChangeableView {
	

	/**
	 * Get the Login button, where the {@link LoginPresenter} can register an {@link ClickHandler} for it.
	 * @return
	 */
	public HasClickHandlers getLoginButton();
	
	/**
	 * Get the new registration button, where the {@link LoginPresenter} can register an {@link ClickHandler} for it.
	 * @return
	 */
	public HasClickHandlers getNewRegistrationButton();
	
	/**
	 * Get the forgot password button, where the {@link LoginPresenter} can register an {@link ClickHandler} for it.
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
	 * Set the username which should be displayed
	 * @param username
	 */
	public void setUsername(String username);
	
}
