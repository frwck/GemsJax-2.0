package org.gemsjax.client.admin.presenter;

import java.io.IOException;

import org.gemsjax.client.module.CollaborationModule;

import com.google.gwt.event.shared.EventBus;

public class CollaborationPresenter extends Presenter{

	private CollaborationModule module;
	
	public CollaborationPresenter(EventBus eventBus, CollaborationModule module) throws IOException {
		super(eventBus);
		this.module = module;
		module.subscribe();
	}

	public void showView(){
		
	}

}
