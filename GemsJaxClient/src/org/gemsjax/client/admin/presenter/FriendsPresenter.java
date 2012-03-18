package org.gemsjax.client.admin.presenter;

import java.io.IOException;
import java.util.Map;

import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent;
import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent.CriticalErrorType;
import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent;
import org.gemsjax.client.admin.presenter.event.LoadingAnimationEvent.LoadingAnimationEventType;
import org.gemsjax.client.admin.presenter.event.LoginSuccessfulEvent;
import org.gemsjax.client.admin.presenter.event.ManageFriendshipEvent;
import org.gemsjax.client.admin.presenter.handler.LoginSuccessfulHandler;
import org.gemsjax.client.admin.presenter.handler.ManageFriendshipHandler;
import org.gemsjax.client.admin.view.FriendsOnlineView;
import org.gemsjax.client.admin.view.ManageFriendsView;
import org.gemsjax.client.module.FriendsModule;
import org.gemsjax.client.module.handler.FriendsModuleHandler;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.friend.FriendError;
import org.gemsjax.shared.communication.message.search.UserResult;

import com.google.gwt.event.shared.EventBus;

public class FriendsPresenter extends Presenter implements FriendsModuleHandler, LoginSuccessfulHandler, ManageFriendshipHandler{
	
	private FriendsOnlineView view;
	private FriendsModule friends;
	private ManageFriendsView manageView;
	
	
	public FriendsPresenter(EventBus eventBus, FriendsOnlineView view, ManageFriendsView manageView, FriendsModule module) {
		super(eventBus);
		this.view = view;
		this.manageView = manageView;
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



	@Override
	public void onNewFriendshipRequired(ManageFriendshipEvent event) {
		
		Friend f = friends.getFriendById(event.getUser().getUserId());
		
		if (f!=null)
			
		
	}



	@Override
	public void onUnfriendRequired(ManageFriendshipEvent event) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onCancelFriendshipsSuccessful(Friend exFriend) {
		manageView.showCancelFriendshipSuccessful(exFriend);
	}



	@Override
	public void onNewFriendshipRequestSuccessful(
			UserResult userWhoHasReceivedRequest) {
		manageView.showFriendshipRequestSuccessful(userWhoHasReceivedRequest.getDisplayName());
	}



	@Override
	public void onCancelFriendshipError(Friend friendToUnfriend,
			FriendError error) {
		manageView.showCancelFriendshipError(friendToUnfriend, error);
	}



	@Override
	public void onNewFriendshipRequestError(UserResult user, FriendError error) {
		manageView.showFriendshipRequestError(user.getDisplayName(), error);
	}



}
