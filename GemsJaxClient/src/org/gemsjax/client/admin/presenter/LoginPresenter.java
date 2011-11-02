package org.gemsjax.client.admin.presenter;

import org.gemsjax.client.admin.AdminApplicationController;
import org.gemsjax.client.admin.event.LoadingAnimationEvent;
import org.gemsjax.client.admin.event.LoadingAnimationEvent.LoadingAnimationEventType;
import org.gemsjax.client.admin.event.LoginEvent;
import org.gemsjax.client.admin.event.LoginEvent.LoginEventType;
import org.gemsjax.client.admin.event.LogoutEvent;
import org.gemsjax.client.admin.handler.LoginHandler;
import org.gemsjax.client.admin.handler.LogoutHandler;
import org.gemsjax.client.admin.view.LoginView;
import org.gemsjax.shared.FieldVerifier;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class LoginPresenter extends Presenter implements LogoutHandler, LoginHandler{

	private EventBus eventBus;
	private LoginView view;
	
	public LoginPresenter(EventBus eventBus, LoginView view, HasWidgets container)
	{
		super(eventBus);
		this.eventBus = eventBus;
		eventBus.addHandler(LogoutEvent.TYPE, this);
		eventBus.addHandler(LoginEvent.TYPE, this);
		this.view = view;
		container.add(view.asWidget());
		bind();
		view.resetView();
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
		
		view.setLoginButtonEnabled(false);
		
		if (!FieldVerifier.isValidUsername(view.getUsername()) )
		{
			
			SC.warn(AdminApplicationController.getInstance().getLanguage().IsNotValidUsernameMessage(), new BooleanCallback() {
				
				@Override
				public void execute(Boolean value) {
					view.setFocusOnUsernameField();				
					view.setLoginButtonEnabled(true);
				}
			});
			
			
			
		}
		else
			if (!FieldVerifier.isNotEmpty(view.getPassword()))
			{
				SC.warn(AdminApplicationController.getInstance().getLanguage().PasswordIsEmptyMessage(), new BooleanCallback() {
					
					@Override
					public void execute(Boolean value) {
						view.setFocusOnPasswordField();				
						view.setLoginButtonEnabled(true);
					}
				});
			}
			else
				//TODO remove login demo
				simulateCorrectLogin();
			
		
	}


	@Override
	public void onLogout(LogoutEvent event) {
		view.bringToFront();
		view.setUsername(event.getLastLogedInUsername());
		//TODO maybe display somewhere the reason for the logout
	}


	@Override
	public void onLogin(LoginEvent event) {
		
		if (event.wasSuccessful())
			view.hide();
		
		// TODO login incorrect
	}
	
	
	private void simulateCorrectLogin()
	{
		eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.SHOW, this, AdminApplicationController.getInstance().getLanguage().WaitWhileLoginIn()));
		new Timer(){

			@Override
			public void run() {
				eventBus.fireEvent(new LoginEvent(view.getUsername(), LoginEventType.SUCCESSFUL));
				eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, LoginPresenter.this));
			}}.schedule(1000);
		
	}
	
	
	
	
}