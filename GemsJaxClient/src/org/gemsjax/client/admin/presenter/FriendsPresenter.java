package org.gemsjax.client.admin.presenter;

import java.io.IOException;

import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent;
import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent.CriticalErrorType;
import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent;
import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent.LoadingAnimationEventType;
import org.gemsjax.client.admin.presenter.event.LoginSuccessfulEvent;
import org.gemsjax.client.admin.presenter.handler.LoginSuccessfulHandler;
import org.gemsjax.client.admin.view.FriendsOnlineView;
import org.gemsjax.client.module.FriendsModule;
import org.gemsjax.client.module.handler.FriendsModuleHandler;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.friend.FriendError;

import com.google.gwt.event.shared.EventBus;

public class FriendsPresenter extends Presenter implements FriendsModuleHandler, LoginSuccessfulHandler{
	
	private FriendsOnlineView view;
	private FriendsModule friends;
	
	
	public FriendsPresenter(EventBus eventBus, FriendsOnlineView view, FriendsModule module) {
		super(eventBus);
		this.view = view;
		this.friends = module;
		friends.addFriendsModuleHandler(this);
		eventBus.addHandler(LoginSuccessfulEvent.TYPE, this);
	}
	
	
	
	private void bind()
	{
	}



	@Override
	public void onFriendsUpdate() {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onNewFriendAdded(Friend f) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onErrorAnswer(String referenceId, FriendError error,
			String additionalInfo) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onFriendshipsSuccessfulCanceled(String referenceId) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onNewFriendshipRequestSuccessful(String referenceId) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onInitGetFriendsResponse(boolean successful) {
		
		eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this, "Loading Friends"));
		
		if (!successful)
			eventBus.fireEvent(new CriticalErrorEvent(CriticalErrorType.INITIAL_ALL_FRIENDS));
		
	}



	@Override
	public void onLoginSuccessful(LoginSuccessfulEvent event) {
	
		try {
			eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.SHOW, this, "Loading Friends"));
			friends.doInitGetAllFriends();
		} catch (IOException e) {
			eventBus.fireEvent(new LoadingAnimationEvent(LoadingAnimationEventType.HIDE, this, "Loading Friends"));
			eventBus.fireEvent(new CriticalErrorEvent(CriticalErrorType.INITIAL_ALL_FRIENDS));
			e.printStackTrace();
		}
		
	}



}
