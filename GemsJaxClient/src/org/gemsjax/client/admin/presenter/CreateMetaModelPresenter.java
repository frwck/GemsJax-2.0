package org.gemsjax.client.admin.presenter;

import java.io.IOException;
import java.util.Set;

import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent;
import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent.LoadingAnimationEventType;
import org.gemsjax.client.admin.view.CreateMetaModelView;
import org.gemsjax.client.admin.widgets.StepByStepWizard.WizardHandler;
import org.gemsjax.client.module.CollaborateableFileModule;
import org.gemsjax.client.module.handler.CollaborateableFileModuleHandler;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileError;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableType;
import org.gemsjax.shared.metamodel.MetaModel;

import com.google.gwt.event.shared.EventBus;

public class CreateMetaModelPresenter extends Presenter implements WizardHandler, CollaborateableFileModuleHandler<MetaModel>{

	private CreateMetaModelView view; 
	private CollaborateableFileModule<MetaModel> module;
	
	public CreateMetaModelPresenter(EventBus eventBus, CreateMetaModelView view, CollaborateableFileModule<MetaModel> module) {
		super(eventBus);
		this.module = module;
		this.view = view;
		module.addCollaborateableFileHandler(this);
		
		bind();
		view.show();
	}
	
	
	private void bind(){
		view.addWizardHandler(this);
	}


	@Override
	public void onFinishReached() {
		try {
			eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.SHOW, this));
			module.createNew(view.getName(),view.getDescription(), CollaborateableType.METAMODEL, view.getPermission(), view.getCollaborators());
			
		} catch (IOException e) {
			eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
			view.showUnexpectedError();
		}
	}


	@Override
	public void onNewCreateSuccessful() {
		eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
		view.showSuccessfulCreatedMessage();
	}


	@Override
	public void onNewCreateError(CollaborateableFileError error) {
		eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this));
		view.showErrorOccurred(error);
	}


	@Override // Not needed
	public void onGetAllSuccessful(Set<MetaModel> result) {	}


	@Override // Not needed
	public void onGetAllError(CollaborateableFileError error) {	}
	
	
	

}
