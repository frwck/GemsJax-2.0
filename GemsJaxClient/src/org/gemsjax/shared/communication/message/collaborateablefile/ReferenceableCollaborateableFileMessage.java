package org.gemsjax.shared.communication.message.collaborateablefile;

import org.gemsjax.shared.communication.message.Message;

/**
 * The abstract super class for every collaborateable administration message, like creating , deleting and editing collaborateables.
 * <b>Note</b> The content of the {@link CollaborateableType} itself is not manipulated with this kind of messages
 * @author Hannes Dorfmann
 *
 */
public abstract class ReferenceableCollaborateableFileMessage implements Message{
	
	public static final String TAG ="col-file";
	public static final String ATTRIBUTE_REFERENCE_ID ="ref-id";
	
	private String referenceId;
	
	
	public ReferenceableCollaborateableFileMessage(String referenceId){
		this.referenceId = referenceId;
	}
	
	
	protected String openingXml(){
		return "<"+TAG+" "+ATTRIBUTE_REFERENCE_ID+"=\""+referenceId+"\" >";
	}
	
	protected String closingXml(){
		return "</"+TAG+">";
	}
	
	public String getReferenceId(){
		return referenceId;
	}
}
