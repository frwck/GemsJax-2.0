package org.gemsjax.server.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.server.communication.channel.FriendsLiveChannel;
import org.gemsjax.server.communication.channel.handler.FriendsChannelHandler;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.AlreadyBefriendedException;
import org.gemsjax.server.persistence.dao.exception.AlreadyExistsException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.shared.communication.message.friend.AllFriendsAnswerMessage;
import org.gemsjax.shared.communication.message.friend.CancelFriendshipAnswerMessage;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.friend.FriendError;
import org.gemsjax.shared.communication.message.friend.FriendErrorAnswerMessage;
import org.gemsjax.shared.communication.message.friend.FriendMessage;
import org.gemsjax.shared.communication.message.friend.FriendshipCanceledMessage;
import org.gemsjax.shared.communication.message.friend.NewFriendAddedMessage;
import org.gemsjax.shared.communication.message.friend.NewFriendshipRequestAnswerMessage;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.UserOnlineState;


/**
 * The {@link FriendModule} executes all friendship operations,
 * like:
 * <ul>
 * <li> create a friendship {@link #createFriendship(RegisteredUser, RegisteredUser)}</li>
 * <li> unfriend - {@link #onCancelFriendships(OnlineUser, Set)}</li>
 * <li> get all friends of a certain user - {@link #onGetAllFriends(OnlineUser)}</li>
 * </ul>
 * 
 * Note: {@link #onCancelFriendships(OnlineUser, Set)} and {@link #onGetAllFriends(OnlineUser)} are methods of a {@link FriendsChannelHandler}
 * and should only be invoked by {@link FriendsLiveChannel}s (Observer pattern)
 * <br /><br />
 * Note also, that this {@link FriendModule} is implemented as singleton. So use {@link #getInstance()} to access this object
 * @author Hannes Dorfmann
 *
 */
public class FriendModule implements FriendsChannelHandler{
	
	private static FriendModule INSTANCE  = new FriendModule();
	private UserDAO userDAO;

	private FriendModule()
	{
		userDAO = new HibernateUserDAO();
	}
	
	
	public static FriendModule getInstance() {
		return INSTANCE;
	}
	
	
	@Override
	public void onGetAllFriends(OnlineUser requester, String referenceId) {
		
		RegisteredUser user = (RegisteredUser)requester.getUser();
		
		Set<Friend> friends = new LinkedHashSet<Friend>();
		for (RegisteredUser f : user.getAllFriends())
		{
			OnlineUser friendOU = OnlineUserManager.getInstance().getOnlineUser(f);
			UserOnlineState friendState = (friendOU==null) ? UserOnlineState.OFFLINE : friendOU.getOnlineState();
				
			friends.add(new Friend(f.getId(),f.getDisplayedName(), friendState , f.getProfilePicture()));
		}
		
		
		try {
			requester.getFriendChannel().send(new AllFriendsAnswerMessage(referenceId, friends) );
		} catch (IOException e) {
			// TODO What to do, if message cant be sent to client
			e.printStackTrace();
		}
		
	}

