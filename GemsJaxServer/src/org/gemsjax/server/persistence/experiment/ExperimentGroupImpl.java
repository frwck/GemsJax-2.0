package org.gemsjax.server.persistence.experiment;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.experiment.ExperimentInvitation;
import org.gemsjax.shared.user.ExperimentUser;

public class ExperimentGroupImpl implements ExperimentGroup{

	private int id;
	
	private Date endDate;
	private Date startDate;
	
	private Set<ExperimentUser> participants;
	private Set<ExperimentInvitation> experimentInvitations;
	
	private Experiment experiment;
	
	private String name;
	
	
	public ExperimentGroupImpl()
	{
		participants = new LinkedHashSet<ExperimentUser>();
		experimentInvitations = new LinkedHashSet<ExperimentInvitation>();
	}
	
	
	
	@Override
	public Date getEndDate() {
		return endDate;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Set<ExperimentUser> getParticipants() {
		return participants;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	@Override
	public void setEndDate(Date arg0) {
		this.endDate = arg0;
	}

	@Override
	public void setStartDate(Date arg0) {
		this.startDate = arg0;
	}



	@Override
	public Set<ExperimentInvitation> getExperimentInvitations() {
		return experimentInvitations;
	}



	@Override
	public Experiment getExperiment() {
		return experiment;
	}



	@Override
	public String getName() {
		return name;
	}



	@Override
	public void setName(String name) {
		this.name = name;
	}

	
	public void setExperiment(Experiment e)
	{
		this.experiment = e;
	}
	
	
}
