package org.gemsjax.client.admin.presenter;

import org.gemsjax.client.admin.presenter.event.CreateNewMetaModelRequiredEvent;
import org.gemsjax.client.admin.view.AllMetaModelsView;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class AllMetaModelsPresenter extends Presenter implements ClickHandler{

	private AllMetaModelsView view;
	
	public AllMetaModelsPresenter(EventBus eventBus, AllMetaModelsView view) {
		super(eventBus);
		this.view = view;
		bind();
		view.showIt();
	}
	
	private void bind(){
		view.getCreateNewButton().addClickHandler(this);
	}

	@Override
	public void onClick(ClickEvent event) {
		eventBus.fireEvent(new CreateNewMetaModelRequiredEvent());
	}
	

}
