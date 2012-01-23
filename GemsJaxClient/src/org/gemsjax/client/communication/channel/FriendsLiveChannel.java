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
import org.gemsjax.shared.communication.message.friend.AddFriendsAnswerMessage;
import org.gemsjax.shared.communication.message.friend.CancelFriendshipAnswerMessage;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.friend.FriendErrorMessage;
import org.gemsjax.shared.communication.message.friend.FriendMessage;
import org.gemsjax.shared.communication.message.friend.FriendUpdateMessage;
import org.gemsjax.shared.communication.message.friend.FriendshipCanceledMessage;

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
		parser = new FriendMessageParser();
		handlers = new LinkedHashSet<FriendsLiveChannelHandler>();
		
		String regEx1 = RegExFactory.startWithTagSubTag(FriendMessage.TAG, FriendUpdateMessage.TAG);
		String regEx2 = RegExFactory.startWithTagSubTag(FriendMessage.TAG, FriendErrorMessage.TAG);
		String regEx3 = RegExFactory.startWithTagSubTag(FriendMessage.TAG, AddFriendsAnswerMessage.TAG);
		String regEx4 = RegExFactory.startWithTagSubTag(FriendMessage.TAG, FriendshipCanceledMessage.TAG);
		String regEx5 = RegExFactory.startWithTagSubTag(FriendMessage.TAG, CancelFriendshipAnswerMessage.TAG);
		
		regEx = RegExp.compile( RegExFactory.createOr(regEx1, regEx2, regEx3, regEx4, regEx5) );
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
		
		if (m instanceof AddFriendsAnswerMessage)
		{
			fireAllFriends(((AddFriendsAnswerMessage)m).getFriends());
		}
		else
		if (m instanceof FriendUpdateMessage)
		{
			fireFriendUpdate(((FriendUpdateMessage)m).getFriend());
		}
		else
		if(m instanceof FriendErrorMessage)
		{
			FriendErrorMessage fm = (FriendErrorMessage) m;
			
			switch (fm.getError().getErrorType())
			{
			case AUTHENTICATION: fireAuthenticationError(); break;
			case PARSE: fireUnexpectedError(new Exception("An unexpected parse error has occurred on server side: "+fm.getError().getAdditionalInfo())); break;
			case DATABASE:  fireUnexpectedError(new Exception("An unexpected databasae error has occurred on server side: "+fm.getError().getAdditionalInfo())); break;
			default: fireUnexpectedError(new Exception("An unexpected error has occurred. Error type could not be determined"));break;
			}
			
		}
		else
		if (m instanceof FriendshipCanceledMessage)
		{
			fireFriendshipCanceled(((FriendshipCanceledMessage)m).getExFriendId());
		}
		else
		if (m instanceof CancelFriendshipAnswerMessage)
		{
			fireCancelAnswer(((CancelFriendshipAnswerMessage)m).getFriendIds());
		}
		
	}
	
	
	
	private void fireAllFriends(Set<Friend> friends)
	{
		for (FriendsLiveChannelHandler h: handlers)
			h.onAllFriendsReceived(friends);
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

	
	private void fireAuthenticationError()
	{
		for (FriendsLiveChannelHandler h: handlers)
			h.onAutenticationError();
	}
	
	
	private void fireUnexpectedError(Throwable t)
	{
		for (FriendsLiveChannelHandler h: handlers)
			h.onUnexpectedError(t);
	}
	
	
	private void fireCancelAnswer(Set<Integer> exFriendIds)
	{
		for (FriendsLiveChannelHandler h: handlers)
			h.onCancelFriendshipAnswer(exFriendIds);
	}
	
	
	@Override
	public boolean isMatchingFilter(String msg) {
		return regEx.test(msg);
	}
	
	

}