	@Override
	public void onCancelFriendships(OnlineUser requester, Set<Integer> friendIds, String  referenceId) {
		
		RegisteredUser user = (RegisteredUser) requester.getUser();
		
		Set<Integer> exFriendIds = new LinkedHashSet<Integer>();

		RegisteredUser exFriend;
		
			for (int id: friendIds)
			{
				
				// Check if a Ex-friend is online now
				OnlineUser exOu = OnlineUserManager.getInstance().getOnlineUser(id);
				if (exOu != null) // if online right now
				{
					try
					{
						exFriend = (RegisteredUser) exOu.getUser();
						userDAO.cancelFriendship(user, exFriend);
	
						exFriendIds.add(exFriend.getId());
						Set<Integer> tId = new LinkedHashSet<Integer>();
						tId.add(user.getId());
						exOu.getFriendChannel().send(new FriendshipCanceledMessage(tId));
						// TODO generate and send Notification
						
					} catch (DAOException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// What to do if message could not be sent
						e.printStackTrace();
					}
				}
				else
				{ // if not online
					try {
						
						exFriend = userDAO.getRegisteredUserById(id);
						userDAO.cancelFriendship(user, exFriend);
						exFriendIds.add(exFriend.getId());
						
						// TODO generate Notificatoin
						
					} catch (NotFoundException e) {
						e.printStackTrace();
					} catch (DAOException e) {
						e.printStackTrace();
					}
				}
			}
			
		
			// Finally inform the requester about the friendships, that are now canceled
		
			try {
				requester.getFriendChannel().send(new CancelFriendshipAnswerMessage(referenceId, exFriendIds));
			} catch (IOException e) {
				// TODO what to do if message could not be sent
				e.printStackTrace();
			}
			
	}
	
	
	/**
	 * Create a new Friendship between requester and friend and send all needed {@link FriendMessage}s to both
	 * @param requester
	 * @param friend
	 * @throws DAOException, {@link AlreadyBefriendedException}
	 */
	public void createFriendship(RegisteredUser requester, RegisteredUser friend) throws DAOException, AlreadyBefriendedException 
	{
		userDAO.addFriendship(requester, friend);
		
		// check if request is online and send him a Add
		OnlineUser friendOu = OnlineUserManager.getInstance().getOnlineUser(friend);
				
				if (friendOu!=null)
				{ // requester is online
					
					Friend f = new Friend(requester.getId(), requester.getDisplayedName(), requester.getOnlineState(), requester.getProfilePicture());
					try {
						friendOu.getFriendChannel().send(new NewFriendAddedMessage(f));
					} catch (IOException e) {
						// TODO what to do if message could be sent
						e.printStackTrace();
					}
				}
				
				// TODO store a Notification, if friend is not online right now
				
				
				
				OnlineUser requesterOu = OnlineUserManager.getInstance().getOnlineUser(requester);
				
				if (requesterOu!=null)
				{ // requester is online
					
					Friend f = new Friend(requester.getId(), requester.getDisplayedName(), requester.getOnlineState(), requester.getProfilePicture());
					try {
						requesterOu.getFriendChannel().send(new NewFriendAddedMessage(f));
					} catch (IOException e) {
						// TODO what to do if message could be sent
						e.printStackTrace();
					}
				}
				// TODO store a Notification, if friend is not online right now
				
	
	}
	/*	
		
		
		
		
		
		// Generate answer for the requester
		// if no Exception was thrown, than the friendship has been established correctly
		try {
			requesterOu.getFriendChannel().send(new NewFriendshipRequestAnswerMessage(referenceId, new Friend (friend.getId(), friend.getDisplayedName(), friend.getOnlineState(), friend.getProfilePicture())));
		} catch (IOException e) {
			// what to do if message cant be sent
			e.printStackTrace();
		}
				
	}
*/

	@Override
	public void onNewFriendshipRequest(OnlineUser requester, int friendId, String referenceId) {
		
		RegisteredUser req = (RegisteredUser) requester.getUser();
		
		// Check if friend is online right now
		OnlineUser onFriend = OnlineUserManager.getInstance().getOnlineUser(friendId);
		RegisteredUser friend = null;
		
		if (onFriend != null) // Friend is online right now
			friend = (RegisteredUser) onFriend.getUser();
		else
		{
			try {
		
				friend = userDAO.getRegisteredUserById(friendId);
			} catch (NotFoundException e) {
				try {
					requester.getFriendChannel().send(new FriendErrorAnswerMessage(referenceId, FriendError.FRIEND_ID));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return;
			}
			
		}
		
		
		
		if (req.getAllFriends().contains(friend))
		{
			try {
				requester.getFriendChannel().send(new FriendErrorAnswerMessage(referenceId, FriendError.ALREADY_FRIENDS));
			} catch (IOException e) {
				// TODO what to do if msg cant be sent
				e.printStackTrace();
			}
			return;
		}
		
		try {
			RequestModule.getInstance().createFriendshipRequest(req, friend);
			
			// If everything was fine, generate a positive answer
			try {
				requester.getFriendChannel().send(new NewFriendshipRequestAnswerMessage(referenceId));
			} catch (IOException e) {
				// TODO What to do if message can't be sent
				e.printStackTrace();
			}
			
			
		} catch (AlreadyBefriendedException e1) {
			try {
				requester.getFriendChannel().send(new FriendErrorAnswerMessage(referenceId, FriendError.ALREADY_FRIENDS));
			} catch (IOException e) {
				// TODO What to do if message cant be sent
				e.printStackTrace();
			}
		} catch (AlreadyExistsException e1) {
			try {
				requester.getFriendChannel().send(new FriendErrorAnswerMessage(referenceId, FriendError.ALREADY_REQUESTED));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (DAOException e1) {
			try {
				requester.getFriendChannel().send(new FriendErrorAnswerMessage(referenceId, FriendError.DATABASE));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
	}

}
