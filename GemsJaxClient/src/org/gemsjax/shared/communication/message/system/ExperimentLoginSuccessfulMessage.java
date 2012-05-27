package org.gemsjax.shared.communication.message.system;

import java.util.Date;

import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.communication.serialisation.Serializable;

public class ExperimentLoginSuccessfulMessage extends SystemMessage implements Serializable{
	
	private String experiementName;
	private String experimetnGroupName;
	private String experimentDescription;
	private Date startDate;
	private Date endDate;
	private String userDisplayName;
	private int userId;
	private int metaModelId;
	private int modelId;
	
	
	public ExperimentLoginSuccessfulMessage(){}
	
	
	public ExperimentLoginSuccessfulMessage(String experiementName,
			String experimetnGroupName, String experimentDescription,
			Date startDate, Date endDate, String userDisplayName) {
		super();
		this.experiementName = experiementName;
		this.experimetnGroupName = experimetnGroupName;
		this.experimentDescription = experimentDescription;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userDisplayName = userDisplayName;
	}
	
	
	@Override
	public String toXml() {
		return null;
	}

	@Override
	public void serialize(Archive a) throws Exception {
		experiementName = a.serialize("experiementName", experiementName).value;
		experimetnGroupName = a.serialize("experimetnGroupName", experimetnGroupName).value;
		experimentDescription = a.serialize("experimentDescription", experimentDescription).value;
		startDate = a.serialize("startDate", startDate).value;
		endDate = a.serialize("endDate", endDate).value;
		userDisplayName = a.serialize("displayName", userDisplayName).value;
		metaModelId = a.serialize("metaModelId", metaModelId).value;
		modelId = a.serialize("modelId", modelId).value;
		userId = a.serialize("userId", userId).value;
	}


	public String getExperiementName() {
		return experiementName;
	}


	public void setExperiementName(String experiementName) {
		this.experiementName = experiementName;
	}


	public String getExperimetnGroupName() {
		return experimetnGroupName;
	}


	public void setExperimetnGroupName(String experimetnGroupName) {
		this.experimetnGroupName = experimetnGroupName;
	}


	public String getExperimentDescription() {
		return experimentDescription;
	}


	public void setExperimentDescription(String experimentDescription) {
		this.experimentDescription = experimentDescription;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getUserDisplayName() {
		return userDisplayName;
	}


	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public int getMetaModelId() {
		return metaModelId;
	}


	public void setMetaModelId(int metaModelId) {
		this.metaModelId = metaModelId;
	}


	public int getModelId() {
		return modelId;
	}


	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	

}
