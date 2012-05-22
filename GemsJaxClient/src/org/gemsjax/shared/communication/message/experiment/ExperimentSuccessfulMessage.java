package org.gemsjax.shared.communication.message.experiment;

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
	

	@Override
	public String toXml() {
		return null;
//		super.openingXml()+"<"+TAG+" />"+super.closingXml();
	}
	
	
}
