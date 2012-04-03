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
	public static final String SUBTAG_PUBLIC="public";
	public static final String SUBTAG_KEYWORDS="keywords";
	public static final String SUBTAG_COLLABORATORS="collaborators";
	public static final String SUBTAG_ADMINS="admins";
	
	public static final String SUBSUBTAG_ADD_COLLABORATOR="add";
	public static final String SUBSUBTAG_REMOVE_COLLABORATOR="remove";
	public static final String ATTRIBUTE_COLLABORATOR_ID="id";
	public static final String SUBSUBTAG_ADD_ADMIN="add";
	public static final String SUBSUBTAG_REMOVE_ADMIN="remove";
	public static final String ATTRIBUTE_ADMIN_ID="id";
	public static final String ATTRIBUTE_COLLABORATEABLE_ID="col-id";
	
	private String name;
	private Boolean _public;
	private String keywords;
	private Set<Integer> addAdminIds;
	private Set<Integer> removeAdminIds;
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
		
		if (_public!=null)
			r+="<"+SUBTAG_PUBLIC+">"+_public+"</"+SUBTAG_PUBLIC+">";
		
		if (keywords!=null)
			r+="<"+SUBTAG_KEYWORDS+">"+keywords+"</"+SUBTAG_KEYWORDS+">";
		
		if (addAdminIds!=null || removeAdminIds!=null)
		{
			r+="<"+SUBTAG_ADMINS+">";
			if (addAdminIds!=null)
				for (Integer id: addAdminIds)
					r+="<"+SUBSUBTAG_ADD_ADMIN+" "+ATTRIBUTE_ADMIN_ID+"=\""+id+"\" />";
			
			if (removeAdminIds!=null)
				for (Integer id: removeAdminIds)
					r+="<"+SUBSUBTAG_REMOVE_ADMIN+" "+ATTRIBUTE_ADMIN_ID+"=\""+id+"\" />";
			
			r+="</"+SUBTAG_ADMINS+">";
		}
		
		
		if (addCollaboratorIds!=null || removeAdminIds!=null)
		{
			r+="<"+SUBTAG_COLLABORATORS+">";
			if (addCollaboratorIds!=null)
				for (Integer id: addCollaboratorIds)
					r+="<"+SUBSUBTAG_ADD_COLLABORATOR+" "+ATTRIBUTE_COLLABORATOR_ID+"=\""+id+"\" />";
			
			if (removeCollaboratorIds!=null)
				for (Integer id: removeAdminIds)
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

	public boolean isPublic() {
		return _public;
	}

	public void setPublic(boolean _public) {
		this._public = _public;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Set<Integer> getAddAdminIds() {
		return addAdminIds;
	}

	public void setAddAdminIds(Set<Integer> addAdminIds) {
		this.addAdminIds = addAdminIds;
	}

	public Set<Integer> getRemoveAdminIds() {
		return removeAdminIds;
	}

	public void setRemoveAdminIds(Set<Integer> removeAdminIds) {
		this.removeAdminIds = removeAdminIds;
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
