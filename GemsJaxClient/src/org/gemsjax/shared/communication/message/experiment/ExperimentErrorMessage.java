package org.gemsjax.shared.communication.message.experiment;

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
	
	public ExperimentErrorMessage(String referenceId, ExperimentError error) {
		super(referenceId);
		this.error = error;
	}

	@Override
	public String toXml() {
		return super.openingXml() + "<"+TAG+" "+ATTRIBUTE_REASON+"=\""+error.toConstant()+"\" />"+super.closingXml();
	}
	
}
