package org.gemsjax.shared.communication.message.collaborateablefile;

/**
 * Sent from server to the client as a negative response on a {@link ReferenceableCollaborateableFileMessage}
 * @author Hannes Dorfmann
 *
 */
public class CollaborateableFileErrorMessage extends ReferenceableCollaborateableFileMessage{

	public static final String TAG ="error";
	public static final String ATTRIBUTE_REASON ="reason";
	
	private CollaborateableFileError error;
	
	public CollaborateableFileErrorMessage(String referenceId, CollaborateableFileError error) {
		super(referenceId);
		this.error = error;
	}

	@Override
	public String toXml() {
		return super.openingXml()+"<"+TAG+" "+ATTRIBUTE_REASON+"=\""+error.toConstant()+"\"/>"+super.closingXml();
	}

	public CollaborateableFileError getError(){
		return error;
	}
}
