
package org.gemsjax.client.admin.presenter;

import org.gemsjax.client.admin.presenter.event.DoNewGlobalSearchEvent;
import org.gemsjax.client.admin.presenter.event.LoginSuccessfulEvent;
import org.gemsjax.client.admin.presenter.event.ShowAllExperimentsRequestedEvent;
import org.gemsjax.client.admin.presenter.event.ShowAllMetaModelsRequestedEvent;
import org.gemsjax.client.admin.presenter.event.ShowNotificationRequestCenterRequiredEvent;
import org.gemsjax.client.admin.presenter.handler.LoginSuccessfulHandler;
import org.gemsjax.client.admin.presenter.handler.ShowAllMetaModelRequestedHandler;
import org.gemsjax.client.admin.view.AdminUIView;
import org.gemsjax.client.admin.view.QuickSearchView;
import org.gemsjax.client.admin.view.QuickSearchView.QuickSearchHanlder;
import org.gemsjax.client.admin.view.implementation.AllMetaModelsViewImpl;
import org.gemsjax.shared.user.RegisteredUser;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class AdminApplicationPresenter extends Presenter implements QuickSearchHanlder, LoginSuccessfulHandler{

	private AdminUIView view;
	private QuickSearchView searchView;
	private RegisteredUser authenticatedUser;
	
	public AdminApplicationPresenter(EventBus eventBus, AdminUIView view, QuickSearchView searchView) {
		super(eventBus);
		eventBus.addHandler(LoginSuccessfulEvent.TYPE, this);
		this.view = view;
		this.searchView = searchView;
		bind();
	}
	
	
	private void bind()
	{
		searchView.addQuickSearchHandler(this);
		
		view.getUserMenuNotificationRequestCenter().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				onNotificationRequestCenterClicked();
			}
		});
		
		
		view.getUserMenuMetaModels().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				onMetaModelsClicked();
			}
		});
		
		
		view.getUserMenuExperiments().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				onExperimentsClicked();
			}

		});
		
		
		/*
		view.getUserMenuExperiments().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				/* TODO bugging  Null pointer exception 
				java.lang.NullPointerException: null
			    at org.gemsjax.client.admin.view.implementation.AdminApplicationViewImpl.getUserMenuExperiments(AdminApplicationViewImpl.java:111)
			    at org.gemsjax.client.admin.presenter.AdminApplicationPresenter.bind(AdminApplicationPresenter.java:26)
			    at org.gemsjax.client.admin.presenter.AdminApplicationPresenter.<init>(AdminApplicationPresenter.java:20)
			    at org.gemsjax.client.admin.AdminApplicationController.start(AdminApplicationController.java:102)
			    at org.gemsjax.client.admin.GemsJaxClient.onModuleLoad(GemsJaxClient.java:23)
			    
				Window.alert("Experimets");
			}
		});
		
		*/
	}


	@Override
	public void onLoginSuccessful(LoginSuccessfulEvent event) {
		authenticatedUser = (RegisteredUser) event.getAuthenticatedUser();
		view.setUser(authenticatedUser);
		view.show();
	}


	@Override
	public void onDoSearch(String searchString) {
		eventBus.fireEvent(new DoNewGlobalSearchEvent(searchString));
	}
	
	
	private void onNotificationRequestCenterClicked(){
		eventBus.fireEvent(new ShowNotificationRequestCenterRequiredEvent());
	}

	
	private void onMetaModelsClicked(){
		eventBus.fireEvent(new ShowAllMetaModelsRequestedEvent());
	}

	
	private void onExperimentsClicked() {
		eventBus.fireEvent(new ShowAllExperimentsRequestedEvent());
	}
	
}
