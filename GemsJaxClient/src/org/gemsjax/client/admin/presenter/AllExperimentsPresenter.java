package org.gemsjax.client.admin.presenter;

import org.gemsjax.client.admin.presenter.event.CreateNewExperimentRequiredEvent;
import org.gemsjax.client.admin.view.AllExperimentsView;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class AllExperimentsPresenter extends Presenter implements ClickHandler{

	private AllExperimentsView view;
	
	
	public AllExperimentsPresenter(AllExperimentsView view, EventBus eventBus) {
		super(eventBus);
		this.view = view;
		
		
		view.showContent();
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

}
