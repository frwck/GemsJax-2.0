package org.gemsjax.shared.communication.message.request;

import java.util.Date;

public class AdministrateExperimentRequest extends Request{
	
	private int experimentId;
	private String experimentName;
	
	public AdministrateExperimentRequest(int id, String requesterDisplayName,
			String requesterUsername, Date date, int experimentId, String experimentName) {
		super(id, requesterDisplayName, requesterUsername, date);
		
		this.experimentId = experimentId;
		this.experimentName = experimentName;
	}

	public int getExperimentId() {
		return experimentId;
	}

	public String getExperimentName() {
		return experimentName;
	}

}
