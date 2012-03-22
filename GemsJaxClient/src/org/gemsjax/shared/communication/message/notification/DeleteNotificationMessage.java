package org.gemsjax.shared.communication.message.notification;
/**
 * Sent from Client to server, to mark a notification as read
 * @author Hannes Dorfmann
 *
 */
public class DeleteNotificationMessage extends ReferenceableNotificationMessage{
	
	public static final String TAG ="del";
	public static final String ATTRIBUTE_NOTIFICATION_ID="id";
	
	private long notificationId;
	
	public DeleteNotificationMessage(String referenceId, long notificiationId){
		super(referenceId);
		this.notificationId = notificiationId;
	}

	@Override
	public String toXml() {
		return openingXml()+"<"+TAG+" "+ATTRIBUTE_NOTIFICATION_ID+"=\""+notificationId+"\" />"+closingXml();
	}

	public long getNotificationId(){
		return notificationId;
	}
}
