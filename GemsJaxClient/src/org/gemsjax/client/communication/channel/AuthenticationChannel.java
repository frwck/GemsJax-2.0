package org.gemsjax.client.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import org.gemsjax.client.communication.channel.handler.AuthenticationChannelHandler;
import org.gemsjax.client.communication.parser.SystemMessageParser;
import org.gemsjax.client.user.RegisteredUserImpl;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.system.LoginAnswerMessage;
import org.gemsjax.shared.communication.message.system.LoginAnswerMessage.LoginAnswerStatus;
import org.gemsjax.shared.communication.message.system.LoginMessage;
import org.gemsjax.shared.communication.message.system.LogoutMessage;
import org.gemsjax.shared.communication.message.system.LogoutMessage.LogoutReason;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage;
import org.gemsjax.shared.communication.message.system.SystemErrorMessage;
import org.gemsjax.shared.communication.message.system.SystemMessage;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.UserOnlineState;

import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.xml.client.DOMException;

/**
 * The {@link AuthenticationChannel} is responsible for {@link LoginMessage} and {@link LogoutMessage}.
 * @author Hannes Dorfmann
 *
 */
public class AuthenticationChannel implements InputChannel, OutputChannel{
	
	
	private CommunicationConnection connection;
	private SystemMessageParser parser;

	private RegExp regEx;
	private Set<AuthenticationChannelHandler> handlers;
	
	/**
	 * Creates a new {@link AuthenticationChannel} and register this {@link AuthenticationChannel}
	 * as {@link InputChannel} on the passed {@link CommunicationConnection}
	 * @param connection
	 */
	public AuthenticationChannel(CommunicationConnection connection)
	{
		this.connection = connection;
		handlers = new LinkedHashSet<AuthenticationChannelHandler>();
		parser = new SystemMessageParser();
		
		String regEx1 = RegExFactory.startWithTagSubTag(SystemMessage.TAG, LoginAnswerMessage.TAG);
		String regEx2 = RegExFactory.startWithTagSubTag(SystemMessage.TAG, LogoutMessage.TAG);
		String regEx3 = RegExFactory.startWithTagSubTag(SystemMessage.TAG, SystemErrorMessage.TAG);
		
		String regExFilter = RegExFactory.createOr(regEx1, regEx2);
		regExFilter = RegExFactory.createOr(regExFilter, regEx3);
		
		regEx = RegExp.compile(regExFilter);
		connection.registerInputChannel(this);
	}
	
	public void addAuthenticationChannelHandler(AuthenticationChannelHandler h)
	{
		handlers.add(h);
	}
	
	
	public void removeAuthenticationChannelHandler(AuthenticationChannelHandler h)
	{
		handlers.remove(h);
	}
	

	@Override
	public boolean isMatchingFilter(String msg) {
		return regEx.test(msg);
	}

	
	@Override
	public void onMessageReceived(InputMessage msg) {
		try{
			SystemMessage m = parser.parseMessage(msg.getText());
			
			if (m instanceof LoginAnswerMessage)
			{
				LoginAnswerMessage am = (LoginAnswerMessage) m;
				
				if (am.getAnswer()== LoginAnswerStatus.OK) // Login successful
				{
					RegisteredUser u = new RegisteredUserImpl(am.getUserId(), am.getDisplayedName(), UserOnlineState.ONLINE);
					fireLoginAnswer(am.getAnswer(),u, am.getUnreadNotifications());
				}
				else // Login fail
				{
					fireLoginAnswer(am.getAnswer(), null, 0l);
				}
			}
			else
			if (m instanceof LogoutMessage){
				fireLogout(((LogoutMessage)m).getLogoutReason());
			}
			else
			if (m instanceof SystemErrorMessage)
			{
				SystemErrorMessage sm = (SystemErrorMessage) m;
				
				
				fireParseError(new Exception("A serverside Error has occurred: "+sm.getError().getErrorType()+ ": "+sm.getError().getAdditionalInfo()));
			}
		}
		catch (DOMException e)
		{
			fireParseError(e);
		}
	}

	@Override
	public void send(Message message) throws IOException {
		connection.send(message);
	}
	
	
	/**
	 * Inform all registered {@link AuthenticationChannelHandler}s that a Logout was received
	 * @param reason
	 */
	private void fireLogout(LogoutReason reason)
	{
		for (AuthenticationChannelHandler h : handlers)
			h.onLogout(reason);
	}
	
	/**
	 * Inform all registered {@link AuthenticationChannelHandler} that a {@link LoginAnswerMessage} is received
	 * @param status
	 * @param authenticatedUser
	 */
	private void fireLoginAnswer(LoginAnswerStatus status, RegisteredUser authenticatedUser, Long unreadNotifications)
	{
		for (AuthenticationChannelHandler h : handlers)
			h.onLoginAnswer(status, authenticatedUser, unreadNotifications);
	}
	
	
	/**
	 * Inform all registered {@link AuthenticationChannelHandler} that an error while parsing a message has occurred
	 * @param The {@link Exception} that was thrown
	 */
	private void fireParseError(Exception e)
	{
		for (AuthenticationChannelHandler h : handlers)
			h.onParseError(e);
	}
	
	
	/**
	 * Do a login by sending a {@link LoginMessage} to the server
	 * @param username
	 * @param password
	 * @throws IOException
	 */
	public void doLogin(String username, String password) throws IOException
	{
		send(new LoginMessage(username, password, false));
	}
	
	
	/**
	 * Do a logout by sending a {@link LogoutMessage} with {@link LogoutReason#CLIENT_USER_LOGOUT} to the server
	 * @throws IOException
	 */
	public void doLogout() throws IOException
	{
		send(new LogoutMessage(LogoutReason.CLIENT_USER_LOGOUT));
	}

	@Override
	public void onMessageRecieved(Message msg) {
		// TODO Auto-generated method stub
		
	}

}
