package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.ShowExperimentRequiredHandler;

import com.google.gwt.event.shared.GwtEvent;

public class ShowExperimentRequiredEvent extends GwtEvent<ShowExperimentRequiredHandler> {
	
	public static com.google.gwt.event.shared.GwtEvent.Type<ShowExperimentRequiredHandler> TYPE = new com.google.gwt.event.shared.GwtEvent.Type<ShowExperimentRequiredHandler>();
	
	private String name;
	private int experimentId;
	
	public ShowExperimentRequiredEvent(int experimentId, String name){
		this.name = name;
		this.experimentId =experimentId;
	}
	
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShowExperimentRequiredHandler> getAssociatedType() {
		return TYPE;
	}
	@Override
	protected void dispatch(ShowExperimentRequiredHandler handler) {
		handler.onShowExperimentRequired(experimentId, name);
	}


	public String getName() {
		return name;
	}


	public int getExperimentId() {
		return experimentId;
	}
	

}
