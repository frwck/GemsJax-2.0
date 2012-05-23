package org.gemsjax.client.admin.widgets;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.notification.Notification.NotificationPosition;
import org.gemsjax.client.admin.notification.NotificationManager;
import org.gemsjax.client.admin.notification.TipNotification;
import org.gemsjax.client.module.FriendsModule;
import org.gemsjax.client.module.FriendsModule.FriendDisplayNameResults;
import org.gemsjax.client.module.handler.FriendsModuleHandler;
import org.gemsjax.client.user.RegisteredUserImpl;
import org.gemsjax.client.util.Console;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.friend.FriendError;
import org.gemsjax.shared.communication.message.search.UserResult;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.events.KeyDownEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyDownHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;


interface AmbigiousFriendChooserHandler{
	/**
	 * Called, to inform, that a Friend has been choosen. 
	 * @param f The friend that has been choosen or <code>null</code> if AmbigiousFriendChooser has been closed, without choosing a Friend
	 */
	public void onFriendChoosen(Friend f);
}


public class FriendChooserList extends VStack implements FriendsModuleHandler, AmbigiousFriendChooserHandler{

	private class FriendListGridRecord extends ListGridRecord{
		
		private Friend friend;
		
		public FriendListGridRecord(Friend friend){
	
			this.friend = friend;
			this.setAttribute("displayName", friend.getDisplayName());
			String profilePic = friend.getProfilePicture();
			if (profilePic==null || profilePic.isEmpty() || profilePic.equals("null"))
				profilePic = RegisteredUserImpl.NO_PROFILE_PICTURE_URL;
			
			this.setAttribute("profilePic", profilePic);
		}
		
	
		public Friend getFriend(){
			return friend;
		}
		
	}
	
	
	
	
	public class AmbigiousFriendChooser extends ModalDialog implements ClickHandler{
		
		private UserLanguage lang;
		private OptionButton closeButton;
		private Set<Friend> friendsToChoose;
		private Set<AmbigiousFriendChooserHandler> handlers;
		
		private class FriendItem extends OptionButton{
			private Friend friend;
			
			public FriendItem(Friend friend) {
				super("");
				
				String profilePic = friend.getProfilePicture();
				
				if (profilePic == null || profilePic.isEmpty())
					profilePic = RegisteredUserImpl.NO_PROFILE_PICTURE_URL;
				
				this.friend = friend;
				String html = "<table style=\"border:none; width:100%;\"> <tr> <td style=\"width:25px;\" rowspan=\"2\">"+
								"<img width=\"25\" height=\"25\" src=\""+profilePic+"\" /> </td>"+
								"<td style=\"font: 18px Arial bold;\">"+friend.getDisplayName()+"</td> </tr> </table>";
								
				
				this.setContents(html);
			}
			
			public Friend getFriend(){
				return this.friend;
			}
		
			
		}
		
		
		
		public AmbigiousFriendChooser(UserLanguage lang, Set<Friend> friendsToChoose){
			this.lang = lang;
			handlers = new LinkedHashSet<AmbigiousFriendChooserHandler>();
			
			VStack content = new VStack();
			content.setWidth100();
			HLayout header = new HLayout();
			header.setMembersMargin(20);
			
			
			closeButton = new OptionButton(lang.CreateMetaModelCloseButton());
			closeButton.setWidth(55);
			closeButton.addClickHandler(this);
			
			Title t = new Title(lang.FriendChooserAmbigousDailogTitle());
			t.setWidth("*");
			
			header.addMember(t);
			header.addMember(closeButton);
			header.setWidth100();
			header.setHeight(30);
			
			
			Label intro = new Label(lang.FriendChooserAmbigousDailogIntro());
			intro.setWidth100();
			intro.setHeight(30);
			
			content.setMembersMargin(0);
			content.addMember(header);
			content.addMember(intro);
			
			content.setMargin(5);
			
			for (Friend f: friendsToChoose)
			{
				FriendItem i = new FriendItem(f);
				i.setWidth100();
				i.setHeight(25);
				content.addMember(i);
				i.addClickHandler(this);
			}
			
			
			this.addItem(content);
			this.setWidth(400);
			this.setHeight(400);
			
			this.centerInPage();
			this.animateShow(AnimationEffect.WIPE);
		}


