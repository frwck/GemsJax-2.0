package org.gemsjax.client.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.RegistrationChannel;
import org.gemsjax.client.communication.channel.handler.RegistrationChannelHandler;
import org.gemsjax.client.module.handler.RegistrationModuleHandler;
import org.gemsjax.shared.communication.message.system.NewRegistrationMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;

import com.google.gwt.http.client.URL;

/**
 * A {@link RegistrationModule} is responsible to execute a new user registration (sign up).
 * <b>Note:</b> The workflow is designed for sinlge thread only.
 * @author Hannes Dorfmann
 *
 */
public class RegistrationModule  implements RegistrationChannelHandler{

	private RegistrationChannel channel;
	private Set<RegistrationModuleHandler> handlers;
	
	public RegistrationModule(RegistrationChannel c)
	{
			channel = c;
			channel.addRegistrationChannelHandler(this);
			handlers = new LinkedHashSet<RegistrationModuleHandler>();
	}

	
	public void doRegisttraion(String username, String password, String email) throws IOException
	{
		channel.send(new NewRegistrationMessage(username, password, email));
	}
	
	
	public void addRegistrationModuleHandler(RegistrationModuleHandler h)
	{
		handlers.add(h);
	}
	
	public void removeRegistrationModuleHandler(RegistrationModuleHandler h)
	{
		handlers.remove(h);
	}
	
	@Override
	public void onRegistrationSuccessful() {
		for (RegistrationModuleHandler h: handlers)
			h.onRegistrationSuccessful();
	}

	@Override
	public void onRegistrationFailed(RegistrationAnswerStatus status, String fail) {
		for (RegistrationModuleHandler h: handlers)
			h.onRegistrationFailed(status, fail);
	}

	@Override
	public void onError(Throwable t) {
		for (RegistrationModuleHandler h: handlers)
			h.onError(t);
	}
}
