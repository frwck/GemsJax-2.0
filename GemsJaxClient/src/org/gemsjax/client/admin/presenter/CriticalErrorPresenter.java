package org.gemsjax.client.admin.presenter;

import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent;
import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent.CriticalErrorType;
import org.gemsjax.client.admin.presenter.handler.CriticalErrorHandler;
import org.gemsjax.client.admin.view.CriticalErrorView;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.CommunicationConnection.ClosedListener;

import com.google.gwt.event.shared.EventBus;


/**
 * The {@link CriticalErrorPresenter} is responsible for 
 * @author Hannes Dorfmann
 *
 */
public class CriticalErrorPresenter extends Presenter implements CriticalErrorHandler, ClosedListener{

	private CriticalErrorView view;
	
	public CriticalErrorPresenter(EventBus eventBus, CriticalErrorView view, CommunicationConnection liveCommunication) {
		super(eventBus);
		this.view = view;
		liveCommunication.addCloseListener(this);
		
		eventBus.addHandler(CriticalErrorEvent.TYPE, this);
	}

	@Override
	public void onCriticalError(CriticalErrorEvent event) {
		switch (event.getErrorType())
		{
			case LIVE_CONNECTION_CLOSED: view.setErrorText("The live connection to the server has been lost!"); break;
			case INITIAL_ALL_FRIENDS: view.setErrorText("Could not load your friends."); break;
			case INITAIL_NOTIFICATION_REQUEST: view.setErrorText("Could not load your notifications or requests"); break;
			case AUTHENTICATION: view.setErrorText("You are not authenticated. Log in!");break;
			case EXPERIMENT_VERIFICATION: view.setErrorText("Incorrect experiment URL! Please only use the URL of your experiment invitation e-mail"); break;
			case CANVAS_NOT_SUPPORTED: view.setErrorText("HTML5 Canvas element is not supported by your browser"); break;
		}
		
		view.show();
		// TODO maybe stop the app somehow, that no other presenter can be bring its view to the front of this CriticalErrorView
		
		
	}

	@Override
	public void onClose(CommunicationConnection connection) {
		onCriticalError(new CriticalErrorEvent(CriticalErrorType.LIVE_CONNECTION_CLOSED));
	}
	

}
