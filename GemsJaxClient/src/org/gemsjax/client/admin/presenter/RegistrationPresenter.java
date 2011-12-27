package org.gemsjax.client.admin.presenter;

import java.io.IOException;

import org.gemsjax.client.admin.presenter.event.ShowRegistrationEvent;
import org.gemsjax.client.admin.presenter.handler.ShowRegistrationHandler;
import org.gemsjax.client.admin.view.RegistrationView;
import org.gemsjax.client.communication.HttpPostCommunicationConnection;
import org.gemsjax.client.communication.channel.RegistrationChannel;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.communication.message.system.NewRegistrationMessage;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class RegistrationPresenter extends Presenter implements ShowRegistrationHandler{

	private RegistrationView view;
	private RegistrationChannel channel;
	
	
	public RegistrationPresenter(RegistrationView view, EventBus eventBus) {
		super(eventBus);
		
		this.view = view;
		try {
			this.channel = new RegistrationChannel(new HttpPostCommunicationConnection("/servlets/registration"));
		} catch (IOException e) {
			
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
		
		if (FieldVerifier.isEmpty(view.getPassword()) || !view.getPassword().equals(view.getPasswordRepeated()))
		{
			view.showErrorMessage(view.getCurrentLanguage().RegistrationPasswordMismatch());
			return;
		}
		
		try {
			channel.send(new NewRegistrationMessage(view.getUsername(), view.getPassword(), view.getEmail()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void onShowRegistrationEvent(ShowRegistrationEvent event) {
		SC.logWarn("ShowRegistrationEvent received");
		view.show();
		view.bringToFront();
	}

}
