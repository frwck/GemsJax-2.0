package org.gemsjax.client.admin.presenter;

import org.gemsjax.client.admin.view.CreateMetaModelView;
import org.gemsjax.client.admin.widgets.StepByStepWizard.WizardHandler;

import com.google.gwt.event.shared.EventBus;

public class CreateMetaModelPresenter extends Presenter implements WizardHandler{

	private CreateMetaModelView view; 
	
	public CreateMetaModelPresenter(EventBus eventBus, CreateMetaModelView view) {
		super(eventBus);
		this.view = view;
		
		bind();
		view.show();
	}
	
	
	private void bind(){
		view.addWizardHandler(this);
	}


	@Override
	public void onFinishReached() {
		
	}
	
	
	

}
