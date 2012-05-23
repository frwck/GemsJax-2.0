package org.gemsjax.client.admin.presenter;

import java.io.IOException;
import java.util.Set;

import org.gemsjax.client.admin.presenter.event.CreateNewExperimentRequiredEvent;
import org.gemsjax.client.admin.view.AllExperimentsView;
import org.gemsjax.client.module.ExperimentModule;
import org.gemsjax.client.module.handler.ExperimentModuleHandler;
import org.gemsjax.shared.communication.message.experiment.ExperimentDTO;
import org.gemsjax.shared.communication.message.experiment.ExperimentError;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class AllExperimentsPresenter extends Presenter implements ClickHandler, ExperimentModuleHandler{

	private AllExperimentsView view;
	private ExperimentModule module;
	
	public AllExperimentsPresenter(AllExperimentsView view, EventBus eventBus, ExperimentModule module) throws IOException {
		super(eventBus);
		this.view = view;
		
		this.module = module;
		this.module.addExperimentModuleHandler(this);
		
		view.showLoading();
		module.getAllExperiments();
		
		view.showIt();
		
		bind();
		
	}
	
	
	private void bind(){
		view.getCreateNewButton().addClickHandler(this);
		view.getRefreshButton().addClickHandler(this);
	}


	@Override
	public void onClick(ClickEvent event) {
	
		if (event.getSource() == view.getCreateNewButton()){
			
			eventBus.fireEvent(new CreateNewExperimentRequiredEvent());
		}
		else
		if(event.getSource() == view.getRefreshButton()){
			// TODO refresh
		}
		
	}


	@Override
	public void onCreateNewSuccessful() {
		// Not needed
		
	}


	@Override
	public void onCreateNewFailed(ExperimentError error) {
		// Not needed
		
	}


	@Override
	public void onGetAllSuccessful(Set<ExperimentDTO> experiments) {
		view.setAllExperiments(experiments);
		view.showContent();
	}


	@Override
	public void onGetAllFailed(ExperimentError error) {
		view.showUnexpectedError();
		view.closeIt();
	}

}
