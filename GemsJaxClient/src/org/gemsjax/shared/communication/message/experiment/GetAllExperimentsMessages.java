package org.gemsjax.shared.communication.message.experiment;



/**
 * Sent from Client to server
 * <exp ref-id="blabla">
 * 		<all />
 * </exp>
 * @author Hannes Dorfmann
 *
 */
public class GetAllExperimentsMessages extends ReferenceableExperimentMessage{

	public static final String TAG ="all";
	
	
	public GetAllExperimentsMessages(String referenceId) {
		super(referenceId);
		
		
	}

	@Override
	public String toXml() {
		return super.openingXml()+"<"+TAG+" />"+super.closingXml();
	}

}
