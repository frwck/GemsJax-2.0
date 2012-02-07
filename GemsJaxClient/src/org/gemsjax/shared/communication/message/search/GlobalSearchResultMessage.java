package org.gemsjax.shared.communication.message.search;

import java.util.Set;

/**
 * Sent from the server to the client as the answer of a {@link GlobalSearchMessage}.
 * This message contains the search results
 * @author Hannes Dorfmann
 *
 */
public class GlobalSearchResultMessage extends ReferenceableSearchMessage {
	
	public static final String TAG="global-result";
	public static final String SUBTAG_USER_RESULT ="users";
	public static final String SUBTAG_COLLABORATEABLE_RESULT="collaboratables";
	public static final String SUBTAG_EXPERIMENT_RESULT="experiments";
	
	public static final String SUBTAG_USER ="user";
	public static final String SUBTAG_COLLABORATEABLE="col";
	public static final String SUBTAG_EXPERIMENT="exp";
	
	public static final String ATTRIBUTE_USER_ID="id";
	public static final String ATTRIBUTE_USER_DISPLAY_NAME="dispName";
	public static final String ATTRIBUTE_USER_USERNAME="uname";
	public static final String ATTRIBUTE_USER_PROFILE_PICTURE="img";
	
	public static final String ATTRIBUTE_EXPERIMENT_ID="id";
	public static final String ATTRIBUTE_EXPERIMENT_NAME="name";
	public static final String ATTRIBUTE_EXPERIMENT_OWNER_NAME="owner";
	public static final String ATTRIBUTE_EXPERIMENT_CO_ADMIN="admin";
	
	
	public static final String ATTRIBUTE_COLLABORATEABLE_ID="id";
	public static final String ATTRIBUTE_COLLABORATEABLE_NAME="name";
	public static final String ATTRIBUTE_COLLABORATEABLE_OWNER_NAME="owner";
	public static final String ATTRIBUTE_COLLABORATEABLE_TYPE="type";
	public static final String ATTRIBUTE_COLLABORATEABLE_PUBLIC="public";
	
	
	
	private Set<UserResult> userResults;
	private Set<CollaborationResult> collaborationResults;
	private Set<ExperimentResult> experimentResults;
	
	
	public GlobalSearchResultMessage(String referenceId, Set<UserResult> userResults, Set<CollaborationResult> collaborationResults, Set<ExperimentResult> experimentResults) {
		super(referenceId);
		this.experimentResults = experimentResults;
		this.userResults = userResults;
		this.collaborationResults = collaborationResults;
	}

	
	private String userToXml()
	{
		String r ="<"+SUBTAG_USER_RESULT+">";
		
		for (UserResult u : userResults)
		{
			r+="<"+SUBTAG_USER+" "+ATTRIBUTE_USER_ID+"=\""+u.getUserId()+"\" "+ATTRIBUTE_USER_DISPLAY_NAME+"=\""+u.getDisplayName()+"\"";
			r+=" "+ATTRIBUTE_USER_PROFILE_PICTURE+"=\""+u.getProfilePicture()+"\" "+ATTRIBUTE_USER_USERNAME+"=\""+u.getUsername()+"\" />";
		}
		
		r+="</"+SUBTAG_USER_RESULT+">";
		
		return r;
	}
	
	
	private String collaborateableToXml()
	{
		String r ="<"+SUBTAG_COLLABORATEABLE_RESULT+">";
		
		for (CollaborationResult u : collaborationResults)
			r+="<"+SUBTAG_COLLABORATEABLE+" "+ATTRIBUTE_COLLABORATEABLE_ID+"=\""+u.getId()+"\" "+ATTRIBUTE_COLLABORATEABLE_NAME+"=\""+u.getName()+"\" "+ATTRIBUTE_COLLABORATEABLE_OWNER_NAME+"=\""+u.getOwnerName()+"\" "+ATTRIBUTE_COLLABORATEABLE_TYPE+"=\""+u.getType().toConstant() +"\" "+ATTRIBUTE_COLLABORATEABLE_PUBLIC+"=\""+u.isPublic()+"\" />";
		
		r+="</"+SUBTAG_COLLABORATEABLE_RESULT+">";
		
		return r;
	}
	
	
	
	private String experimentToXml()
	{
		String r ="<"+SUBTAG_EXPERIMENT_RESULT+">";
		
		for (ExperimentResult u : experimentResults)
			r+="<"+SUBTAG_EXPERIMENT+" "+ATTRIBUTE_EXPERIMENT_ID+"=\""+u.getId()+"\" "+ATTRIBUTE_EXPERIMENT_NAME+"=\""+u.getName()+"\" "+ATTRIBUTE_EXPERIMENT_OWNER_NAME+"=\""+u.getOwnerName()+"\" "+ATTRIBUTE_EXPERIMENT_CO_ADMIN+"=\""+u.isCoAdmin()+"\" />";
		
		r+="</"+SUBTAG_EXPERIMENT_RESULT+">";
		
		return r;
	}
	

	@Override
	public String toXml() {
		return "<"+ReferenceableSearchMessage.TAG+"><"+TAG+">"+userToXml()+experimentToXml()+collaborateableToXml()+"</"+TAG+"></"+ReferenceableSearchMessage.TAG+">";
	}


	public Set<UserResult> getUserResults() {
		return userResults;
	}


	public Set<CollaborationResult> getCollaborationResults() {
		return collaborationResults;
	}


	public Set<ExperimentResult> getExperimentResults() {
		return experimentResults;
	}

}
