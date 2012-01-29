package org.gemsjax.shared.communication.message.search;

import org.gemsjax.shared.communication.message.Message;
/**
 * The Basic super type for search messages
 * @author Hannes Dorfmann
 *
 */
public abstract class ReferenceableSearchMessage implements Message {

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
