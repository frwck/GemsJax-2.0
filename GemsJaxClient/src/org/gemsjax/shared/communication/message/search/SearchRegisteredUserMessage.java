package org.gemsjax.shared.communication.message.search;

/**
 * Sent from the client to server, to do a search for registered User,
 * that matches the 
 * @author Hannes Dorfmann
 *
 */
public class SearchRegisteredUserMessage extends ReferenceableSearchMessage {

	public final static String TAG="user";
	private String searchString;
	
	public SearchRegisteredUserMessage(String referenceId, String searchString)
	{
		super(referenceId);
		this.searchString = searchString;
	
	}
	
	
	
	@Override
	public String toXml() {
		return "<"+ReferenceableSearchMessage.TAG+" "+ATTRIBUTE_REFERENCE_ID+"=\""+getReferenceId()+"\"><"+TAG+">"+searchString+"</"+TAG+"></"+ReferenceableSearchMessage.TAG+">";
	}
	
	
	public String getSearchString()
	{
		return searchString;
	}

}
