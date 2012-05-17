package org.gemsjax.client.admin;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.notification.NotificationManager;
import org.gemsjax.client.admin.notification.ShortInfoNotification;
import org.gemsjax.client.admin.presenter.AdminApplicationPresenter;
import org.gemsjax.client.admin.presenter.AllMetaModelsPresenter;
import org.gemsjax.client.admin.presenter.CollaborationPresenter;
import org.gemsjax.client.admin.presenter.CreateMetaModelPresenter;
import org.gemsjax.client.admin.presenter.CriticalErrorPresenter;
import org.gemsjax.client.admin.presenter.FriendsPresenter;
import org.gemsjax.client.admin.presenter.GlobalSearchPresenter;
import org.gemsjax.client.admin.presenter.LoadingPresenter;
import org.gemsjax.client.admin.presenter.AuthenticationPresenter;
import org.gemsjax.client.admin.presenter.MetaModelPresenter;
import org.gemsjax.client.admin.presenter.NotificationRequestPresenter;
import org.gemsjax.client.admin.presenter.Presenter;
import org.gemsjax.client.admin.presenter.RegistrationPresenter;
import org.gemsjax.client.admin.presenter.event.CreateNewMetaModelRequiredEvent;
import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent;
import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent.CriticalErrorType;
import org.gemsjax.client.admin.presenter.event.CollaborateableClosedEvent;
import org.gemsjax.client.admin.presenter.event.DoNewGlobalSearchEvent;
import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent;
import org.gemsjax.client.admin.presenter.event.LoginSuccessfulEvent;
import org.gemsjax.client.admin.presenter.event.ShowAllMetaModelsRequestedEvent;
import org.gemsjax.client.admin.presenter.event.ShowMetaModelRequiredEvent;
import org.gemsjax.client.admin.presenter.handler.CollaboratebableClosedHandler;
import org.gemsjax.client.admin.presenter.handler.CreateNewMetaModelRequiredHandler;
import org.gemsjax.client.admin.presenter.handler.DoNewGlobalSearchHandler;
import org.gemsjax.client.admin.presenter.handler.LoginSuccessfulHandler;
import org.gemsjax.client.admin.presenter.handler.ManageFriendsViewImpl;
import org.gemsjax.client.admin.presenter.handler.ShowAllMetaModelRequestedHandler;
import org.gemsjax.client.admin.presenter.handler.ShowMetaModelRequiredHandler;
import org.gemsjax.client.admin.view.CreateMetaModelView;
import org.gemsjax.client.admin.view.LoadingView;
import org.gemsjax.client.admin.view.MetaModelView;
import org.gemsjax.client.admin.view.implementation.AdminApplicationViewImpl;
import org.gemsjax.client.admin.view.implementation.AllMetaModelsViewImpl;
import org.gemsjax.client.admin.view.implementation.CreateMetaModelViewImpl;
import org.gemsjax.client.admin.view.implementation.CriticalErrorViewImpl;
import org.gemsjax.client.admin.view.implementation.GlobalSearchResultViewImpl;
import org.gemsjax.client.admin.view.implementation.LoadingViewImpl;
import org.gemsjax.client.admin.view.implementation.LoginViewImpl;
import org.gemsjax.client.admin.view.implementation.LogoutViewImpl;
import org.gemsjax.client.admin.view.implementation.MetaModelViewImpl;
import org.gemsjax.client.admin.view.implementation.NotificationRequestViewImpl;
import org.gemsjax.client.admin.view.implementation.RegistrationViewImpl;
import org.gemsjax.client.canvas.CanvasSupportException;
import org.gemsjax.client.communication.HttpPostCommunicationConnection;
import org.gemsjax.client.communication.WebSocketCommunicationConnection;
import org.gemsjax.client.communication.channel.AuthenticationChannel;
import org.gemsjax.client.communication.channel.CollaborateableFileChannel;
import org.gemsjax.client.communication.channel.CollaborationChannel;
import org.gemsjax.client.communication.channel.FriendsLiveChannel;
import org.gemsjax.client.communication.channel.NotificationChannel;
import org.gemsjax.client.communication.channel.RegistrationChannel;
import org.gemsjax.client.communication.channel.RequestChannel;
import org.gemsjax.client.communication.channel.SearchChannel;
import org.gemsjax.client.module.AuthenticationModule;
import org.gemsjax.client.module.CollaborateableFileModule;
import org.gemsjax.client.module.CollaborationModule;
import org.gemsjax.client.module.FriendsModule;
import org.gemsjax.client.module.GlobalSearchModule;
import org.gemsjax.client.module.NotificationRequestModule;
import org.gemsjax.client.module.RegistrationModule;
import org.gemsjax.client.util.Console;
import org.gemsjax.shared.ServletPaths;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaInheritance;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.exception.MetaAttributeException;
import org.gemsjax.shared.metamodel.exception.MetaClassException;
import org.gemsjax.shared.metamodel.exception.MetaConnectionException;
import org.gemsjax.shared.metamodel.exception.MetaInheritanceExcepetion;
import org.gemsjax.shared.metamodel.impl.MetaFactory;
import org.gemsjax.shared.metamodel.impl.MetaModelImpl;
import org.gemsjax.shared.user.RegisteredUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.util.SC;

