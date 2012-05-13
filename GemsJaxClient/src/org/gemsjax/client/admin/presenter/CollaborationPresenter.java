package org.gemsjax.client.admin.presenter;


import com.google.gwt.event.shared.EventBus;

public abstract class CollaborationPresenter extends Presenter{

	public CollaborationPresenter(EventBus eventBus) {
		super(eventBus);
		
	}

	public abstract void showView();
}
