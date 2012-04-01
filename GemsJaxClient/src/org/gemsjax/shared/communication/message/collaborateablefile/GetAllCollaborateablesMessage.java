package org.gemsjax.shared.communication.message.collaborateablefile;

/**
 * Sent from client to the server to retrieve all collaborateables of one user
 * @author Hannes Dorfmann
 *
 */
public class GetAllCollaborateablesMessage extends ReferenceableCollaborateableFileMessage{

	public static final String TAG="all";
	
	public GetAllCollaborateablesMessage(String referenceId) {
		super(referenceId);
	
	}

	@Override
	public String toXml() {
		return super.openingXml()+"<"+TAG+" />"+super.closingXml();
	}

}
