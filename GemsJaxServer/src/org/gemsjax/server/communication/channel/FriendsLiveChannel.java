package org.gemsjax.server.communication.channel;

import java.io.IOException;

import org.gemsjax.server.communication.parser.FriendMessageParser;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.CommunicationError;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.CommunicationError.ErrorType;
import org.gemsjax.shared.communication.message.friend.AddFriendsAnswerMessage;
import org.gemsjax.shared.communication.message.friend.CancelFriendshipMessage;
import org.gemsjax.shared.communication.message.friend.FriendErrorMessage;
import org.gemsjax.shared.communication.message.friend.FriendMessage;
import org.gemsjax.shared.communication.message.friend.FriendUpdateMessage;
import org.gemsjax.shared.communication.message.friend.FriendshipCanceledMessage;
import org.gemsjax.shared.communication.message.friend.GetAllFriendsMessage;
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
	
	public FriendsLiveChannel(CommunicationConnection connection)
	{
		this.connection = connection;
		parser = new FriendMessageParser();
		String reg1 = RegExFactory.startWithTagSubTag(FriendMessage.TAG, GetAllFriendsMessage.TAG);
		String reg2 = RegExFactory.startWithTagSubTag(FriendMessage.TAG, CancelFriendshipMessage.TAG);

		filterRegEx = RegExFactory.createOr(reg1, reg2);
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
		
		
		
		
		} catch (SAXException e) {
			
			try {
				send(new FriendErrorMessage(new CommunicationError(ErrorType.PARSE, e.getMessage())));
			} catch (IOException e1) {
				// TODO What to do, if error message could not be sent to the client?
				e1.printStackTrace();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
			
			try {
				send(new FriendErrorMessage(new CommunicationError(ErrorType.PARSE, e.getMessage())));
			} catch (IOException e1) {
				// TODO What to do, if error message could not be sent to the client?
				e1.printStackTrace();
			}
		}
		
	}
	
	
}
