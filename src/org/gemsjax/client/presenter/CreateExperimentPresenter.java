package org.gemsjax.client.presenter;

import org.gemsjax.client.view.CreateExperimentView;

import com.google.gwt.event.shared.EventBus;

public class CreateExperimentPresenter extends Presenter{
	
	private CreateExperimentView view ;

	public CreateExperimentPresenter(EventBus eventBus, CreateExperimentView view) {
		super(eventBus);
		
		this.view = view;
		
	}



}
