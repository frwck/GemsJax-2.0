package org.gemsjax.shared.communication.message.notification;

import java.util.Date;

public class ExperimentRequestNotification extends Notification{

	private String username;
	private String displayName;
	private boolean accepted;
	private int experimentId;
	private String experimentName;
	
	public ExperimentRequestNotification(long id, Date date, boolean read, String displayName, String username, boolean accepted, int experimentId, String experimentName) {
		super(id, date, read);
		this.username = username;
		this.displayName = displayName;
		this.accepted = accepted;
		this.experimentName = experimentName;
		this.experimentId = experimentId;
	}

	public String getUsername() {
		return username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public String getExperimentName() {
		return experimentName;
	}
	
	
	public int getExperimentId()
	{
		return experimentId;
	}

}
