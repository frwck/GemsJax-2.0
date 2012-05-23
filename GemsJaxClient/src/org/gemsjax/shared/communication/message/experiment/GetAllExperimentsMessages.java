package org.gemsjax.shared.communication.message.experiment;

import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.communication.serialisation.Serializable;



/**
 * Sent from Client to server
 * <exp ref-id="blabla">
 * 		<all />
 * </exp>
 * @author Hannes Dorfmann
 *
 */
public class GetAllExperimentsMessages extends ReferenceableExperimentMessage implements Serializable{

	public static final String TAG ="all";
	
	
	public GetAllExperimentsMessages(String referenceId) {
		super(referenceId);
		
		
	}

	@Override
	public String toXml() {
//		return super.openingXml()+"<"+TAG+" />"+super.closingXml();
		return null;
	}

	@Override
	public void serialize(Archive a) throws Exception {
		super.serialize(a);
	}

}
