package org.gemsjax.client.experiment.presenter;

import java.io.IOException;

import org.gemsjax.client.admin.presenter.Presenter;
import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent;
import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent.LoadingAnimationEventType;
import org.gemsjax.client.experiment.view.ExperimentRegistrationView;
import org.gemsjax.client.module.RegistrationModule;
import org.gemsjax.client.module.handler.RegistrationModuleHandler;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;
import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class ExperimentRegistrationPresenter extends Presenter implements ClickHandler, RegistrationModuleHandler{

	private ExperimentRegistrationView view;
	private String verificationCode;
	private RegistrationModule module;
	
	public ExperimentRegistrationPresenter(EventBus eventBus, ExperimentRegistrationView view, RegistrationModule module, String verificationCode) {
		super(eventBus);
		this.view = view;
		
		this.verificationCode = verificationCode;
		this.module = module;
		bind();
	}
	
	private void bind(){
		view.getSubmitButton().addClickHandler(this);
		module.addRegistrationModuleHandler(this);
	}

	@Override
	public void onClick(ClickEvent event) {
		
		String displayName = view.getDisplayName();
		String pass1 = view.getPassword();
		String pass2 = view.getPasswordRepeated();
		
		if (!FieldVerifier.isValidDisplayedName(displayName)){
				view.showDisplayNameNotValid();
				return;
		}
		
		if (FieldVerifier.isEmpty(pass1) || !pass1.equals(pass2))
		{
			view.showPasswordMissmatch();
			return;
		}
		
		
		
		try {
			eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.SHOW, this));
			module.doExperimentRegistration(verificationCode, pass1, displayName);
		} catch (IOException e) {
			eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
			e.printStackTrace();
			view.showUnexpectedError();
		}
		
				
		
		
	}

	@Override
	public void onRegistrationFailed(RegistrationAnswerStatus status,
			String failString) {
	
		eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
		if (status!=RegistrationAnswerStatus.FAIL_EXPERIMENT_DISPLAYED_NAME_IN_USE)
			view.showUnexpectedError();
		else
			view.showDisplayedNameAlreadyUsed();
	}

	@Override
	public void onRegistrationSuccessful() {
		view.showSuccessful();
//		eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
		
	}

	@Override
	public void onError(Throwable t) {
		eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
		view.showUnexpectedError();
		
	}
	

}
