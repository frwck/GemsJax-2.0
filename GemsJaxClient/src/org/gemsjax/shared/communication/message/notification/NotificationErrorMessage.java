package org.gemsjax.shared.communication.message.notification;

public class NotificationErrorMessage extends ReferenceableNotificationMessage{

	public static final String TAG="error";
	public static final String ATTRIBUTE_REASON="reason";
	
	private NotificationError error;
	
	public NotificationErrorMessage(String referenceId, NotificationError error) {
		super(referenceId);
		this.error = error;
	}

	@Override
	public String toXml() {
		return openingXml()+"<"+TAG+" "+ATTRIBUTE_REASON+"=\""+error.toConstant()+"\" />"+closingXml();
	}
	
	public NotificationError getError(){
		return error;
	}

}
