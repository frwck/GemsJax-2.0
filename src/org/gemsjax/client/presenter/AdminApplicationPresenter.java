package org.gemsjax.client.presenter;

import org.gemsjax.client.view.AdminUIView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class AdminApplicationPresenter extends Presenter {

	private AdminUIView view;
	
	public AdminApplicationPresenter(EventBus eventBus, AdminUIView view) {
		super(eventBus);
		this.view = view;
		// TODO should the GUI been visible from begin on?
		view.show();
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
	

	
	

}
