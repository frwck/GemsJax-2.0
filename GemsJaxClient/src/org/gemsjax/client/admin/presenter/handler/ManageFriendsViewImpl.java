package org.gemsjax.client.admin.presenter.handler;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.notification.Notification.NotificationPosition;
import org.gemsjax.client.admin.notification.NotificationManager;
import org.gemsjax.client.admin.notification.TipNotification;
import org.gemsjax.client.admin.view.ManageFriendsView;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.friend.FriendError;

import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.util.SC;

public class ManageFriendsViewImpl implements ManageFriendsView{

		
		private UserLanguage lang;
		
		public ManageFriendsViewImpl(UserLanguage lang)
		{
			this.lang = lang;
		}
		
		
		
		@Override
		public void showAlreadyBefriendedMessage(Friend friend) {
			SC.warn(lang.ManageFriendshipAlreadyBefriended()+" "+friend.getDisplayName());
		}

		@Override
		public void showYetBefriended(Friend friend) {
			SC.warn(lang.ManageFriendshipAlreadyFriendsError());
		}

		@Override
		public void showUnexpectedError(Throwable t) {
			SC.warn(lang.ManageFriendshipUnexpectedError());
		}

		@Override
		public void showFriendshipRequestError(String userDisplayName, FriendError error) {
			
			String msg= lang.ManageFriendshipReqeustError()+userDisplayName;
			switch (error)
			{
			case PARSING: msg += lang.ManageFriendshipParsingError();break;
			case DATABASE: msg +=lang.ManageFriendshipDatabaseError();break;
			case ALREADY_REQUESTED: msg += lang.ManageFriendshipAlreadyRequestedError();break;
			case FRIEND_ID: msg += lang.ManageFriendshipFriendIdError(); break;
			}
			
			SC.warn(msg);
		}



		@Override
		public void showFriendshipRequestSuccessful(String userDisplayName) {
			NotificationManager.getInstance().showTipNotification(new TipNotification(lang.ManageFriendshipRequestSuccessful()+userDisplayName, null, 2000, NotificationPosition.BOTTOM_CENTERED), AnimationEffect.FADE);
		}



		@Override
		public void showCancelFriendshipSuccessful(Friend exFriend) {
			NotificationManager.getInstance().showTipNotification(new TipNotification(lang.ManageCancelFriendshipSuccessful1()+exFriend.getDisplayName()+lang.ManageCancelFriendshipSuccessful2(), null, 2000, NotificationPosition.BOTTOM_CENTERED), AnimationEffect.FADE);
		}



		@Override
		public void showCancelFriendshipError(Friend friendToCancel, FriendError error) {
			
			String msg= lang.ManageCancelFriendshipError1()+friendToCancel.getDisplayName()+ lang.ManageCancelFriendshipError2();
			switch (error)
			{
				case PARSING: msg += lang.ManageFriendshipParsingError();break;
				case DATABASE: msg +=lang.ManageFriendshipDatabaseError();break;
				case ALREADY_REQUESTED: msg += lang.ManageFriendshipAlreadyRequestedError();break;
				case FRIEND_ID: msg += lang.ManageFriendshipFriendIdError(); break;
			}
			
			SC.warn(msg);
			
		}
		


}
