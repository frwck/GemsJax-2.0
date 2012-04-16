package org.gemsjax.client.admin.widgets;

import java.util.LinkedHashMap;
import java.util.Set;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.notification.Notification.NotificationPosition;
import org.gemsjax.client.admin.notification.NotificationManager;
import org.gemsjax.client.admin.notification.TipNotification;
import org.gemsjax.client.module.FriendsModule;
import org.gemsjax.client.module.FriendsModule.FriendDisplayNameResults;
import org.gemsjax.client.module.handler.FriendsModuleHandler;
import org.gemsjax.client.util.Console;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.friend.FriendError;
import org.gemsjax.shared.communication.message.search.UserResult;

import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.events.KeyDownEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyDownHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VStack;

public class FriendChooserList extends VStack implements FriendsModuleHandler{

	private class FriendListGridRecord extends ListGridRecord{
		
		private Friend friend;
		
		public FriendListGridRecord(Friend friend){
	
			this.friend = friend;
			this.setAttribute("displayName", friend.getDisplayName());
		}
		
		public Friend getFriend(){
			return friend;
		}
		
		
	}
	
	
	private ComboBoxItem searchField;
	private ListGrid list;
	private FriendsModule friendsModule;
	private LinkedHashMap<String, String> valueMap;
	
	
	public FriendChooserList(UserLanguage lang, FriendsModule friendModule){
		
		list = new ListGrid();
		list.setWidth100();
		searchField = new ComboBoxItem();
		searchField.setTitle(lang.FriendChooserListEnterName());
		searchField.setWidth("100%");
		searchField.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				
				
				if (event.getKeyName()=="Enter"){
					onSearchFriendRequested();
				}
			}
		});
		
		this.friendsModule = friendModule;
		
		friendsModule.addFriendsModuleHandler(this);
		buildFriendSuggestions();
		
		DynamicForm form = new DynamicForm();
		form.setItems(searchField);	
		this.addMember(form);
		this.addMember(list);
		
	}
	
	
	
	public void deregisterFriendsModule(){
		friendsModule.removeFriendsModuleHandler(this);
	}



	@Override
	public void onFriendsUpdate() {
		buildFriendSuggestions();
	}



	@Override
	public void onInitGetFriendsResponse(boolean successful) {
		// not needed
	}



	@Override
	public void onNewFriendAdded(Friend f) {
		buildFriendSuggestions();
	}



	@Override
	public void onCancelFriendshipsSuccessful(Friend exFriend) {
		buildFriendSuggestions();
	}



	@Override
	public void onNewFriendshipRequestSuccessful(
			UserResult userWhoHasReceivedRequest) {
		// Not needed
	}



	@Override
	public void onCancelFriendshipError(Friend friendToUnfriend,
			FriendError error) {
		// not needed
	}



	@Override
	public void onNewFriendshipRequestError(UserResult user, FriendError errror) {
		// Not needed
	}
	
	
	private void buildFriendSuggestions(){
		valueMap = new LinkedHashMap<String, String>();  
		
		for (Friend f: friendsModule.getAllFriends())
			valueMap.put(f.getDisplayName().toLowerCase(), f.getDisplayName());
			
        searchField.setValueMap(valueMap);
        
        
        searchField.setAddUnknownValues(false);
        searchField.setAlwaysFetchMissingValues(true);
      
        
        
	}
	
	
	private void onSearchFriendRequested(){
		
		FriendDisplayNameResults res = friendsModule.getFriendByDisplayName(searchField.getValueAsString());
		
		if (res.getSerachString().equals(searchField.getValueAsString()))
		{
			
		}
		
		
		if (res.getResult().isEmpty())
		{
			NotificationManager.getInstance().showTipNotification(new TipNotification(null, "No friend found", 1000, NotificationPosition.CENTER), AnimationEffect.FADE);
		}
		
	}
	
	

}




