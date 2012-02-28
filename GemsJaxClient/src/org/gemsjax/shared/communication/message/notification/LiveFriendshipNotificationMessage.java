package org.gemsjax.shared.communication.message.notification;

/**
 * Pushed from server to the client
 * @author Hannes Dorfmann
 *
 */
public class LiveFriendshipNotificationMessage extends LiveNotificationMessage{

	public static final String TAG="live-quick";
	
	public static final String ATTRIBUTE_ID="id";
	public static final String ATTRIBUTE_TIME="time";
	public static final String ATTRIBUTE_READ="read";
	public static final String ATTRIBUTE_USERNAME="username";
	public static final String ATTRIBUTE_DISPLAYNAME="dispName";
	public static final String ATTRIBUTE_ACCEPTED="accepted";
	
			
	private FriendshipRequestNotification notification;
	
	
	public LiveFriendshipNotificationMessage(FriendshipRequestNotification notification){
		this.notification = notification;
	}
	
	
	private String notiToXml(){
		return "<"+TAG+" "+ATTRIBUTE_ID+"=\""+notification.getId()+"\" "+
				ATTRIBUTE_TIME+"=\""+notification.getDate().getTime()+"\" "+ATTRIBUTE_READ+"\""+notification.isRead()+"\" "+
				ATTRIBUTE_USERNAME+"=\""+notification.getUsername()+"\" "+
				ATTRIBUTE_DISPLAYNAME+"=\""+notification.getDisplayName()+"\" "+
				ATTRIBUTE_ACCEPTED+"=\""+notification.isAccepted()+"\" />";
	}
	
	@Override
	public String toXml() {
		return openingXml()+notiToXml()+closingXml();
	}


	public FriendshipRequestNotification getNotification() {
		return notification;
	}
	
	

}
