package org.gemsjax.shared.communication.message.notification;
/**
 * Sent from Server to Client as a positive response on a {@link NotificationAsReadMessage} or {@link DeleteNotificationMessage}
 * @author Hannes Dorfmann
 *
 */
public class SuccessfulNotificationMessage extends ReferenceableNotificationMessage{

	public static final String TAG ="ok";
	
	public SuccessfulNotificationMessage(String referenceId) {
		super(referenceId);
	}

	@Override
	public String toXml() {
		return openingXml()+"<"+TAG+" />"+closingXml();
	}

	
	
}
