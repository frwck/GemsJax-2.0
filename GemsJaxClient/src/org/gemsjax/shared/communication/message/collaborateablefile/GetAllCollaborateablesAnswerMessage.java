package org.gemsjax.shared.communication.message.collaborateablefile;

import java.util.Set;

import org.gemsjax.shared.collaboration.Collaborateable;


/**
 * Is the answer (with the result set) on a {@link GetAllCollaborateablesMessage}
 * @author Hannes Dorfmann
 *
 */
public class GetAllCollaborateablesAnswerMessage extends ReferenceableCollaborateableFileMessage{
	
	public static final String TAG = "all";
	public static final String SUBTAG_RESULT="col";
	public static final String ATTRIBUTE_NAME = "name";
	public static final String ATTRIBUTE_ID ="id";
	public static final String ATTRIBUTE_TYPE ="type";
	
	private CollaborateableType type;
	private Set<Collaborateable> result;
	
	public GetAllCollaborateablesAnswerMessage(String referenceId, CollaborateableType type, Set<Collaborateable> result){
		super(referenceId);
		this.type = type;
		this.result = result;
	}
	
	
	public CollaborateableType getType(){
		return type;
	}


	@Override
	public String toXml() {
		String ret = super.openingXml()+"<"+TAG+" "+ATTRIBUTE_TYPE+"=\""+type.toConstant()+"\" >";
		
		for (Collaborateable c : result)
			ret+="<"+SUBTAG_RESULT+" "+ATTRIBUTE_ID+"=\""+c.getId()+"\" "+ATTRIBUTE_NAME+"=\""+c.getName()+"\" />";
		
		return ret+"</"+TAG+">"+super.closingXml();
	}
	
	public Set<Collaborateable> getResult(){
		return result;
	}

}
