package org.gemsjax.shared.communication.message.search;

import java.util.Set;

public class SearchRegisteredUserResultMessage extends ReferenceableSearchMessage{

	public static final String TAG="user-result";
	public static final String SUBTAG_USER ="user";
	public static final String ATTRIBUTE_USER_ID ="id";
	public static final String ATTRIBUTE_DISPALY_NAME="dispName";
	public static final String ATTRIBUTE_PROFILE_PICTURE="img";
	public static final String ATTRIUBTE_USERNAME="uname";
	
	private Set<UserResult> result;
	
	public SearchRegisteredUserResultMessage(String referenceId, Set<UserResult> result)
	{
		super(referenceId);
		this.result = result;
	
	}
	
	
	private String resultToXml()
	{
		String r="";
		
		for (UserResult u : result)
		{
			r+="<"+SUBTAG_USER+" "+ATTRIBUTE_USER_ID+"=\""+u.getUserId()+"\" "+ATTRIBUTE_DISPALY_NAME+"=\""+u.getDisplayName()+"\" "+ATTRIBUTE_PROFILE_PICTURE+"=\""+u.getProfilePicture()+"\" "+ATTRIUBTE_USERNAME+"=\""+u.getUsername()+"\" />";
		}
		
		return r;
	}
	
	
	
	@Override
	public String toXml() {
		return "<"+ReferenceableSearchMessage.TAG+" "+ATTRIBUTE_REFERENCE_ID+"=\""+getReferenceId()+"\"><"+TAG+">"+resultToXml()+"</"+TAG+"></"+ReferenceableSearchMessage.TAG+">";
	}

	
	public Set<UserResult> getResultSet()
	{
		return result;
	}

}
