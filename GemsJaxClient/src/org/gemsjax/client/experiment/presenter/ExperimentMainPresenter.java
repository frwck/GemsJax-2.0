package org.gemsjax.client.experiment.presenter;

import org.gemsjax.client.admin.presenter.Presenter;
import org.gemsjax.client.experiment.view.ExperimentMainView;

import com.google.gwt.event.shared.EventBus;

public class ExperimentMainPresenter extends Presenter {

	private ExperimentMainView view;
	
	public ExperimentMainPresenter(EventBus eventBus, ExperimentMainView view) {
		super(eventBus);
		
		this.view = view;
		
	}

}
