package org.gemsjax.shared.communication.message.search;

/**
 * Is sent from server to the client, as a rsponse on any search message
 * @author Hannes Dorfmann
 *
 */
public class SearchResultErrorMessage extends ReferenceableSearchMessage{

	public static final String TAG="error";
	public static final String ATTRIBUTE_TYPE="type";
	
	private SearchError searchError;
	private String additionalInfo;
	
	public SearchResultErrorMessage(String referenceId, SearchError error) {
		super(referenceId);
		this.searchError = error;
		
	}
	
	public SearchResultErrorMessage(String referenceId, SearchError error, String additionalInfo) {
		super(referenceId);
		this.searchError = error;
		this.additionalInfo = additionalInfo;
		
	}
	
	
	public SearchError getSearchError()
	{
		return searchError;
	}

	
	public String getAdditionalInfo()
	{
		return additionalInfo;
	}

	
	private String errorXml()
	{
		return "<"+TAG+" "+ATTRIBUTE_TYPE+"=\""+searchError.toConstant()+"\">"+(additionalInfo!=null ? additionalInfo:"")+"</"+TAG+">";
	}
	
	
	@Override
	public String toXml() {
		return "<"+ReferenceableSearchMessage.TAG+" "+ReferenceableSearchMessage.ATTRIBUTE_REFERENCE_ID+"=\""+getReferenceId()+"\">"+errorXml()+"</"+ReferenceableSearchMessage.TAG+">";
	}

}
