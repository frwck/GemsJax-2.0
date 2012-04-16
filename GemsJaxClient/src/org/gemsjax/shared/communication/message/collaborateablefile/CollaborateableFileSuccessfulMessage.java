package org.gemsjax.shared.communication.message.collaborateablefile;

import org.gemsjax.shared.collaboration.Collaborateable;

/**
 * This Message is send from the server to the client as a positive resong on a {@link NewCollaborateableFileMessage} or {@link UpdateCollaborateableFileMessage}
 * to inform that a {@link Collaborateable}s meta data has been created or updated successfully.
 * @author Hannes Dorfmann
 *
 */
public class CollaborateableFileSuccessfulMessage extends ReferenceableCollaborateableFileMessage {

	public static final String TAG ="ok";
	
	public CollaborateableFileSuccessfulMessage(String referenceId) {
		super(referenceId);
	
	}

	@Override
	public String toXml() {
		return super.openingXml()+"<"+TAG+" />"+super.closingXml();
	}
	
	

}
