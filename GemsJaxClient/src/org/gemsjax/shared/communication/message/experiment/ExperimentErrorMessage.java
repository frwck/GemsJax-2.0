package org.gemsjax.shared.communication.message.experiment;

import org.gemsjax.shared.communication.message.collaboration.TransactionError;
import org.gemsjax.shared.communication.serialisation.Archive;

/**
 * Sent from Server to Client as a negative response on a {@link CreateExperimentMessage}
 * 
 *	<exp ref-id="String" ><error reason="String" /></exp>
 * 
 * Note: ATTRIBUTE_REASON is a String that is representing the corresponding {@link ExperimentError} enum constant.
 * Use {@link ExperimentError#fromConstant(String)} to parse it.
 * @author Hannes Dorfmann
 *
 */
public class ExperimentErrorMessage extends ReferenceableExperimentMessage {

	public static final String TAG = "error";
	public static final String ATTRIBUTE_REASON="reason";
	
	private ExperimentError error;
	
	
	public ExperimentErrorMessage(){}
	
	public ExperimentErrorMessage(String referenceId, ExperimentError error) {
		super(referenceId);
		this.error = error;
	}
	
	public ExperimentError getExperimentError(){
		return error;
	}

	@Override
	public String toXml() {
		return null;
	}
	
	@Override
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
		
		String e = a.serialize("error", error!=null?error.toString():null).value;
		error = e==null?null:ExperimentError.valueOf(e);

	}
	
}