		@Override
		public void onClick(ClickEvent event) {
			Object src = event.getSource();
			if (src == closeButton)
			{
				
				for (AmbigiousFriendChooserHandler h: handlers)
					h.onFriendChoosen(null);
				
				this.destroy();
			}
			
			if (src instanceof FriendItem){
				for (AmbigiousFriendChooserHandler h: handlers)
					h.onFriendChoosen(((FriendItem)src).getFriend());
				
				this.destroy();
			}
		}
		
		
		public void addAmbigiousFriendChooserHandler(AmbigiousFriendChooserHandler h)
		{
			handlers.add(h);
		}
		
		public void removeAmbigiousFriendChooserHandler(AmbigiousFriendChooserHandler h)
		{
			handlers.remove(h);
		}
		
	}
	
	
	private ComboBoxItem searchField;
	private ListGrid list;
	private FriendsModule friendsModule;
	private LinkedHashMap<String, String> valueMap;
	private UserLanguage lang;
	
	
	public FriendChooserList(UserLanguage lang, FriendsModule friendModule){
		this.lang = lang;
		list = new ListGrid(){
		    @Override  
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {  
  
                String fieldName = this.getFieldName(colNum);  
  
                if (fieldName.equals("profilePic")) {  
                   
                	Img profile = new Img(record.getAttribute("profilePic"));  
                    profile.setShowDown(false);  
                    profile.setShowRollOver(false);  
                    profile.setLayoutAlign(Alignment.CENTER);  
                    profile.setSrc(record.getAttribute("profilePic"));  
                    profile.setHeight(16);  
                    profile.setWidth(16);  
                 
                    return profile;
                } else if (fieldName.equals("displayName")) {  
                   Label dispName = new Label(record.getAttribute("displayName"));
                    dispName.setHeight(18);  
                    dispName.setWidth100();                    
                    return dispName;  
                } else {  
                    return null;  
                }  
   
		    }  
			
		};
		list.setData(new RecordList());
		list.setWidth100();
		list.setHeight100();
		list.setCanRemoveRecords(true);
		
		
		ListGridField profileField = new ListGridField("profilePic", "Profile Picture");
		profileField.setType(ListGridFieldType.IMAGE);
		ListGridField nameField = new ListGridField("displayName", "Displayed Name");  
		
		list.setFields(profileField, nameField);
		list.setShowHeader(false);
		
		searchField = new ComboBoxItem();
		searchField.setTitle(lang.FriendChooserListEnterName());
		searchField.setWidth("100%");
		searchField.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				
				
				if (event.getKeyName().equals("Enter")){
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
        
	}
	
	
	private void onSearchFriendRequested(){
		
		String search = searchField.getValueAsString();
		
		if (search == null || search.isEmpty()){
			NotificationManager.getInstance().showTipNotification(new TipNotification(lang.FriendChooserNoOneFound(), null, 1500, NotificationPosition.CENTER), AnimationEffect.FADE);
			return;
		}
		
		
		FriendDisplayNameResults res = friendsModule.getFriendByDisplayName(search);
		
		if (res.getSerachString().equals(searchField.getValueAsString()))
		{
			if (res.getResult().size()>1)
			{
				AmbigiousFriendChooser c = new AmbigiousFriendChooser(this.lang, res.getResult());
				c.addAmbigiousFriendChooserHandler(this);
			}
			else
			if (res.getResult().isEmpty())
				NotificationManager.getInstance().showTipNotification(new TipNotification(lang.FriendChooserNoOneFound(), null, 1500, NotificationPosition.CENTER), AnimationEffect.FADE);
			else
			if(res.getResult().size()==1){ // exactly one
				for (Friend f: res.getResult())
					addFriendToList(f);
			}
		}
		
		
	}
	
	
	private void addFriendToList(Friend f){
		
		RecordList l = list.getDataAsRecordList();
		
		for (Record r: l.toArray())
			if (r instanceof FriendListGridRecord)
				if (((FriendListGridRecord) r).getFriend().equals(f))
				{
					NotificationManager.getInstance().showTipNotification(new TipNotification(lang.FriendChooserAlreadyInList(), null, 1500, NotificationPosition.CENTER), AnimationEffect.FADE);
					return;
				}
		
		l.add(new FriendListGridRecord(f));
		list.setData(l);
	}



	@Override
	public void onFriendChoosen(Friend f) {
		
		if (f != null)
			addFriendToList(f);
	}
	
	
	
	public Set<Friend> getSelectedFriends(){
		
		Record[] l = list.getDataAsRecordList().toArray();
		
		Set<Friend> ret = new LinkedHashSet<Friend>();
		
		for (Record r: l){
			if( r instanceof FriendListGridRecord){
				ret.add(((FriendListGridRecord) r).getFriend());
			}
				
		}
		
		
		return ret;
		
	}
	
	

}




