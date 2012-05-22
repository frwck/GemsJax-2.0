package org.gemsjax.shared.communication.message.experiment;

import java.util.Date;
import java.util.Set;

import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.communication.serialisation.Serializable;

public class ExperimentGroupDTO implements Serializable{
	
	private int id;
	private int metaModelId;
	private int modelId;
	
	private String name;
	private Date startDate;
	private Date endDate;
	
	private Set<ExperimentInvitationDTO> notParicipatedEmails;

	
	public ExperimentGroupDTO(){}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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


	public Set<ExperimentInvitationDTO> getNotParicipatedEmails() {
		return notParicipatedEmails;
	}


	public void setNotParicipatedEmails(Set<ExperimentInvitationDTO> notParicipatedEmails) {
		this.notParicipatedEmails = notParicipatedEmails;
	}


	@Override
	public void serialize(Archive a) throws Exception {
		id = a.serialize("id", id).value;
		metaModelId = a.serialize("metaModelId", metaModelId).value;
		modelId = a.serialize("modelId", modelId).value;
		name = a.serialize("name", name).value;
		startDate = a.serialize("startDate", startDate).value;
		endDate = a.serialize("endDate", endDate).value;
		notParicipatedEmails = a.serialize("notParicipatedEmails", notParicipatedEmails).value;
	}
	

}
