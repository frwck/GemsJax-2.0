package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.CreateNewExperimentRequiredHandler;

import com.google.gwt.event.shared.GwtEvent;

public class CreateNewExperimentRequiredEvent extends GwtEvent<CreateNewExperimentRequiredHandler>{

	
	public static com.google.gwt.event.shared.GwtEvent.Type<CreateNewExperimentRequiredHandler> TYPE = new com.google.gwt.event.shared.GwtEvent.Type<CreateNewExperimentRequiredHandler>();
	
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CreateNewExperimentRequiredHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CreateNewExperimentRequiredHandler handler) {
		handler.onCreateNewExperimentRequired();
	}

}
