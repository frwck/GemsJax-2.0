package org.gemsjax.client.experiment;

import java.io.IOException;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.presenter.CriticalErrorPresenter;
import org.gemsjax.client.admin.presenter.LoadingPresenter;
import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent;
import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent.CriticalErrorType;
import org.gemsjax.client.admin.view.implementation.CriticalErrorViewImpl;
import org.gemsjax.client.admin.view.implementation.LoadingViewImpl;
import org.gemsjax.client.communication.HttpPostCommunicationConnection;
import org.gemsjax.client.communication.WebSocketCommunicationConnection;
import org.gemsjax.client.communication.channel.RegistrationChannel;
import org.gemsjax.client.experiment.presenter.ExperimentRegistrationPresenter;
import org.gemsjax.client.experiment.view.impl.ExperimentRegistrationViewImpl;
import org.gemsjax.client.module.RegistrationModule;
import org.gemsjax.shared.ServletPaths;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

public class ExperimentRegistrationController implements EntryPoint {
	
	private LoadingPresenter loadingPresenter;
	private ExperimentRegistrationPresenter registrationPresenter;
	private CriticalErrorPresenter errorPresenter;
	
	private EventBus eventBus;
	private UserLanguage language = GWT.create(UserLanguage.class);
	

	@Override
	public void onModuleLoad() {
		
		eventBus = new SimpleEventBus();
		
		// Loading Presenter is needed
		loadingPresenter = new LoadingPresenter(eventBus, new LoadingViewImpl());
		
		// error Presenter
		errorPresenter = new CriticalErrorPresenter(eventBus, new CriticalErrorViewImpl(), WebSocketCommunicationConnection.getInstance());
		
		
		
		String verificationCode = UrlHelper.getVerificationCode();
		
		if (verificationCode == null)
			eventBus.fireEvent(new CriticalErrorEvent(CriticalErrorType.EXPERIMENT_VERIFICATION));
		else{
			// ExperimentRegistration
			RegistrationModule module;
			try {
				module = new RegistrationModule( new RegistrationChannel(new HttpPostCommunicationConnection(ServletPaths.REGISTRATION)));
				registrationPresenter = new ExperimentRegistrationPresenter(eventBus, new ExperimentRegistrationViewImpl(language), module, verificationCode);
			} catch (IOException e) {
				eventBus.fireEvent(new CriticalErrorEvent(CriticalErrorType.LIVE_CONNECTION_CLOSED));
				e.printStackTrace();
			}
		}
	}

}
