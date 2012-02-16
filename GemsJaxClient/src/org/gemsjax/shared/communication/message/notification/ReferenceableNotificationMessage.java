package org.gemsjax.shared.communication.message.notification;

public abstract class ReferenceableNotificationMessage extends NotificationMessage {
	
	public static final String ATTRIBUTE_REFERENCE_ID="ref-id";
	
	private String referenceId;
	
	public ReferenceableNotificationMessage(String referenceId){
		this.referenceId = referenceId;
	}
	
	
	protected String openingXml(){
		return "<"+TAG+" "+ATTRIBUTE_REFERENCE_ID+"=\""+referenceId+"\">";
	}

	protected String closingXml(){
		return "</"+TAG+">";
	}

	public String getReferenceId() {
		return referenceId;
	}
	
	
}
