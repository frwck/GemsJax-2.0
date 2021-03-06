package org.gemsjax.server.persistence.experiment;

import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.experiment.ExperimentInvitation;

public class ExperimentInvitationImpl implements ExperimentInvitation {

	private Integer id;
	
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
	public void setParticipated(boolean participated)
	{
		this.participated = participated;
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
	
	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof ExperimentInvitationImpl) ) return false;
		
		final ExperimentInvitationImpl that = (ExperimentInvitationImpl) other;
		
		if (id != null && that.id != null)
			return this.id.equals(that.id);
		
		return false;
	}
		
	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		else
			return super.hashCode();
	}
	
}
