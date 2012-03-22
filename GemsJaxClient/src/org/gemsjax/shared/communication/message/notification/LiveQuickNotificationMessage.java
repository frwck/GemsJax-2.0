package org.gemsjax.shared.communication.message.notification;

/**
 * Pushed from server to the client
 * @author Hannes Dorfmann
 *
 */
public class LiveQuickNotificationMessage extends LiveNotificationMessage{

	public static final String TAG="live-quick";
	
	public static final String ATTRIBUTE_ID="id";
	public static final String ATTRIBUTE_TIME="time";
	public static final String ATTRIBUTE_READ="read";
	public static final String ATTRIBUTE_TYPE="type";
	public static final String ATTRIBUTE_OPTIONAL="opt";
	
			
	private QuickNotification notification;
	
	
	public LiveQuickNotificationMessage(QuickNotification notification){
		this.notification = notification;
	}
	
	
	private String notiToXml(){
		return "<"+TAG+" "+ATTRIBUTE_ID+"=\""+notification.getId()+"\" "+
				ATTRIBUTE_TIME+"=\""+notification.getDate().getTime()+"\" "+ATTRIBUTE_READ+"=\""+notification.isRead()+"\" "+
				ATTRIBUTE_TYPE+"=\""+notification.getType().toConstant()+"\" "+
				ATTRIBUTE_OPTIONAL+"=\""+notification.getOptionalMessage()+"\" />";
	}
	
	@Override
	public String toXml() {
		return openingXml()+notiToXml()+closingXml();
	}


	public QuickNotification getNotification() {
		return notification;
	}
	
	

}
