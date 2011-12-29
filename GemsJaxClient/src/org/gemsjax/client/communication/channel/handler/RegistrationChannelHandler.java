package org.gemsjax.client.communication.channel.handler;

import org.gemsjax.client.communication.channel.RegistrationChannel;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;

/**
 * An handler to observer a {@link RegistrationChannel} 
 * @author Hannes Dorfmann
 *
 */
public interface RegistrationChannelHandler {

	/**
	 * Called by the underlying {@link RegistrationChannel} to inform that the registration was successful
	 */
	public abstract void onRegistrationSuccessful();
	
	/**
	 * Called by the underlying {@link RegistrationChannel} to inform that the registration was not successful
	 * @param statusThe {@link RegistrationAnswerStatus}
	 * @param fail Is the string that was the reason, why the registration has failed. This could be the submited username or email address
	 */
	public abstract void onRegistrationFailed(RegistrationAnswerStatus status, String fail);
	
	/**
	 * Called by the underlying {@link RegistrationChannel} to inform, that an unexpected error has occurred
	 * @param t {@link Throwable}
	 */
	public abstract void onError(Throwable t);
	
}
