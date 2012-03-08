package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.ShowNotificationRequestCenterHandler;
import org.gemsjax.client.admin.view.NotificationRequestView;

import com.google.gwt.event.shared.GwtEvent;

/**
 * This message is thrown, to show the {@link NotificationRequestView}
 * @author Hannes Dorfmann
 *
 */
public class ShowNotificationRequestCenterRequiredEvent extends GwtEvent<ShowNotificationRequestCenterHandler>{

	public static Type<ShowNotificationRequestCenterHandler>TYPE = new Type<ShowNotificationRequestCenterHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShowNotificationRequestCenterHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowNotificationRequestCenterHandler handler) {
		handler.onShowNotificationRequestCenterRequired(this);
	}

}
