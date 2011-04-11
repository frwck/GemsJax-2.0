package org.gemsjax.client.admin.presenter;

import org.gemsjax.client.admin.event.LoginEvent;
import org.gemsjax.client.admin.handler.LoginHandler;
import org.gemsjax.client.admin.view.AdminUIView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class AdminApplicationPresenter extends Presenter implements LoginHandler{

	private AdminUIView view;
	
	public AdminApplicationPresenter(EventBus eventBus, AdminUIView view) {
		super(eventBus);
		this.view = view;
	}
	
	
	private void bind()
	{
		view.getUserMenuExperiments().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				//TODO bind correct
				Window.alert("Experimets");
			}
		});
	}


	@Override
	public void onLogin(LoginEvent event) {
		if (event.wasSuccessful())
		{
			//TODO maybe we should display the username somewhere in the gui
			//view.setUsername(event.getUsername());
			view.show();
		}
			
	}
	

	
	

}
