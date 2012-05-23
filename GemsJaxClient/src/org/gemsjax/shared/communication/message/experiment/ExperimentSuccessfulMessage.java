package org.gemsjax.shared.communication.message.experiment;

import org.gemsjax.shared.communication.serialisation.Archive;

/**
 * Sent from server to client as a positive response on {@link CreateExperimentMessage}
 * 
 * <exp ref-id="blabla">
 * 		<ok />
 * </exp>
 * @author Hannes Dorfmann
 *
 */
public class ExperimentSuccessfulMessage extends ReferenceableExperimentMessage{

	
	public static final String TAG ="ok";
	
	public ExperimentSuccessfulMessage(String referenceId) {
		super(referenceId);
		
	}
	
	public ExperimentSuccessfulMessage(){}
	
	
	@Override
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
	}
	

	@Override
	public String toXml() {
		return null;
//		super.openingXml()+"<"+TAG+" />"+super.closingXml();
	}
	
	
}
