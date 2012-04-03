package org.gemsjax.shared.communication.message.collaborateablefile;

import java.util.Set;

import org.gemsjax.shared.communication.CommunicationConstants;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableType;

/**
 * Create a new {@link CollaborateableType}
 * @author Hannes Dorfmann
 *
 */
public class NewCollaborateableFileMessage extends ReferenceableCollaborateableFileMessage {
	
	
	public static final String TAG = "new";
	public static final String ATTRIBUTE_NAME="name";
	public static final String ATTRIBUTE_PUBLIC="public";
	public static final String ATTRIBUTE_TYPE="type";
	
	
	public static final String SUBTAG_KEYWORDS="keywords";
	public static final String SUBTAG_COLLABORATORS="collaborators";
	public static final String SUBTAG_ADMINS="admins";
	
	public static final String SUBSUBTAG_ADD_COLLABORATOR="add";
	public static final String ATTRIBUTE_COLLABORATOR_ID="id";
	public static final String SUBSUBTAG_ADD_ADMIN="add";
	public static final String ATTRIBUTE_ADMIN_ID="id";
	
	private boolean _public;
	private String name;
	private Set<Integer> adminIds;
	private Set<Integer> collaboratorIds;
	private CollaborateableType type;
	private String keywords;
	
	public NewCollaborateableFileMessage(String referenceId, String name, CollaborateableType type, Set<Integer> administratorIds, Set<Integer> collaboratorIds, boolean _public, String keywords)
	{
		super(referenceId);
		this._public = _public;
		this.name = name;
		this.adminIds = administratorIds;
		this.collaboratorIds = collaboratorIds;
		this.keywords = keywords;
	}
	
	
	public CollaborateableType getCollaborateableType()
	{
		return type;
	}
	

	public boolean isPublic() {	
		return _public;
	}

	public String getName() {
		return name;
	}

	public Set<Integer> getAdministratorIds() {
		return adminIds;
	}

	public Set<Integer> getCollaboratorIds() {
		return collaboratorIds;
	}
	
	public String getKeywords(){
		return keywords;
	}
	
	public CollaborateableType getType(){
		return type;
	}
	
	
	@Override
	public String toXml() {
		String x=super.openingXml();
		
		x+="<"+TAG+" "+ATTRIBUTE_NAME+"=\""+name +"\" " +ATTRIBUTE_PUBLIC+"=\""+Boolean.toString(_public)+"\" "+ATTRIBUTE_TYPE+"=\""+type.toConstant()+"\">";
		
		
		x+= "<"+SUBTAG_KEYWORDS+">";
		if (keywords!=null && !keywords.isEmpty())x+=keywords;
		x+="</"+SUBTAG_KEYWORDS+">";
		
		x+="<"+SUBTAG_COLLABORATORS+">";
		if (collaboratorIds!=null && !collaboratorIds.isEmpty()){
				for (Integer id : collaboratorIds)
					x+="<"+SUBSUBTAG_ADD_COLLABORATOR+" "+ATTRIBUTE_COLLABORATOR_ID+"=\""+id+"\" />";
		}
		
		x+="</"+SUBTAG_COLLABORATORS+">";
		
		x+="<"+SUBTAG_ADMINS+">";
		if (adminIds!=null && !adminIds.isEmpty()){
				for (Integer id : adminIds)
					x+="<"+ATTRIBUTE_ADMIN_ID+" "+ATTRIBUTE_ADMIN_ID+"=\""+id+"\" />";
		}
		x+="</"+SUBTAG_ADMINS+">";
		
		x+="</"+TAG+">"+super.closingXml();
		return x;
	}

}
