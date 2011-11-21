package org.gemsjax.server.persistence.experiment;

import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.experiment.ExperimentInvitation;

public class ExperimentInvitationImpl implements ExperimentInvitation {

	private int id;
	
	private String email;
	private ExperimentGroup experimentGroup;
	private String verificationCode;
	private boolean participated;
	
	
	public  ExperimentInvitationImpl() {
	
		participated = false;
	}
	
	
	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public ExperimentGroup getExperimentGroup() {
		return experimentGroup;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getVerificationCode() {
		return verificationCode;
	}

	@Override
	public boolean hasParticipated() {
		return participated;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public void setExperimentGroup(ExperimentGroup group) {
		this.experimentGroup = group;
	}

	public void setVerificationCode(String code)
	{
		this.verificationCode = code;
	}
	
}