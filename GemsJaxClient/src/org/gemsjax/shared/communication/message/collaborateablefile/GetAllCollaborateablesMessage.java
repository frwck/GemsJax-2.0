package org.gemsjax.shared.communication.message.collaborateablefile;

/**
 * Sent from client to the server to retrieve all collaborateables of one user
 * @author Hannes Dorfmann
 *
 */
public class GetAllCollaborateablesMessage extends ReferenceableCollaborateableFileMessage{

	public static final String TAG="all";
	public static final String ATTRIBUTE_TYPE="type";
	
	private CollaborateableType type;
	
	public GetAllCollaborateablesMessage(String referenceId, CollaborateableType type) {
		super(referenceId);
		this.type = type;
	
	}

	@Override
	public String toXml() {
		return super.openingXml()+"<"+TAG+" "+ATTRIBUTE_TYPE+"=\""+type.toConstant()+"\" />"+super.closingXml();
	}
	
	
	public CollaborateableType getType(){
		return type;
	}
	

}
