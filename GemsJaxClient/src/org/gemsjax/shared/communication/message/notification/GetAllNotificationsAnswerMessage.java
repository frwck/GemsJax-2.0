package org.gemsjax.shared.communication.message.notification;

import java.util.Set;

/**
 * This Message is sent from server to client as the response/answer on a {@link GetAllNotificationsMessage}
 * @author Hannes Dorfmann
 *
 */
public class GetAllNotificationsAnswerMessage extends ReferenceableNotificationMessage{

	public static final String TAG ="all";
	public static final String SUBTAG_QUICKNOTIFICATION_RESULTS ="quicks";
	public static final String SUBTAG_FRIENDSHIP_REQUEST_RESULTS ="fr-requests";
	public static final String SUBTAG_COLLABORATION_REQUEST_RESULTS ="col-requests";
	public static final String SUBTAG_EXPERIMENT_REQUEST_RESULTS ="exp-requests";
	
	public static final String SUBTAG_QUICKNOTIFICATION="quick";
	public static final String SUBTAG_FRIENDSHIPNOTIFICATION="fr";
	public static final String SUBTAG_EXPERIMENTNOTIFICATION="exp";
	public static final String SUBTAG_COLLABORATIONNOTIFICATION="col";
	
	public static final String ATTRIBUTE_ID="id";
	public static final String ATTRIBUTE_TIME="time";
	public static final String ATTRIBUTE_READ="read";
	
	public static final String ATTRIBUTE_QUICKNOTIFICATION_TYPE="type";
	public static final String ATTRIBUTE_QUICKNOTIFICATION_OPTIONAL="opt";
	
	public static final String ATTRIBUTE_FRIENDSHIP_USERNAME="username";
	public static final String ATTRIBUTE_FRIENDSHIP_DISPLAYNAME="dispName";
	public static final String ATTRIBUTE_FRIENDSHIP_ACCEPTED="accepted";
	
	public static final String ATTRIBUTE_EXPERIMENT_USERNAME="username";
	public static final String ATTRIBUTE_EXPERIMENT_DISPLAYNAME="dispName";
	public static final String ATTRIBUTE_EXPERIMENT_ID="exp-id";
	public static final String ATTRIBUTE_EXPERIMENT_NAME="exp-name";
	public static final String ATTRIBUTE_EXPERIMENT_ACCEPTED="accepted";
	
	
	
	public static final String ATTRIBUTE_COLLABORATION_USERNAME="username";
	public static final String ATTRIBUTE_COLLABORATION_DISPLAYNAME="dispName";
	public static final String ATTRIBUTE_COLLABORATION_ID="col-id";
	public static final String ATTRIBUTE_COLLABORATION_NAME="col-name";
	public static final String ATTRIBUTE_COLLABORATION_ACCEPTED="accepted";
	
	
	
	private Set<QuickNotification> quicknotificatins;
	private Set<ExperimentRequestNotification> experimentNotifications;
	private Set<FriendshipRequestNotification> friendshipNotifications;
	private Set<CollaborationRequestNotification> collaborationNotifications;
	
	
	public GetAllNotificationsAnswerMessage(String referenceId, Set<QuickNotification> quicknotificatins, Set<ExperimentRequestNotification> experimentNotifications,
			Set<FriendshipRequestNotification> friendshipNotifications,Set<CollaborationRequestNotification> collaborationNotifications) {
		
		super(referenceId);
		this.quicknotificatins = quicknotificatins;
		this.experimentNotifications = experimentNotifications;
		this.collaborationNotifications = collaborationNotifications;
		this.friendshipNotifications = friendshipNotifications;
		
		
	}


	public Set<QuickNotification> getQuicknotificatins() {
		return quicknotificatins;
	}


	public Set<ExperimentRequestNotification> getExperimentNotifications() {
		return experimentNotifications;
	}


	public Set<FriendshipRequestNotification> getFriendshipNotifications() {
		return friendshipNotifications;
	}


	public Set<CollaborationRequestNotification> getCollaborationNotifications() {
		return collaborationNotifications;
	}

