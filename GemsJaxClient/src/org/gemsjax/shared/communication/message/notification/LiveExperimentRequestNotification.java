package org.gemsjax.shared.communication.message.notification;

/**
 * Pushed from server to client
 * @author Hannes Dorfmann
 *
 */
public class LiveExperimentRequestNotification extends LiveNotificationMessage{

	
	public static final String TAG="quick-exp";
	
	public static final String ATTRIBUTE_ID="id";
	public static final String ATTRIBUTE_TIME="time";
	public static final String ATTRIBUTE_READ="read";
	public static final String ATTRIBUTE_EXPERIMENT_USERNAME="username";
	public static final String ATTRIBUTE_EXPERIMENT_DISPLAYNAME="dispName";
	public static final String ATTRIBUTE_EXPERIMENT_ID="exp-id";
	public static final String ATTRIBUTE_EXPERIMENT_NAME="exp-name";
	public static final String ATTRIBUTE_EXPERIMENT_ACCEPTED="accepted";
	
	
	private ExperimentRequestNotification notification;
	
	
	public LiveExperimentRequestNotification(ExperimentRequestNotification notification)
	{
		this.notification = notification;
	}
	
	private String expToXml(){
		return "<"+TAG+" "+ATTRIBUTE_ID+"=\""+notification.getId()+"\" "+
				ATTRIBUTE_TIME+"=\""+notification.getDate().getTime()+"\" "+ATTRIBUTE_READ+"\""+notification.isRead()+"\" "+
				ATTRIBUTE_EXPERIMENT_USERNAME+"=\""+notification.getUsername()+"\" "+
				ATTRIBUTE_EXPERIMENT_DISPLAYNAME+"=\""+notification.getDisplayName()+"\" "+
				ATTRIBUTE_EXPERIMENT_ACCEPTED+"=\""+notification.isAccepted()+"\" "+
				ATTRIBUTE_EXPERIMENT_ID+"=\""+notification.getExperimentId()+"\" "+
				ATTRIBUTE_EXPERIMENT_NAME+"=\""+notification.getExperimentName()+"\" />";
	}
	
	@Override
	public String toXml() {
		return openingXml()+expToXml()+closingXml();
	}

}
