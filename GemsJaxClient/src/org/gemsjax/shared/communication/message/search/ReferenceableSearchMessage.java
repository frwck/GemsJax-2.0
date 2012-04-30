package org.gemsjax.shared.communication.message.search;

/**
 * The Basic super type for search messages
 * @author Hannes Dorfmann
 *
 */
public abstract class ReferenceableSearchMessage extends SearchMessage {

	public static final String TAG="serach";
	public static final String ATTRIBUTE_REFERENCE_ID="ref-id";
	
	private String referenceId;
	
	public ReferenceableSearchMessage(String referenceId)
	{
		this.referenceId = referenceId;
	}
	
	
	public String getReferenceId()
	{
		return referenceId;
	}
}
