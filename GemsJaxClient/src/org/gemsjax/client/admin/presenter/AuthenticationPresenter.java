package org.gemsjax.client.admin.presenter;

import java.io.IOException;

import org.gemsjax.client.admin.AdminApplicationController;
import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent;
import org.gemsjax.client.admin.presenter.event.LoginSuccessfulEvent;
import org.gemsjax.client.admin.presenter.event.LogoutRequiredEvent;
import org.gemsjax.client.admin.presenter.event.NewRegistrationRequiredEvent;
import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent.LoadingAnimationEventType;
import org.gemsjax.client.admin.presenter.handler.LogoutRequiredHandler;
import org.gemsjax.client.admin.view.LoginView;
import org.gemsjax.client.admin.view.LogoutView;
import org.gemsjax.client.communication.channel.AuthenticationChannel;
import org.gemsjax.client.communication.channel.handler.AuthenticationChannelHandler;
import org.gemsjax.client.module.AuthenticationModule;
import org.gemsjax.client.module.handler.AuthenticationModuleHandler;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.message.system.LoginAnswerMessage.LoginAnswerStatus;
import org.gemsjax.shared.communication.message.system.LogoutMessage.LogoutReason;
import org.gemsjax.shared.user.RegisteredUser;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

/**
 * The {@link AuthenticationPresenter} coordinates the underlying {@link AuthenticationChannel} and the {@link LoginView} and {@link LogoutView}.
 * @author Hannes Dorfmann
 *
 */
public class AuthenticationPresenter extends Presenter implements LogoutRequiredHandler, AuthenticationModuleHandler {

	private LoginView loginView;
	private LogoutView logoutView;
	private AuthenticationModule authenticationModule;
	
	public AuthenticationPresenter(EventBus eventBus, LoginView loginView, LogoutView logoutView, AuthenticationModule authenticationModule)
	{
		super(eventBus);
		this.authenticationModule = authenticationModule;
		authenticationModule.addAuthenticationModuleHandler(this);
		
		
		eventBus.addHandler(LogoutRequiredEvent.TYPE, this);
		this.loginView = loginView;
		this.logoutView = logoutView;
		//container.add(loginView.asWidget());
		bind();
		loginView.resetView();
		// We start by displaying the login form
		loginView.bringToFront();
	}
	
	
	/**
	 * 
	 * Bind the {@link ShowLoginFormEventHandler} to this Presenter
	 * and bind the {@link ClickHandler} to the view
	 */
	private void bind()
	{

		loginView.getForgotPasswordButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				onForgotPasswordClicked();
			}
		});
		
		
		loginView.getLoginButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				onLoginClicked();
			}
		});
		
		
		loginView.getNewRegistrationButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				onNewRegistrationClicked();
			}
		});
	}
	
	
	private void onNewRegistrationClicked()
	{
		eventBus.fireEvent(new NewRegistrationRequiredEvent());
	}
	
	private void onForgotPasswordClicked()
	{
		
	}
	
	
	private void onLoginClicked()
	{
		
		loginView.setLoginButtonEnabled(false);
		
		if (!FieldVerifier.isValidUsername(loginView.getUsername()) )
		{
			
			SC.warn(AdminApplicationController.getInstance().getLanguage().IsNotValidUsernameMessage(), new BooleanCallback() {
				
				@Override
				public void execute(Boolean value) {
					loginView.setFocusOnUsernameField();				
					loginView.setLoginButtonEnabled(true);
				}
			});
			
			
			
		}
		else
			if (!FieldVerifier.isNotEmpty(loginView.getPassword()))
			{
				SC.warn(AdminApplicationController.getInstance().getLanguage().PasswordIsEmptyMessage(), new BooleanCallback() {
					
					@Override
					public void execute(Boolean value) {
						loginView.setFocusOnPasswordField();				
						loginView.setLoginButtonEnabled(true);
					}
				});
			}
			else
			{
				// Fields are valid, do the login
				eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.SHOW, this));
				try {
					authenticationModule.doLogin(loginView.getUsername(), loginView.getPassword());
				} catch (IOException e) {
					loginView.showSendError();
					e.printStackTrace();
					
				}
			}
			
		
	}


	@Override
	public void onLogoutRequired(LogoutRequiredEvent event) {
		loginView.resetView();
		
		
		try {
			authenticationModule.doLogout();
			loginView.bringToFront();
		} catch (IOException e) {
			
			loginView.bringToFront();
			loginView.showSendError();
		}
	}
	




	@Override
	public void onLogoutReceived(LogoutReason reason) {
		loginView.resetView();
		loginView.bringToFront();
		logoutView.show(reason);
	}




	@Override
	public void onParseError(Exception e) {
		//eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
		loginView.showUnexpectedError();
		loginView.setLoginButtonEnabled(true);
	}


	@Override
	public void onLoginSuccessful(RegisteredUser authenticatedUser) {
		eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
		loginView.setLoginButtonEnabled(true);
		loginView.hide();
		eventBus.fireEvent(new LoginSuccessfulEvent(authenticatedUser));
		
	}


	@Override
	public void onLoginFailed() {
		eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
		loginView.setLoginButtonEnabled(true);
		loginView.showLoginFailed();
	}
	
	
	
	
}
