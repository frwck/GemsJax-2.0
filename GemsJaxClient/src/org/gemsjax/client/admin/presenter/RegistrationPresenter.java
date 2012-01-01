package org.gemsjax.client.admin.presenter;

import java.io.IOException;

import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent;
import org.gemsjax.client.admin.presenter.event.ShowRegistrationEvent;
import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent.LoadingAnimationEventType;
import org.gemsjax.client.admin.presenter.handler.ShowRegistrationHandler;
import org.gemsjax.client.admin.view.RegistrationView;
import org.gemsjax.client.communication.HttpPostCommunicationConnection;
import org.gemsjax.client.communication.channel.RegistrationChannel;
import org.gemsjax.client.communication.channel.handler.RegistrationChannelHandler;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.ServletPaths;
import org.gemsjax.shared.communication.message.system.NewRegistrationMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;


public class RegistrationPresenter extends Presenter implements ShowRegistrationHandler, RegistrationChannelHandler{

	private RegistrationView view;
	private RegistrationChannel channel;
	
	
	public RegistrationPresenter(RegistrationView view, EventBus eventBus) {
		super(eventBus);
		
		this.view = view;
		try {
			this.channel = new RegistrationChannel(new HttpPostCommunicationConnection(ServletPaths.REGISTRATION));
			this.channel.addRegistrationChannelHandler(this);
		} catch (IOException e) {
			e.printStackTrace(); // Should never be reached
		}
		bind();
	}
	
	
	private void bind()
	{
		eventBus.addHandler(ShowRegistrationEvent.TYPE, this);
		
		view.getSubmitButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				onSubmitClicked();
			}
		});
	}
	
	
	
	private void onSubmitClicked()
	{
		view.doGuiValidate();
		
		SC.warn("Username " + view.getUsername() +" Password " + view.getPassword());
		
		if (!FieldVerifier.isValidUsername(view.getUsername()))
		{
			view.showErrorMessage(view.getCurrentLanguage().RegistrationInvalidUsername());
			return;
		}
		
		if (!FieldVerifier.isValidEmail(view.getEmail()))
		{
			view.showErrorMessage(view.getCurrentLanguage().RegistrationInvalidEmail());
			return;
		}
		
	
		
		if (FieldVerifier.isEmpty(view.getPassword()) || FieldVerifier.isEmpty(view.getPasswordRepeated()) || !view.getPassword().equals(view.getPasswordRepeated()))
		{
			view.showErrorMessage(view.getCurrentLanguage().RegistrationPasswordMismatch());
			return;
		}
		
		
		try {
			
			eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.SHOW, this));
			channel.send(new NewRegistrationMessage(URL.encode(view.getUsername()), URL.encode(view.getPassword()), URL.encode(view.getEmail())));
			
		} catch (IOException e) {
			e.printStackTrace();
			eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
			view.showUnexpectedError(e);
		}
	}


	@Override
	public void onShowRegistrationEvent(ShowRegistrationEvent event) {
		view.clearForm();
		view.show();
		view.bringToFront();
	}


	@Override
	public void onRegistrationFailed(RegistrationAnswerStatus status, String fail) {
		
		eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
		
		switch (status)
		{
			case FAIL_EMAIL: 	view.bringToFront();
								view.showErrorMessage(view.getCurrentLanguage().RegistrationFailEmail());
								break;
								
			case FAIL_INVALID_EMAIL: 	view.bringToFront();
										view.showErrorMessage(view.getCurrentLanguage().RegistrationFailInvalidEmail());
										break;
			
			case FAIL_USERNAME:	view.bringToFront();
								view.showErrorMessage(view.getCurrentLanguage().RegistrationFailUsername());
								break;
								
			case FAIL_INVALID_USERNAME:	view.bringToFront();
										view.showErrorMessage(view.getCurrentLanguage().RegistrationFailInvalidUsername());
										break;
						
		
						
			default:	view.showErrorMessage("Unexpected Error. Please retry.");
						break;
		}
	}


	@Override
	public void onRegistrationSuccessful() {
		eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
		
		view.hideIt();
		view.clearForm();
		view.showSuccessfulRegistrationMessage();
		
	}


	@Override
	public void onError(Throwable t) {
		
		eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
		view.showUnexpectedError(t);
		
	}

}
