package org.gemsjax.client.admin.presenter;

import org.gemsjax.client.admin.view.FriendsOnlineView;
import org.gemsjax.client.module.FriendsModule;
import org.gemsjax.client.module.handler.FriendsModuleHandler;

import com.google.gwt.event.shared.EventBus;

public class FriendsPresenter extends Presenter implements FriendsModuleHandler{

	
	private FriendsOnlineView view;
	private FriendsModule friends;
	
	
	public FriendsPresenter(EventBus eventBus, FriendsOnlineView view, FriendsModule module) {
		super(eventBus);
		this.view = view;
		this.friends = module;
		friends.addFriendsModuleHandler(this);
	}
	
	
	
	private void bind()
	{
		
	}



	@Override
	public void onFriendsUpdate() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onAuthenticationError() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onUnexpectedError() {
		// TODO Auto-generated method stub
		
	}

}
