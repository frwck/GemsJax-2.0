package org.gemsjax.client.module.handler;
import org.gemsjax.client.module.RegistrationModule;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;


/**
 * This is a handler to get updates from a  {@link RegistrationModule}s.
 * @author Hannes Dorfmann
 *
 */
public interface RegistrationModuleHandler {

	public void onRegistrationFailed(RegistrationAnswerStatus status, String failString);


	public void onRegistrationSuccessful();


	public void onError(Throwable t);
}
