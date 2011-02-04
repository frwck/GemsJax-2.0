package org.gemsjax.client.presenter;

import org.gemsjax.client.event.ShowLoginFormEvent;
import org.gemsjax.client.handler.ShowLoginFormEventHandler;
import org.gemsjax.client.view.LoginView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class LoginPresenter extends Presenter implements ShowLoginFormEventHandler{

	private EventBus eventBus;
	private LoginView view;
	
	public LoginPresenter(EventBus eventBus, LoginView view, HasWidgets container)
	{
		super(eventBus);
		this.eventBus = eventBus;
		this.view = view;
		container.add(view.asWidget());
		bind();
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
		
	}


	@Override
	public void onShowLoginForm(ShowLoginFormEvent event) {
		view.bringToFront();
		
		if (event.getPreviousLoggedInUsername()!=null)
			view.setUsername(event.getPreviousLoggedInUsername());
		
	}
	
	
	
	
	
	
	
}
