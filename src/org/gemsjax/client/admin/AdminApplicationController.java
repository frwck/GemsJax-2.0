package org.gemsjax.client.admin;

import org.gemsjax.client.admin.event.LoadingAnimationEvent;
import org.gemsjax.client.admin.presenter.AdminApplicationPresenter;
import org.gemsjax.client.admin.presenter.LoadingPresenter;
import org.gemsjax.client.admin.presenter.LoginPresenter;
<<<<<<< HEAD
import org.gemsjax.client.admin.presenter.Presenter;
import org.gemsjax.client.admin.view.LoadingView;
import org.gemsjax.client.admin.view.implementation.AdminApplicationViewImpl;
import org.gemsjax.client.admin.view.implementation.LoadingViewImpl;
import org.gemsjax.client.admin.view.implementation.LoginViewImpl;
=======
import org.gemsjax.client.admin.presenter.MetaModelPresenter;
import org.gemsjax.client.admin.presenter.Presenter;
import org.gemsjax.client.admin.view.LoadingView;
import org.gemsjax.client.admin.view.MetaModelView;
import org.gemsjax.client.admin.view.implementation.AdminApplicationViewImpl;
import org.gemsjax.client.admin.view.implementation.LoadingViewImpl;
import org.gemsjax.client.admin.view.implementation.LoginViewImpl;
import org.gemsjax.client.admin.view.implementation.MetaModelViewImpl;
import org.gemsjax.client.canvas.CanvasSupportException;
import org.gemsjax.client.editor.MetaModelCanvas.EditingMode;
>>>>>>> 6e39b6f63e9e5ce0f0ec81e97e49f26d82589248
import org.gemsjax.client.websocket.WebSocket;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootPanel;
<<<<<<< HEAD
=======
import com.smartgwt.client.widgets.events.HasClickHandlers;
>>>>>>> 6e39b6f63e9e5ce0f0ec81e97e49f26d82589248


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
	 * The {@link UserLanguage} 
	 */
	private static UserLanguage language = GWT.create(UserLanguage.class);
	
	
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
	 * Get the current Language
	 * @return
	 */
	public UserLanguage getLanguage()
	{
		return language;
	}
	
	
	/**
	 * Start the "admin" app by initializing the {@link LanguageManager}.
	 * When the {@link LanguageManager} initialization is done the application can 
	 * "start" by calling {@link #startAfterCorrectLanguageManagerInitialization()}.
	 * <b>NOTE:</b> Since thie {@link AdminApplicationController} implements {@link LanguageManagerHandler} 
	 * the method {@link #startAfterCorrectLanguageManagerInitialization()} will be called automatically  when a {@link LanguageManagerEvent}
	 * with the type {@link LanguageManagerEventType#INITIALIZED} will be received
	 */
	public void start()
	{
		
		loadingPresenter = new LoadingPresenter(eventBus, new LoadingViewImpl());
		
		// Important: first create the loginPresenter and than the AdminApplicationPresenter: So the LoginPresenter will receive LoginEvents as the first
		loginPresenter = new LoginPresenter(eventBus, new LoginViewImpl(language), RootPanel.get());
		new AdminApplicationPresenter(eventBus, new AdminApplicationViewImpl(language));
<<<<<<< HEAD
=======
		
		try {
			new MetaModelPresenter(eventBus, new MetaModelViewImpl("MetaModel 1", language), null);
		} catch (CanvasSupportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
>>>>>>> 6e39b6f63e9e5ce0f0ec81e97e49f26d82589248
	
		
	}

	
	
}
