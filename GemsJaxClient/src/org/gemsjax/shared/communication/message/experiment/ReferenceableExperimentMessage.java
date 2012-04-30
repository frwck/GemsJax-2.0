package org.gemsjax.shared.communication.message.experiment;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;
import org.gemsjax.shared.communication.message.collaborateablefile.ReferenceableCollaborateableFileMessage;


/**
 * This is the basic super class for any experiment message. All supclasses {@link #toXml()} returns a string wrapped around with this xml:<br /><br /> 
 * <code>
 * <{@link #TAG} {@link #ATTRIBUTE_REFERENCE_ID}="STRING-REFERENCE-ID">
 * 	...
 * </{@link #TAG}>
 * </code>
 * @author Hannes Dorfmann
 *
 */
public abstract class ReferenceableExperimentMessage extends ExperimentMessage{

	public static final String TAG = "exp";
	public static final String ATTRIBUTE_REFERENCE_ID ="ref-id";
	
	
	private String referenceId;
	
	public ReferenceableExperimentMessage(String referenceId){
		this.referenceId = referenceId;
	}
	
	
	@Override
	public abstract String toXml();
	
	protected String openingXml(){
		return "<"+TAG+" "+ATTRIBUTE_REFERENCE_ID+"=\""+referenceId+"\" >";
	}
	
	protected String closingXml(){
		return "</"+TAG+">";
	}
	

}