/**
 * The AdminApplicationController controls the presenters and the History and will listen to the {@link WebSocket} for the server-client communication
 * 
 * @author Hannes Dorfmann
 *
 */
public class AdminApplicationController  implements ShowMetaModelRequiredHandler, DoNewGlobalSearchHandler, LoginSuccessfulHandler, 
											ShowAllMetaModelRequestedHandler, CreateNewMetaModelRequiredHandler,
											CollaboratebableClosedHandler{
	
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
	private AuthenticationPresenter authenticationPresenter;
	
	/**
	 * The {@link RegistrationPresenter} to register a new {@link RegisteredUser}
	 */
	private RegistrationPresenter registrationPresenter;
	
	private FriendsModule friendsModule;
	private FriendsPresenter friendsPresenter;
	
	
	private AdminApplicationPresenter applicationPresenter;
	
	private AuthenticationModule authenticationModule;
	
	private AdminApplicationViewImpl adminMainView;
	private CriticalErrorPresenter errorPresenter;
	
	private NotificationRequestModule notificationRequestModle;
	
	private Map<Integer, CollaborationPresenter> collaborations;
	
	
	private AdminApplicationController()
	{
		eventBus = new SimpleEventBus();
		authenticationModule = new AuthenticationModule(new AuthenticationChannel(WebSocketCommunicationConnection.getInstance()));
		friendsModule = new FriendsModule(new FriendsLiveChannel(WebSocketCommunicationConnection.getInstance()));
		collaborations = new LinkedHashMap<Integer, CollaborationPresenter>();
		bindPresenterEvents();
	}
	
	
	private void bindPresenterEvents()
	{
		eventBus.addHandler(DoNewGlobalSearchEvent.TYPE, this);
		eventBus.addHandler(LoginSuccessfulEvent.TYPE, this);
		eventBus.addHandler(ShowAllMetaModelsRequestedEvent.TYPE, this);
		eventBus.addHandler(CreateNewMetaModelRequiredEvent.TYPE,this);
		eventBus.addHandler(ShowMetaModelRequiredEvent.TYPE, this);
		eventBus.addHandler(CollaborateableClosedEvent.TYPE, this);
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
		
			
		try {
		
			RegistrationModule registrationModule = new RegistrationModule( new RegistrationChannel(new HttpPostCommunicationConnection(ServletPaths.REGISTRATION)));
			
			registrationPresenter = new RegistrationPresenter(new RegistrationViewImpl(language), eventBus,  registrationModule);
			
			errorPresenter = new CriticalErrorPresenter(eventBus, new CriticalErrorViewImpl(), WebSocketCommunicationConnection.getInstance());
			
			// Important: first create the authenticationPresenter and than the AdminApplicationPresenter: So the LoginPresenter will receive LoginEvents as the first
			authenticationPresenter = new AuthenticationPresenter(eventBus, new LoginViewImpl(language), new LogoutViewImpl(language), authenticationModule);
			
			friendsPresenter = new FriendsPresenter(eventBus, null, new ManageFriendsViewImpl(language), friendsModule);
			adminMainView = new AdminApplicationViewImpl(language);
			applicationPresenter = new AdminApplicationPresenter(eventBus, adminMainView, adminMainView);
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		try {
			
			/*
			MetaBaseType baseType = new MetaBaseTypeImpl("base1", "String BaseType");
			
			MetaModel metaModel = new MetaModelImpl("id1", "MetaModel1");
			metaModel.addBaseType(baseType);
			
			MetaClass c1 = metaModel.addMetaClass("mc1", "Class 1");
			
			MetaClass c2 =metaModel.addMetaClass("mc2", "Class 2");
			
			c1.setX(100);
			c1.setY(100);
			c1.setWidth(100);
			c1.setHeight(130);
			
			c2.setX(300);
			c2.setY(150);
			c2.setWidth(100);
			c2.setHeight(130);
			
			
			for (int i =1; i<=10; i++)
			{
				c1.addAttribute("a"+i, "Attribute"+i, baseType);
				c2.addAttribute("aa"+i, "Attribute"+i, baseType);
			}
			
			
			MetaConnection con = c1.addConnection("con1", "Connection c1 - c2", c2, 0, 10);
			

			con.setConnectionBoxX(150);
			con.setConnectionBoxY(150);
			con.setConnectionBoxHeight(50);
			con.setConnectionBoxWidth(80);
			
			con.getSourceConnectionBoxRelativePoint().x = 0;
			con.getSourceConnectionBoxRelativePoint().y = 10;
			con.getSourceRelativePoint().x = 0;
			con.getSourceRelativePoint().y = 0;
			
			con.getTargetConnectionBoxRelativePoint().x=(con.getConnectionBoxWidth());
			con.getTargetConnectionBoxRelativePoint().y = 20;
			con.getTargetRelativePoint().x =0;
			con.getTargetRelativePoint().y=0;
			
			
			
			*/
			/*
			MetaBaseType baseType = MetaFactory.createExistingBaseType("basetypeID", "BaseType 1");
			
			MetaModel metaModel = MetaFactory.createMetaModel(1,"MetaFactory Model");
			
			
			MetaClass c1 = MetaFactory.createClass("Class1", 50, 50);
			
			MetaClass c2 = MetaFactory.createClass("Class2",300,100);
			
			LoginSuccessfulEvent
			for (int i =1; i<=10; i++)
			{
				c1.addAttribute(MetaFactory.createAttribute("Attribute "+i,baseType));
				c2.addAttribute(MetaFactory.createAttribute("Attribute "+i,baseType));
			}
			
			
			MetaConnection con = MetaFactory.createMetaConnection("Connection", c1, c2);
			
			c1.addConnection(con);
			
			for (int i =0; i<10;i++)
				con.addAttribute(MetaFactory.createAttribute("Con Attribute"+i, baseType));
			
			
			metaModel.addMetaClass(c1);
			metaModel.addMetaClass(c2);
			
			
			MetaInheritance inh = MetaFactory.createInheritance(c1, c2);
			c1.addInheritance(inh);
			
			
			
			
			new MetaModelPresenter(eventBus, new MetaModelViewImpl("MetaFactory Model", language), metaModel);
		
			
			
		} catch (CanvasSupportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SC.logWarn(e.getMessage());
		} catch (MetaAttributeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			SC.logWarn(e.getMessage());
		} catch (MetaConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			SC.logWarn(e.getMessage());
		} catch (MetaClassException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			SC.logWarn(e.getMessage());
		} catch (MetaInheritanceExcepetion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SC.logWarn(e.getMessage());
		}
		
		*/
	
		
	}



	@Override
	public void onDoNewGlobalSerch(DoNewGlobalSearchEvent event) {
		
		GlobalSearchResultViewImpl view = new GlobalSearchResultViewImpl(event.getSearchString(), getLanguage());
		TabEnviroment.getInstance().addTab(view);
		view.showLoading();
		view.showContent();
		/*
		CommunicationConnection c = new HttpPostCommunicationConnection(ServletPaths.SEARCH);
		c.connect();
		*/
		SearchChannel channel = new SearchChannel(WebSocketCommunicationConnection.getInstance());
		GlobalSearchModule searchModule = new GlobalSearchModule(authenticationModule.getCurrentlyAuthenticatedUser(),channel, friendsModule);
		
		GlobalSearchPresenter p = new GlobalSearchPresenter(eventBus, view, searchModule);
		p.start(event.getSearchString());
	}


	@Override
	public void onLoginSuccessful(LoginSuccessfulEvent event) {
		notificationRequestModle = new NotificationRequestModule(event.getUnreadNotificationRequestCount(), new NotificationChannel(WebSocketCommunicationConnection.getInstance()), new RequestChannel(WebSocketCommunicationConnection.getInstance()));
		new NotificationRequestPresenter(notificationRequestModle, new NotificationRequestViewImpl(language.NotificationCenterTitle(),language), adminMainView, eventBus);
		
	}

	@Override
	public void onShowAllMetaModelsRequested(ShowAllMetaModelsRequestedEvent e) {
		AllMetaModelsViewImpl view = new AllMetaModelsViewImpl(language.AllMetaModelsTitle(), language, eventBus);
		AllMetaModelsPresenter p = new AllMetaModelsPresenter(eventBus, view, new CollaborateableFileModule<MetaModel>(new CollaborateableFileChannel<MetaModel>(WebSocketCommunicationConnection.getInstance())));
	}


	@Override
	public void onCreateNewMetaModelRequired() {
		CreateMetaModelView view = new CreateMetaModelViewImpl(language, friendsModule);
		CreateMetaModelPresenter p = new CreateMetaModelPresenter(eventBus, view, new CollaborateableFileModule<MetaModel>(new CollaborateableFileChannel<MetaModel>(WebSocketCommunicationConnection.getInstance())));
	}


	@Override
	public void onShowMetaModelRequired(int collaborateableId, String name) {
		CollaborationPresenter p = collaborations.get(collaborateableId);
		
		if (p != null)
			p.showView();
		else
		{
			MetaModel mm = new MetaModelImpl(collaborateableId, name);
			
			CollaborationModule module = new CollaborationModule(authenticationModule.getCurrentlyAuthenticatedUser(), mm, new CollaborationChannel(WebSocketCommunicationConnection.getInstance(), mm));
			
			try {
			
				MetaModelView view = new MetaModelViewImpl(name, language);
			
			
				p = new MetaModelPresenter(eventBus,view , mm,  module);
				collaborations.put(collaborateableId, p);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CanvasSupportException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}


	@Override
	public void onCollaborateableClosed(int collaborateableId) {
		collaborations.remove(collaborateableId);
		Console.log("closed "+collaborateableId);
	}
	
}
