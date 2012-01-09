package org.gemsjax.client.communication.channel.handler;

import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.message.system.LoginAnswerMessage.LoginAnswerStatus;
import org.gemsjax.shared.communication.message.system.LogoutMessage;
import org.gemsjax.shared.communication.message.system.LogoutMessage.LogoutReason;
import org.gemsjax.shared.user.RegisteredUser;

/**
 * This is a handler for an {@link AuthenticationChannelHandler}
 * @author Hannes Dorfmann
 *
 */
public interface AuthenticationChannelHandler {

	/**
	 * Is called, if the observed {@link AuthenticationChannel} has received a {@link LogoutMessage}
	 * @param reason
	 */
	public void onLogout(LogoutReason reason);
	
	/**
	 * 
	 RegisteredUser authenticatedUser
	 * @param answerStatus
	 */
	public void onLoginAnswer(LoginAnswerStatus answerStatus, RegisteredUser authenticatedUser);
	
	/**
	 * If an unexpected parse error occures in the observed {@link AuthenticationChannel} while receiving an parsing a {@link InputMessage}
	 * for the underlying {@link CommunicationConnection}
	 * @param e
	 */
	public void onParseError(Exception e);
}