	private String quickToXml(){
		
		String ret="<"+SUBTAG_QUICKNOTIFICATION_RESULTS+">";
		
		for (QuickNotification n: quicknotificatins)
			ret+="<"+SUBTAG_QUICKNOTIFICATION+" "+ATTRIBUTE_ID+"=\""+n.getId()+"\" "+
				ATTRIBUTE_TIME+"=\""+n.getDate().getTime()+"\" "+ATTRIBUTE_READ+"=\""+n.isRead()+"\" "+
				ATTRIBUTE_QUICKNOTIFICATION_TYPE+"=\""+n.getType().toConstant()+"\" "+
				ATTRIBUTE_QUICKNOTIFICATION_OPTIONAL+"=\""+n.getOptionalMessage()+"\" />";
		
		ret+="</"+SUBTAG_QUICKNOTIFICATION_RESULTS+">";
		return ret;
	}
	

	private String friendshipToXml(){
		String ret="<"+SUBTAG_FRIENDSHIP_REQUEST_RESULTS+">";
		
		for (FriendshipRequestNotification n: friendshipNotifications)
			ret+="<"+SUBTAG_FRIENDSHIPNOTIFICATION+" "+ATTRIBUTE_ID+"=\""+n.getId()+"\" "+
				ATTRIBUTE_TIME+"=\""+n.getDate().getTime()+"\" "+ATTRIBUTE_READ+"=\""+n.isRead()+"\" "+
				ATTRIBUTE_FRIENDSHIP_USERNAME+"=\""+n.getUsername()+"\" "+
				ATTRIBUTE_FRIENDSHIP_DISPLAYNAME+"=\""+n.getDisplayName()+"\" "+
				ATTRIBUTE_FRIENDSHIP_ACCEPTED+"=\""+n.isAccepted()+"\" />";
				
		
		ret+="</"+SUBTAG_FRIENDSHIP_REQUEST_RESULTS+">";
		return ret;
	}
	
	private String experimentToXml(){
		
		String ret="<"+SUBTAG_EXPERIMENT_REQUEST_RESULTS+">";
		
		for (ExperimentRequestNotification n: experimentNotifications)
			ret+="<"+SUBTAG_EXPERIMENTNOTIFICATION+" "+ATTRIBUTE_ID+"=\""+n.getId()+"\" "+
				ATTRIBUTE_TIME+"=\""+n.getDate().getTime()+"\" "+ATTRIBUTE_READ+"=\""+n.isRead()+"\" "+
				ATTRIBUTE_EXPERIMENT_USERNAME+"=\""+n.getUsername()+"\" "+
				ATTRIBUTE_EXPERIMENT_DISPLAYNAME+"=\""+n.getDisplayName()+"\" "+
				ATTRIBUTE_EXPERIMENT_ACCEPTED+"=\""+n.isAccepted()+"\" "+
				ATTRIBUTE_EXPERIMENT_ID+"=\""+n.getExperimentId()+"\" "+
				ATTRIBUTE_EXPERIMENT_NAME+"=\""+n.getExperimentName()+"\" />";
				
		
		ret+="</"+SUBTAG_EXPERIMENT_REQUEST_RESULTS+">";
		return ret;
		
	}
	
	private String collaborationToXml(){
		String ret="<"+SUBTAG_COLLABORATION_REQUEST_RESULTS+">";
		
		for (CollaborationRequestNotification n: collaborationNotifications)
			ret+="<"+SUBTAG_COLLABORATIONNOTIFICATION+" "+ATTRIBUTE_ID+"=\""+n.getId()+"\" "+
				ATTRIBUTE_TIME+"=\""+n.getDate().getTime()+"\" "+ATTRIBUTE_READ+"=\""+n.isRead()+"\" "+
				ATTRIBUTE_COLLABORATION_USERNAME+"=\""+n.getUsername()+"\" "+
				ATTRIBUTE_COLLABORATION_DISPLAYNAME+"=\""+n.getDisplayName()+"\" "+
				ATTRIBUTE_COLLABORATION_ACCEPTED+"=\""+n.isAccepted()+"\" "+
				ATTRIBUTE_COLLABORATION_ID+"=\""+n.getCollaborationId()+"\" "+
				ATTRIBUTE_COLLABORATION_NAME+"=\""+n.getCollaborationName()+"\" />";
				
		
		ret+="</"+SUBTAG_COLLABORATION_REQUEST_RESULTS+">";
		return ret;
	}

	@Override
	public String toXml() {
		return openingXml()+"<"+TAG+">"+quickToXml()+friendshipToXml()+experimentToXml()+collaborationToXml()+"</"+TAG+">"+closingXml();
	}

}
