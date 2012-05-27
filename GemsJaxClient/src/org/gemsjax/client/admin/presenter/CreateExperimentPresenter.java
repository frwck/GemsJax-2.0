package org.gemsjax.client.admin.presenter;

import java.io.IOException;
import java.util.Set;

import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent;
import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent.LoadingAnimationEventType;
import org.gemsjax.client.admin.view.CreateExperimentView;
import org.gemsjax.client.admin.widgets.StepByStepWizard.WizardHandler;
import org.gemsjax.client.module.ExperimentModule;
import org.gemsjax.client.module.handler.ExperimentModuleHandler;
import org.gemsjax.shared.communication.message.experiment.ExperimentDTO;
import org.gemsjax.shared.communication.message.experiment.ExperimentError;

import com.google.gwt.event.shared.EventBus;

public class CreateExperimentPresenter extends Presenter implements WizardHandler, ExperimentModuleHandler{
	
	private CreateExperimentView view ;
	private ExperimentModule module;

	public CreateExperimentPresenter(EventBus eventBus, CreateExperimentView view, ExperimentModule module) {
		super(eventBus);
		
		this.view = view;
		this.module = module;
		this.module.addExperimentModuleHandler(this);
		
		view.addWizardHandler(this);
		view.showIt();
		
	}

	@Override
	public void onFinishReached() {
		
		try {
			eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.SHOW, this));
			module.createExperiment(view.getExperimentName(), view.getDescription(), view.getExperimentGroups(), view.getSelectedFriends());
		} catch (IOException e) {
			eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.SHOW, this));
			view.showException(e);
			e.printStackTrace();
		}
		
	}

	@Override
	public void onCreateNewSuccessful() {
		eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
		view.closeIt();
		view.showSuccessful();
		
	}

	@Override
	public void onCreateNewFailed(ExperimentError error) {
		eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
		view.showException(null);
	}

	@Override
	public void onGetAllSuccessful(Set<ExperimentDTO> experiments) {
		// NOT needed
	}

	@Override
	public void onGetAllFailed(ExperimentError error) {
		// Not needed
	}



}
