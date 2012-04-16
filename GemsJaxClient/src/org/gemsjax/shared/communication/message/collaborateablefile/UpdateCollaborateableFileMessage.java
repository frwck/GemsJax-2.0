package org.gemsjax.shared.communication.message.collaborateablefile;

import java.util.Set;

import org.gemsjax.shared.collaboration.Collaborateable;

/**
 * Updates a {@link Collaborateable}
 * @author Hannes Dorfmann
 *
 */
public class UpdateCollaborateableFileMessage extends ReferenceableCollaborateableFileMessage {
	
	public static final String TAG ="update";
	
	public static final String SUBTAG_NAME="name";
	public static final String SUBTAG_PERMISSION="permission";
	public static final String SUBTAG_KEYWORDS="keywords";
	public static final String SUBTAG_COLLABORATORS="collaborators";
	public static final String SUBTAG_ADMINS="admins";
	
	public static final String SUBSUBTAG_ADD_COLLABORATOR="add";
	public static final String SUBSUBTAG_REMOVE_COLLABORATOR="remove";
	public static final String ATTRIBUTE_COLLABORATOR_ID="id";
	public static final String ATTRIBUTE_COLLABORATEABLE_ID="col-id";
	
	private String name;
	private Collaborateable.Permission permission;
	private String keywords;
	private Set<Integer> addCollaboratorIds;
	private Set<Integer> removeCollaboratorIds;
	private int collaborateableId;

	public UpdateCollaborateableFileMessage(String referenceId, int colaborateableId){
		super(referenceId);
		this.collaborateableId = colaborateableId;
	}

	@Override
	public String toXml() {
		String r = super.openingXml()+"<"+TAG+" "+ATTRIBUTE_COLLABORATEABLE_ID+"=\""+collaborateableId+"\">";
		
		if (name!=null)
			r+="<"+SUBTAG_NAME+">"+name+"</"+SUBTAG_NAME+">";
		
		if (permission!=null)
			r+="<"+SUBTAG_PERMISSION+">"+permission.toConstant()+"</"+SUBTAG_PERMISSION+">";
		
		if (keywords!=null)
			r+="<"+SUBTAG_KEYWORDS+">"+keywords+"</"+SUBTAG_KEYWORDS+">";
		
		
		if (addCollaboratorIds!=null || removeCollaboratorIds!=null)
		{
			r+="<"+SUBTAG_COLLABORATORS+">";
			if (addCollaboratorIds!=null)
				for (Integer id: addCollaboratorIds)
					r+="<"+SUBSUBTAG_ADD_COLLABORATOR+" "+ATTRIBUTE_COLLABORATOR_ID+"=\""+id+"\" />";
			
			if (removeCollaboratorIds!=null)
				for (Integer id: removeCollaboratorIds)
					r+="<"+SUBSUBTAG_REMOVE_COLLABORATOR+" "+ATTRIBUTE_COLLABORATOR_ID+"=\""+id+"\" />";
			
			r+="</"+SUBTAG_COLLABORATORS+">";
		}
		
		r+=super.closingXml();
		
		return r;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collaborateable.Permission getPermission() {
		return permission;
	}

	public void setPermission(Collaborateable.Permission permission) {
		this.permission = permission;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Set<Integer> getAddCollaboratorIds() {
		return addCollaboratorIds;
	}

	public void setAddCollaboratorIds(Set<Integer> addCollaboratorIds) {
		this.addCollaboratorIds = addCollaboratorIds;
	}

	public Set<Integer> getRemoveCollaboratorIds() {
		return removeCollaboratorIds;
	}

	public void setRemoveCollaboratorIds(Set<Integer> removeCollaboratorIds) {
		this.removeCollaboratorIds = removeCollaboratorIds;
	}
	
	public int getCollaborateableId(){
		return collaborateableId;
	}
	
	
	
}
