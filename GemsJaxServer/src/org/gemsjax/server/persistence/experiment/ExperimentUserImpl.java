package org.gemsjax.server.persistence.experiment;

import org.gemsjax.server.persistence.user.UserImpl;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.user.ExperimentUser;

public class ExperimentUserImpl extends UserImpl implements ExperimentUser {

	// TODO change to validation number
	private String username;
	private ExperimentGroup experimentGroup;
	private String passwordHash;
	
	
	public ExperimentUserImpl()
	{
		
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}


	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	
	
	@Override
	public ExperimentGroup getExperimentGroup() {
		return experimentGroup;
	}
	
	/**
	 * <b>For Hibernate only</b><br />Should only called one time to set the {@link ExperimentGroup} and to store it 
	 * via Hibernate in the database
	 * @param group
	 */
	public void setExperimentGroup(ExperimentGroup group)
	{
		this.experimentGroup = group;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	/**
	 * <b>For Hibernate only</b><br />Should only called one time to set the username and to store it 
	 * via Hibernate in the database
	 * @param group
	 */
	public void setUsername (String username)
	{
		this.username = username;
	}

}
