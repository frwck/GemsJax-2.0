package org.gemsjax.shared.communication.message.collaboration;

import java.util.Set;

import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.CommunicationConstants;
import org.gemsjax.shared.communication.CommunicationConstants.Collaborateable;

/**
 * Create a new {@link Collaborateable}
 * @author Hannes Dorfmann
 *
 */
public class NewCollaborateableMessage extends CollaborateableAdministrationMessage {
	
	/**
	 * Used to determine, what kind of Collaborateable should be created
	 * @author Hannes Dorfmann
	 *
	 */
	public enum CollaborateableType{
		/**
		 * Mapped to protocol constant {@link CommunicationConstants.Collaborateable#TYPE_METAMODEL}
		 */
		METAMODEL,
		
		/**
		 * Mapped to protocol constant {@link CommunicationConstants.Collaborateable#TYPE_MODEL}
		 */
		MODEL
	}

	public static final String TAG = "new";
	public static final String ATTRIBUTE_NAME="name";
	public static final String ATTRIBUTE_PUBLIC="public";
	public static final String ATTRIBUTE_TYPE="type";
	
	
	public static final String SUBTAG_KEYWORDS="keywords";
	public static final String SUBTAG_COLLABORATORS="collaborators";
	public static final String SUBTAG_ADMINS="admins";
	
	public static final String ATTRIBUTE_ADD_COLABORATOR="add";
	public static final String ATTRIBUTE_ADD_COLLABORATOR_ID="id";
	public static final String ATTRIUBTE_ADD_ADMIN="add";
	public static final String ATTRIBUTE_ADD_ADMIN_ID="id";
	
	private boolean _public;
	private String name;
	private Set<String> adminIds;
	private Set<String> collaboratorIds;
	private CollaborateableType type;
	private String keywords;
	
	public NewCollaborateableMessage(String name, CollaborateableType type, Set<String> administratorIds, Set<String> collaboratorIds, boolean _public, String keywords)
	{
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

	public Set<String> getAdministratorIds() {
		return adminIds;
	}

	public Set<String> getCollaboratorIds() {
		return collaboratorIds;
	}
	
	
	private String collaborateableTypeToString()
	{
		switch(type)
		{
			case METAMODEL: return CommunicationConstants.Collaborateable.TYPE_METAMODEL;
			case MODEL: return CommunicationConstants.Collaborateable.TYPE_MODEL;
		}
		
		return null;
	}

	
	@Override
	public String toXml() {
		String x="<"+super.TAG+">";
		
		x+="<"+TAG+" "+ATTRIBUTE_NAME+"=\""+name +"\" " +ATTRIBUTE_PUBLIC+"=\""+Boolean.toString(_public)+"\" "+ATTRIBUTE_TYPE+"=\""+collaborateableTypeToString()+"\">";
		
		if (keywords!=null && !keywords.isEmpty())
			x+= "<"+SUBTAG_KEYWORDS+">"+keywords+"</"+SUBTAG_KEYWORDS+">";
		
		if (collaboratorIds!=null && !collaboratorIds.isEmpty()){
			x+="<"+SUBTAG_COLLABORATORS+">";
				for (String id : collaboratorIds)
					x+="<"+ATTRIBUTE_ADD_COLABORATOR+" "+ATTRIBUTE_ADD_COLLABORATOR_ID+"\""+id+"\" />";
			x+="</"+SUBTAG_COLLABORATORS+">";
		}
		
		
		if (adminIds!=null && !adminIds.isEmpty()){
			x+="<"+SUBTAG_ADMINS+">";
				for (String id : adminIds)
					x+="<"+ATTRIBUTE_ADD_ADMIN_ID+" "+ATTRIBUTE_ADD_ADMIN_ID+"\""+id+"\" />";
			x+="</"+SUBTAG_ADMINS+">";
		}
		
		
		x+="</"+TAG+"></"+super.TAG+">";
		return x;
	}

}
