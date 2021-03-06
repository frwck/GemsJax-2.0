package org.gemsjax.server.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.server.communication.channel.handler.FriendsChannelHandler;
import org.gemsjax.server.communication.parser.FriendMessageParser;
import org.gemsjax.server.module.OnlineUser;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.friend.CancelFriendshipMessage;
import org.gemsjax.shared.communication.message.friend.FriendError;
import org.gemsjax.shared.communication.message.friend.FriendErrorAnswerMessage;
import org.gemsjax.shared.communication.message.friend.FriendMessage;
import org.gemsjax.shared.communication.message.friend.FriendUpdateMessage;
import org.gemsjax.shared.communication.message.friend.FriendshipCanceledMessage;
import org.gemsjax.shared.communication.message.friend.GetAllFriendsMessage;
import org.gemsjax.shared.communication.message.friend.NewFriendshipRequestAnswerMessage;
import org.gemsjax.shared.communication.message.friend.NewFriendshipRequestMessage;
import org.gemsjax.shared.communication.message.friend.ReferenceableFriendMessage;
import org.gemsjax.shared.request.FriendshipRequest;
import org.gemsjax.shared.user.UserOnlineState;
import org.xml.sax.SAXException;


/**
 * Communicate with the client with a "live" connection.
 * "Live" connection means, that the server is able to send messages to the client via push.
 * This channel listens for:
 * <ul>
 * <li> {@link CancelFriendshipMessage}s </li>
 * <li> {@link GetAllFriendsMessage} </li>
 * <ul>
 * 
 * and sends the following messages to the client:
 * <ul>
 * <li> {@link AddFriendsAnswerMessage} as response on {@link GetAllFriendsMessage} and to add a new friend, in case a new {@link FriendshipRequest} has been accepted
 * <li> {@link FriendshipCanceledMessage} to inform the concerning ex-friend, that a friendship has been canceled </li>
 * <li> {@link FriendUpdateMessage} to inform the user, that one of his friends has changed his information like {@link UserOnlineState}, display name or profile picture.
 * Note that a {@link FriendUpdateMessage} is not a response on a previously received message, but will be pushed to the client on profile information changement (invoked by the friend).
 * <li> {@link FriendErrorMessage} if an error on server has side occurred (like parsing or database errors)
 * </ul>
 * 
 * @author Hannes Dorfmann
 *
 */
public class FriendsLiveChannel implements InputChannel, OutputChannel{

	private CommunicationConnection connection;
	private String filterRegEx;
	private FriendMessageParser parser;
	private Set<FriendsChannelHandler> handlers;
	private OnlineUser user;
	
	public FriendsLiveChannel(CommunicationConnection connection, OnlineUser user)
	{
		this.connection = connection;
		connection.registerInputChannel(this);
		this.user = user;
		parser = new FriendMessageParser();
		handlers = new LinkedHashSet<FriendsChannelHandler>();
		filterRegEx = RegExFactory.startWithTag(FriendMessage.TAG);
	}
	
	public void addFriendsChannelHandler(FriendsChannelHandler h)
	{
		handlers.add(h);
	}
	
	public void removeFriendsChanneslHandler(FriendsChannelHandler h)
	{
		handlers.remove(h);
	}

	@Override
	public void send(Message arg0) throws IOException {
		connection.send(arg0);
	}

	@Override
	public boolean isMatchingFilter(String msg) {
		return msg.matches(filterRegEx);
	}

	@Override
	public void onMessageReceived(InputMessage msg) {
		
		
		try {
			
			FriendMessage fm = parser.parse(msg.getText());
			
			OnlineUser authenticatedUser = user;
			
			if (authenticatedUser==null) // Check if user is authenticated
			{
				if (fm instanceof ReferenceableFriendMessage)
					send(new FriendErrorAnswerMessage(((ReferenceableFriendMessage) fm).getReferenceId(), FriendError.AUTHENTICATION));
				else
					send(new FriendErrorAnswerMessage(null, FriendError.AUTHENTICATION));
				
				return;
			}
			
			
			
			if (fm instanceof CancelFriendshipMessage )
				fireCancelFriendship(authenticatedUser, ((CancelFriendshipMessage) fm).getFriendIds(), ((CancelFriendshipMessage) fm).getReferenceId());
			
			else
			if (fm instanceof GetAllFriendsMessage)
				fireGetAllFriends(authenticatedUser, ((GetAllFriendsMessage) fm).getReferenceId());
			
			else
			if (fm instanceof NewFriendshipRequestMessage)
				fireNewFriendshipRequest(authenticatedUser, ((NewFriendshipRequestMessage) fm).getReceiverId(), ((NewFriendshipRequestMessage) fm).getReferenceId());
			
			else
			send(new FriendErrorAnswerMessage(null, FriendError.PARSING,"Could not determine the type of this message"));
		
		} catch (SAXException e) {
			
			try {
				send(new FriendErrorAnswerMessage(parser.getCurrentReferenceId(), FriendError.PARSING, e.getMessage()));
			} catch (IOException e1) {
				// TODO What to do, if error message could not be sent to the client?
				e1.printStackTrace();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
			
			try {
				send(new FriendErrorAnswerMessage(parser.getCurrentReferenceId(), FriendError.PARSING, e.getMessage()));
			} catch (IOException e1) {
				// TODO What to do, if error message could not be sent to the client?
				e1.printStackTrace();
			}
		}
		
	}
	
	
	
	private void fireCancelFriendship(OnlineUser user, Set<Integer> friendIds, String referenceId)
	{
		for (FriendsChannelHandler h: handlers)
			h.onCancelFriendships(user, friendIds, referenceId);
	}
	
	
	private void fireGetAllFriends(OnlineUser user, String referenceId)
	{
		for (FriendsChannelHandler h: handlers)
			h.onGetAllFriends(user, referenceId);
	}
	
	private void fireNewFriendshipRequest(OnlineUser user, int friendId, String referenceId)
	{
		for (FriendsChannelHandler h: handlers)
			h.onNewFriendshipRequest(user, friendId, referenceId);
	}

	@Override
	public void onMessageRecieved(Message arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
