package org.gemsjax.shared.communication.message.request;

import java.util.Set;

/**
 * Sent from server to client as a answer/response on a {@link GetAllRequestsMessage}
 * @author Hannes Dorfmann
 *
 */
public class GetAllRequestsAnswerMessage extends ReferenceableRequestMessage{

	public static final String TAG="all";
	public static final String SUBTAG_FRIENDSHIP_REQUESTS="friendships";
	public static final String SUBTAG_EXPERIMENT_REQUESTS="experiments";
	public static final String SUBTAG_COLLABORATION_REQUESTS="collaboations";
	
	public static final String SUBTAG_EXPERIMENT="exp";
	public static final String ATTRIBUTE_EXPERIMENT_REQUEST_ID="id";
	public static final String ATTRIBUTE_EXPERIMENT_REQUEST_DATETIME="time";
	public static final String ATTRIBUTE_EXPERIMENT_ID="exp-id";
	public static final String ATTRIBUTE_EXPERIMENT_NAME="name";
	public static final String ATTRIBUTE_EXPERIMENT_REQUESTER_DISPLAYNAME="requester";
	public static final String ATTRIBUTE_EXPERIMENT_REQUESTER_USERNAME="req-username";
	
	

	public static final String SUBTAG_FRIENDSHIP="friend";
	public static final String ATTRIBUTE_FRIENDSHIP_REQUEST_ID="id";
	public static final String ATTRIBUTE_FRIENDSHIP_REQUEST_DATETIME="time";
	public static final String ATTRIBUTE_FRIENDSHIP_DISPLAYNAME="dispName";
	public static final String ATTRIBUTE_FRIENDSHIP_REQUESTER_USERNAME="req-username";
	public static final String ATTRIBUTE_FRIENDSHIP_REQUESTER_DISPLAYNAME="requester";
	
	public static final String SUBTAG_COLLABORATION="col";
	public static final String ATTRIBUTE_COLLABORATION_REQUEST_ID="id";
	public static final String ATTRIBUTE_COLLABROTAION_REQUEST_DATETIME="time";
	public static final String ATTRIBUTE_COLLABORATION_NAME="name";
	public static final String ATTRIBUTE_COLLABORATION_ID="col-id";
	public static final String ATTRIBUTE_COLLABORATION_REQUESTER_DISPLAYNAME="requester";
	public static final String ATTRIBUTE_COLLABORATION_REQUESTER_USERNAME="req-username";
	
	
	
	private Set<FriendshipRequest> friendshipRequests;
	private Set<AdminExperimentRequest> experimentRequests;
	private Set<CollaborationRequest> collaborationRequests;
	
	public GetAllRequestsAnswerMessage(String referenceId, Set<FriendshipRequest> friendshipRequests, Set<AdminExperimentRequest> experimentRequests, Set<CollaborationRequest> collaborationRequests) {
		super(referenceId);
		this.friendshipRequests = friendshipRequests;
		this.experimentRequests = experimentRequests;
		this.collaborationRequests = collaborationRequests;
	}
	
	
	private String friendsToXml()
	{
		String ret="<"+SUBTAG_FRIENDSHIP_REQUESTS+">";
		
		for (FriendshipRequest r: friendshipRequests)
			ret+="<"+SUBTAG_FRIENDSHIP+" "+ATTRIBUTE_FRIENDSHIP_REQUEST_ID+"=\""+r.getId()+"\" "+ATTRIBUTE_FRIENDSHIP_REQUEST_DATETIME+"=\""+r.getDate().getTime()+"\" "+ATTRIBUTE_FRIENDSHIP_REQUESTER_DISPLAYNAME+"=\""+r.getRequesterDisplayName()+"\" "+ATTRIBUTE_FRIENDSHIP_REQUESTER_USERNAME+"=\""+r.getRequesterUsername()+"\" />";
		
		ret+="</"+SUBTAG_FRIENDSHIP_REQUESTS+">";
		return ret;
	}
	
	
	private String experimentsToXml()
	{
		String ret="<"+SUBTAG_EXPERIMENT_REQUESTS+">";
		
		for (AdminExperimentRequest r: experimentRequests)
			ret+="<"+SUBTAG_EXPERIMENT+" "+ATTRIBUTE_EXPERIMENT_REQUEST_ID+"=\""+r.getId()+"\" "+ATTRIBUTE_EXPERIMENT_REQUEST_DATETIME+"=\""+r.getDate().getTime()+"\" "+ATTRIBUTE_EXPERIMENT_REQUESTER_DISPLAYNAME+"=\""+r.getRequesterDisplayName()+"\" "+ATTRIBUTE_EXPERIMENT_REQUESTER_USERNAME+"=\""+r.getRequesterUsername()+"\" "+ATTRIBUTE_EXPERIMENT_NAME+"=\""+r.getExperimentName()+"\" "+ATTRIBUTE_EXPERIMENT_ID+"=\""+r.getExperimentId()+"\" />";
		
		ret+="</"+SUBTAG_EXPERIMENT_REQUESTS+">";
		return ret;
	}

	
	private String collaborationsToXml()
	{
		String ret="<"+SUBTAG_COLLABORATION_REQUESTS+">";
		
		for (CollaborationRequest r: collaborationRequests)
			ret+="<"+SUBTAG_COLLABORATION+" "+ATTRIBUTE_COLLABORATION_REQUEST_ID+"=\""+r.getId()+"\" "+ATTRIBUTE_COLLABROTAION_REQUEST_DATETIME+"=\""+r.getDate().getTime()+"\" "+ATTRIBUTE_COLLABORATION_REQUESTER_DISPLAYNAME+"=\""+r.getRequesterDisplayName()+"\" "+ATTRIBUTE_COLLABORATION_REQUESTER_USERNAME+"=\""+r.getRequesterUsername()+"\" "+ATTRIBUTE_COLLABORATION_NAME+"=\""+r.getName()+"\" "+ATTRIBUTE_COLLABORATION_ID+"=\""+r.getCollaborationId()+"\" />";
		
		ret+="</"+SUBTAG_COLLABORATION_REQUESTS+">";
		return ret;
	}
	
	@Override
	public String toXml() {
		return super.openingXml()+friendsToXml()+experimentsToXml()+collaborationsToXml()+super.closingXml();
	}

}
