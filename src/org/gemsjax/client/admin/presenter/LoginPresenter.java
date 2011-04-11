package org.gemsjax.client.admin.presenter;

import org.gemsjax.client.admin.event.LoginEvent;
import org.gemsjax.client.admin.event.LoginEvent.LoginEventType;
import org.gemsjax.client.admin.event.LogoutEvent;
import org.gemsjax.client.admin.handler.LogoutHandler;
import org.gemsjax.client.admin.view.LoginView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class LoginPresenter extends Presenter implements LogoutHandler{

	private EventBus eventBus;
	private LoginView view;
	
	public LoginPresenter(EventBus eventBus, LoginView view, HasWidgets container)
	{
		super(eventBus);
		this.eventBus = eventBus;
		eventBus.addHandler(LogoutEvent.TYPE, this);
		this.view = view;
		container.add(view.asWidget());
		bind();
		// We start by displaying the login form
		view.bringToFront();
	}
	
	
	/**
	 * 
	 * Bind the {@link ShowLoginFormEventHandler} to this Presenter
	 * and bind the {@link ClickHandler} to the view
	 */
	private void bind()
	{

		view.getForgotPasswordButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				onForgotPasswordClicked();
			}
		});
		
		
		view.getLoginButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				onLoginClicked();
			}
		});
		
		
		view.getNewRegistrationButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				onNewRegistrationClicked();
			}
		});
	}
	
	
	private void onNewRegistrationClicked()
	{
		
	}
	
	private void onForgotPasswordClicked()
	{
		
	}
	
	
	private void onLoginClicked()
	{
		//TODO remove login demo
		eventBus.fireEvent(new LoginEvent(view.getUsername(), LoginEventType.SUCCESSFUL));
		
	}


	@Override
	public void onLogout(LogoutEvent event) {
		view.bringToFront();
		view.setUsername(event.getLastLogedInUsername());
		//TODO maybe add the reason for the logout
	}
	
	
	
	
	
	
	
}
