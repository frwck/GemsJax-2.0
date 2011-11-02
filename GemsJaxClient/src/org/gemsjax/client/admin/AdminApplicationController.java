package org.gemsjax.client.admin;

import org.eclipse.jetty.websocket.WebSocket;
import org.gemsjax.client.admin.event.LoadingAnimationEvent;
import org.gemsjax.client.admin.presenter.AdminApplicationPresenter;
import org.gemsjax.client.admin.presenter.LoadingPresenter;
import org.gemsjax.client.admin.presenter.LoginPresenter;
import org.gemsjax.client.admin.presenter.MetaModelPresenter;
import org.gemsjax.client.admin.presenter.Presenter;
import org.gemsjax.client.admin.view.LoadingView;
import org.gemsjax.client.admin.view.implementation.AdminApplicationViewImpl;
import org.gemsjax.client.admin.view.implementation.LoadingViewImpl;
import org.gemsjax.client.admin.view.implementation.LoginViewImpl;
import org.gemsjax.client.admin.view.implementation.MetaModelViewImpl;
import org.gemsjax.client.canvas.CanvasSupportException;
import org.gemsjax.client.metamodel.MetaBaseTypeImpl;
import org.gemsjax.client.metamodel.MetaClassImpl;
import org.gemsjax.client.metamodel.MetaModelImpl;
import org.gemsjax.client.metamodel.factory.MetaFactory;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaInheritance;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.exception.MetaAttributeException;
import org.gemsjax.shared.metamodel.exception.MetaBaseTypeException;
import org.gemsjax.shared.metamodel.exception.MetaClassException;
import org.gemsjax.shared.metamodel.exception.MetaConnectionException;
import org.gemsjax.shared.metamodel.exception.MetaInheritanceExcepetion;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.util.SC;

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
			
			MetaBaseType baseType = MetaFactory.createExistingBaseType("basetypeID", "BaseType 1");
			
			MetaModel metaModel = MetaFactory.createMetaModel("MetaFactory Model");
			
			
			MetaClass c1 = MetaFactory.createClass("Class1", 50, 50);
			
			MetaClass c2 = MetaFactory.createClass("Class2",300,100);
			
			
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
	
		
	}

	
	
}