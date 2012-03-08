package org.gemsjax.shared.communication.message.notification;

public class LiveCollaborationRequestNotificationMessage extends LiveNotificationMessage{
	
	public static final String TAG="quick-exp";
	
	public static final String ATTRIBUTE_ID="id";
	public static final String ATTRIBUTE_TIME="time";
	public static final String ATTRIBUTE_READ="read";
	
	public static final String ATTRIBUTE_COLLABORATION_USERNAME="username";
	public static final String ATTRIBUTE_COLLABORATION_DISPLAYNAME="dispName";
	public static final String ATTRIBUTE_COLLABORATION_ID="col-id";
	public static final String ATTRIBUTE_COLLABORATION_NAME="col-name";
	public static final String ATTRIBUTE_COLLABORATION_ACCEPTED="accepted";
	
	
	private CollaborationRequestNotification notification;
	
	public LiveCollaborationRequestNotificationMessage(CollaborationRequestNotification notification){
		this.notification = notification;
	}

	
	private String colToXml(){
		return "<"+TAG+" "+ATTRIBUTE_ID+"=\""+notification.getId()+"\" "+
				ATTRIBUTE_TIME+"=\""+notification.getDate().getTime()+"\" "+ATTRIBUTE_READ+"\""+notification.isRead()+"\" "+
				ATTRIBUTE_COLLABORATION_USERNAME+"=\""+notification.getUsername()+"\" "+
				ATTRIBUTE_COLLABORATION_DISPLAYNAME+"=\""+notification.getDisplayName()+"\" "+
				ATTRIBUTE_COLLABORATION_ACCEPTED+"=\""+notification.isAccepted()+"\" "+
				ATTRIBUTE_COLLABORATION_ID+"=\""+notification.getCollaborationId()+"\" "+
				ATTRIBUTE_COLLABORATION_NAME+"=\""+notification.getCollaborationName()+"\" />";
	}
	
	
	@Override
	public String toXml() {
		return openingXml()+colToXml()+closingXml();
	}
	
	
	public CollaborationRequestNotification getNotification(){
		return notification;
	}

}
