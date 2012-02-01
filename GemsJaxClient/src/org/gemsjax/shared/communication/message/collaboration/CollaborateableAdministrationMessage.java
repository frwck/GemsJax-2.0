package org.gemsjax.shared.communication.message.collaboration;

import org.gemsjax.shared.communication.message.Message;

/**
 * The abstract super class for every collaborateable administration message, like creating , deleting and editing collaborateables.
 * <b>Note</b> The content of the {@link CollaborateableType} itself is not manipulated with this kind of messages
 * @author Hannes Dorfmann
 *
 */
public abstract class CollaborateableAdministrationMessage implements Message{
	
	public static final String TAG ="col-file";

}
