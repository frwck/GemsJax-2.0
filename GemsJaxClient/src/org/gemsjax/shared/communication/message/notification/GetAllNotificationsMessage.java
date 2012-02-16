package org.gemsjax.shared.communication.message.notification;
/**
 * Sent from Client to server to get all of the users notifications
 * @author Hannes Dorfmann
 *
 */
public class GetAllNotificationsMessage extends ReferenceableNotificationMessage{
	
	
	public static final String TAG="get-all";

	public GetAllNotificationsMessage(String referenceId) {
		super(referenceId);
		
	}

	@Override
	public String toXml() {
		return super.openingXml()+"<"+TAG+"/>"+super.closingXml();
	}
	

}
