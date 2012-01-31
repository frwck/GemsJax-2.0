package org.gemsjax.client.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.handler.FriendsLiveChannelHandler;
import org.gemsjax.client.communication.parser.FriendMessageParser;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.friend.AllFriendsAnswerMessage;
import org.gemsjax.shared.communication.message.friend.CancelFriendshipAnswerMessage;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.friend.FriendError;
import org.gemsjax.shared.communication.message.friend.FriendErrorAnswerMessage;
import org.gemsjax.shared.communication.message.friend.FriendMessage;
import org.gemsjax.shared.communication.message.friend.FriendUpdateMessage;
import org.gemsjax.shared.communication.message.friend.FriendshipCanceledMessage;
import org.gemsjax.shared.communication.message.friend.NewFriendAddedMessage;
import org.gemsjax.shared.communication.message.friend.NewFriendshipRequestAnswerMessage;

import com.google.gwt.regexp.shared.RegExp;

/**
 * This channel listen to incoming {@link FriendUpdateMessage}s and handle them in real time.
 * That means, that the server can push messages to this channel.
 * @author Hannes Dorfmann
 *
 */
public class FriendsLiveChannel implements InputChannel, OutputChannel{

	private CommunicationConnection connection;
	private RegExp regEx;
	private FriendMessageParser parser;
	private Set<FriendsLiveChannelHandler> handlers;
	
	
	public FriendsLiveChannel(CommunicationConnection connection)
	{
		this.connection = connection;
		this.connection.registerInputChannel(this);
		parser = new FriendMessageParser();
		handlers = new LinkedHashSet<FriendsLiveChannelHandler>();
		
		regEx = RegExp.compile( RegExFactory.startWithTag(FriendMessage.TAG) );
	}
	
	
	public void addFriendsChannelHandler(FriendsLiveChannelHandler h)
	{
		handlers.add(h);
	}
	
	
	public void removeFriendsChannelHandler(FriendsLiveChannelHandler h)
	{
		handlers.remove(h);
	}
	
	@Override
	public void send(Message message) throws IOException {
		connection.send(message);
	}

	@Override
	public void onMessageReceived(InputMessage msg) {
		FriendMessage m = parser.parseMessage(msg.getText());
		
		if (m instanceof AllFriendsAnswerMessage)
		{
			AllFriendsAnswerMessage am = ((AllFriendsAnswerMessage)m);
			
			fireAllFriends(am.getReferenceId(), am.getFriends());
		}
		else
		if (m instanceof FriendUpdateMessage)
		{
			fireFriendUpdate(((FriendUpdateMessage)m).getFriend());
		}
		else
		if(m instanceof FriendErrorAnswerMessage)
		{
			FriendErrorAnswerMessage fm = (FriendErrorAnswerMessage) m;
			fireError(fm.getReferenceId(), fm.getError(), fm.getAdditionalInfo());
		}
		else
		if (m instanceof FriendshipCanceledMessage)
		{
			fireFriendshipCanceled(((FriendshipCanceledMessage)m).getExFriendId());
		}
		else
		if (m instanceof CancelFriendshipAnswerMessage)
		{
			CancelFriendshipAnswerMessage cm = ((CancelFriendshipAnswerMessage)m);
			
			fireCancelAnswer(cm.getReferenceId(), cm.getFriendIds());
		}
		else
		if (m instanceof NewFriendAddedMessage)
		{
			fireNewFriendAdded(((NewFriendAddedMessage)m).getFriend());
		}
		else
		if (m instanceof NewFriendshipRequestAnswerMessage)
		{
			NewFriendshipRequestAnswerMessage rm = (NewFriendshipRequestAnswerMessage) m;
			fireNewFriendshipAnswerMessage(rm.getReferenceId(), rm.getFriend());
		}
		
	}
	
	
	
	private void fireNewFriendshipAnswerMessage(String referenceId, Friend f)
	{
		for (FriendsLiveChannelHandler h: handlers)
			h.onNewFriendshipRequestAnswer(referenceId, f);
	}
	
	
	private void fireAllFriends(String referenceId, Set<Friend> friends)
	{
		for (FriendsLiveChannelHandler h: handlers)
			h.onAllFriendsReceived(referenceId, friends);
	}
	
	
	private void fireFriendshipCanceled(Set<Integer> exFriendIds)
	{
		for (FriendsLiveChannelHandler h: handlers)
			h.onFriendshipCanceled(exFriendIds);
	}
	
	
	private void fireFriendUpdate(Friend f)
	{
		for (FriendsLiveChannelHandler h: handlers)
			h.onFriendUpdate(f);
	}
	
	
	private void fireNewFriendAdded(Friend f)
	{
		for (FriendsLiveChannelHandler h: handlers)
			h.onNewFriendAdded(f);
	}

	
	private void fireError(String referenceId, FriendError error, String additionalInfo)
	{
		for (FriendsLiveChannelHandler h: handlers)
			h.onError(referenceId, error, additionalInfo);
	}
	
	/*
	private void fireUnexpectedError(Throwable t)
	{
		for (FriendsLiveChannelHandler h: handlers)
			h.onUnexpectedError(t);
	}
	*/
	
	private void fireCancelAnswer(String referenceId, Set<Integer> exFriendIds)
	{
		for (FriendsLiveChannelHandler h: handlers)
			h.onCancelFriendshipAnswer(referenceId, exFriendIds);
	}
	
	
	@Override
	public boolean isMatchingFilter(String msg) {
		return regEx.test(msg);
	}
	
	

}
