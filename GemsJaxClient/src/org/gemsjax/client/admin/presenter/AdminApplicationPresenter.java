package org.gemsjax.client.admin.presenter;

import org.gemsjax.client.admin.presenter.event.LoginSuccessfulEvent;
import org.gemsjax.client.admin.presenter.handler.LoginSuccessfulHandler;
import org.gemsjax.client.admin.view.AdminUIView;
import org.gemsjax.shared.user.RegisteredUser;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class AdminApplicationPresenter extends Presenter implements LoginSuccessfulHandler{

	private AdminUIView view;
	private RegisteredUser authenticatedUser;
	
	public AdminApplicationPresenter(EventBus eventBus, AdminUIView view) {
		super(eventBus);
		eventBus.addHandler(LoginSuccessfulEvent.TYPE, this);
		this.view = view;
		//bind();
	}
	
	
	private void bind()
	{
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
			    */
				Window.alert("Experimets");
			}
		});
	}


	@Override
	public void onLoginSuccessful(LoginSuccessfulEvent event) {
		authenticatedUser = event.getAuthenticatedUser();
		view.show();
	}
	

	
	

}
