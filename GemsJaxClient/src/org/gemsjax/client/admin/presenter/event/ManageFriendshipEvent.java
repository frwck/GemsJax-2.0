package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.ManageFriendshipHandler;
import org.gemsjax.shared.communication.message.search.UserResult;

import com.google.gwt.event.shared.GwtEvent;

/**
 * This kind of events are thrown by Presenters (as reaction on a User input) to manage friendships,
 * like make new friendship requests or to cancel a friendship (unfriend).
 * This kind of events are handled by {@link ManageFriendshipHandler}s.
 * @author Hannes Dorfmann
 *
 */
public class ManageFriendshipEvent extends GwtEvent<ManageFriendshipHandler> {
	
	public enum ManageFriendshipType{
		NEW_FRIENDSHIP,
		UNFRIEND
	}
	

	public static Type<ManageFriendshipHandler> TYPE = new Type<ManageFriendshipHandler>();
	
	private ManageFriendshipType type;
	private UserResult userResult;
	
	public ManageFriendshipEvent(ManageFriendshipType type, UserResult userResult)
	{
		this.userResult = userResult;
		this.type = type;
	}
	
	public UserResult getUser(){
		return userResult;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ManageFriendshipHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ManageFriendshipHandler handler) {
		if (type == ManageFriendshipType.NEW_FRIENDSHIP)
			handler.onNewFriendshipRequired(this);
		else
			handler.onUnfriendRequired(this);
	}

}
