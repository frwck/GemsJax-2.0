package org.gemsjax.client;

import org.gemsjax.client.adminui.AdminApplicationViewImpl;
import org.gemsjax.client.desktopenviroment.DesktopEnviromentUI;
import org.gemsjax.client.event.LoadingAnimationEvent;
import org.gemsjax.client.model.language.LanguageManager;
import org.gemsjax.client.presenter.AdminApplicationPresenter;
import org.gemsjax.client.presenter.CreateExperimentPresenter;
import org.gemsjax.client.presenter.LoadingPresenter;
import org.gemsjax.client.presenter.LoginPresenter;
import org.gemsjax.client.presenter.Presenter;
import org.gemsjax.client.view.LoadingView;
import org.gemsjax.client.view.implementation.CreateExperimentViewImpl;
import org.gemsjax.client.view.implementation.LoadingViewImpl;
import org.gemsjax.client.view.implementation.LoginViewImpl;
import org.gemsjax.client.websocket.WebSocket;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * The AdminApplicationController controls the presenters and the History and will listen to the {@link WebSocket} for the server-client communication
 * 
 * @author Hannes Dorfmann
 *
 */
public class AdminApplicationController {
	
	/**
	 * Singleton instance
	 */
	private static AdminApplicationController instance;
	
	/**
	 * All {@link Presenter}s can communicate with each other an this {@link AdminApplicationController} by firing Events via this {@link EventBus}
	 */
	private EventBus eventBus;
	
	/**
	 * A LoadingPresenter is always open in the background and can show or hide a {@link LoadingView} by receiving {@link LoadingAnimationEvent}
	 */
	private LoadingPresenter loadingPresenter;
	
	/**
	 * A LoginView is always open in the background and show a simple login view 
	 */
	private LoginPresenter loginPresenter;
	
	
	private AdminApplicationController()
	{
		eventBus = new SimpleEventBus();
		LanguageManager.getInstance().setEventBus(eventBus);
	}

	
	
	public EventBus getEventBus()
	{
		return this.eventBus;
	}
	
	
	
	public static AdminApplicationController getInstance()
	{
		if (instance == null)
			instance = new AdminApplicationController();
		
		return instance;
	}
	
	
	/**
	 * Start the "admin" app with the {@link LoadingPresenter} and the {@link LoginPresenter}
	 */
	public void start()
	{
		//loginPresenter = new LoginPresenter(eventBus, new LoginViewImpl(), RootPanel.get());
		//loadingPresenter = new LoadingPresenter(eventBus, new LoadingViewImpl());
		// AdminApplicationViewImpl.getInstance();
		new AdminApplicationPresenter(eventBus, AdminApplicationViewImpl.getInstance());
		
		// TODO remove Demo
		//new CreateExperimentPresenter(eventBus, new CreateExperimentViewImpl(LanguageManager.getInstance().getCurrentLanguage()));
			
	}
	
	
	
}
