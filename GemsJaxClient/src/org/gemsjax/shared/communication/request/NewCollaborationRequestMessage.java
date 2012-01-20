package org.gemsjax.shared.communication.request;

import java.util.Set;

public class NewCollaborationRequestMessage extends RequestMessage{

	public static final String TAG = "collaboration";
	public static final String SUBTAG_USER ="user";
	public static final String ATTRIBUTE_USER_ID="id";
	
	private Set<Integer> ids;
	
	public NewCollaborationRequestMessage(Set<Integer> ids)
	{
		this.ids = ids;
	}
	
	
	
	private String userToXml()
	{
		String users = "";
		for (Integer id: ids)
		{
			users+="<"+SUBTAG_USER+" "+ATTRIBUTE_USER_ID+"=\""+id+"\" />";
		}
		return users;
	}
	
	@Override
	public String toXml() {
		return "<"+RequestMessage.TAG+"><"+TAG+">"+userToXml()+"</"+TAG+"></"+RequestMessage.TAG+">";
	}
}
