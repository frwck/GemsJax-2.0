package org.gemsjax.client.experiment;

import java.util.Date;
import java.util.Set;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.system.ExperimentLoginSuccessfulMessage;
import org.gemsjax.shared.user.User;
import org.gemsjax.shared.user.UserOnlineState;

public class ExperimentUserImpl implements User{

	private String experiementName;
	private String experimetnGroupName;
	private String experimentDescription;
	private Date startDate;
	private Date endDate;
	private String userDisplayName;
	private int userId;
	private int metaModelId;
	private int modelId;
	
	
	public ExperimentUserImpl(ExperimentLoginSuccessfulMessage m){
		experiementName = m.getExperiementName();
		experimetnGroupName = m.getExperimetnGroupName();
		experimentDescription = m.getExperimentDescription();
		startDate = m.getStartDate();
		endDate = m.getEndDate();
		userDisplayName = m.getUserDisplayName();
		userId = m.getUserId();
		metaModelId = m.getMetaModelId();
		modelId = m.getModelId();
	}
	
	@Override
	public Integer getId() {
		return userId;
	}

	@Override
	public String getDisplayedName() {
		return userDisplayName;
	}

	@Override
	public void setDisplayedName(String newName) {
		userDisplayName = newName;
	}

	@Override
	public Set<Collaborateable> getCollaborateables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserOnlineState getOnlineState() {
		// TODO Auto-generated method stubname
		return null;
	}

	@Override
	public void setOnlineState(UserOnlineState onlineState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProfilePicture(String pictureUrl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProfilePicture() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getExperiementName() {
		return experiementName;
	}

	public String getExperimetnGroupName() {
		return experimetnGroupName;
	}

	public String getExperimentDescription() {
		return experimentDescription;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public int getMetaModelId() {
		return metaModelId;
	}

	public int getModelId() {
		return modelId;
	}

}
