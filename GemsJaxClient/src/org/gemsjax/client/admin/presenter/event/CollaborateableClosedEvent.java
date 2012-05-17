package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.CollaboratebableClosedHandler;

import com.google.gwt.event.shared.GwtEvent;

public class CollaborateableClosedEvent extends GwtEvent<CollaboratebableClosedHandler> {
	
	public static com.google.gwt.event.shared.GwtEvent.Type<CollaboratebableClosedHandler> TYPE = new com.google.gwt.event.shared.GwtEvent.Type<CollaboratebableClosedHandler>();
	
	private int collaborateableId;
	
	public CollaborateableClosedEvent(int collaborateableId){
		this.collaborateableId = collaborateableId;
	}

	public int getCollaborateableId() {
		return collaborateableId;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CollaboratebableClosedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CollaboratebableClosedHandler handler) {
		handler.onCollaborateableClosed(collaborateableId);
	}

}
