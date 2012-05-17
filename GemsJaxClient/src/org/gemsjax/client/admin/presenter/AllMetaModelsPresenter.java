package org.gemsjax.client.admin.presenter;

import java.io.IOException;
import java.util.Set;

import org.gemsjax.client.admin.presenter.event.CreateNewMetaModelRequiredEvent;
import org.gemsjax.client.admin.view.AllMetaModelsView;
import org.gemsjax.client.module.CollaborateableFileModule;
import org.gemsjax.client.module.handler.CollaborateableFileModuleHandler;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileError;
import org.gemsjax.shared.metamodel.MetaModel;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class AllMetaModelsPresenter extends Presenter implements ClickHandler, CollaborateableFileModuleHandler<MetaModel>{

	private AllMetaModelsView view;
	private CollaborateableFileModule<MetaModel> module;
	
	public AllMetaModelsPresenter(EventBus eventBus, AllMetaModelsView view, CollaborateableFileModule<MetaModel> module) {
		super(eventBus);
		this.view = view;
		bind();
		this.module = module;
		module.addCollaborateableFileHandler(this);
		view.showIt();
		requireRefreshViewResult();
	}
	
	private void bind(){
		view.getCreateNewButton().addClickHandler(this);
		view.getRefreshButton().addClickHandler(this);
	}
	

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == view.getCreateNewButton())
			eventBus.fireEvent(new CreateNewMetaModelRequiredEvent());
		
		if (event.getSource() == view.getRefreshButton())
			requireRefreshViewResult();
	}
	
	private void requireRefreshViewResult(){
		try {
			module.getAllMetaModels();
		} catch (IOException e) {
			view.showUnexpectedError();
		}
	}

	@Override // Not needed
	public void onNewCreateSuccessful() {}

	@Override // Not needed
	public void onNewCreateError(CollaborateableFileError error) {}

	@Override
	public void onGetAllSuccessful(Set<MetaModel> result) {
		view.setAllMetaModels(result);
		view.showContent();
	}
	
	public void onGetAllError(CollaborateableFileError error) {
		view.showUnexpectedError();
	}
	

}
