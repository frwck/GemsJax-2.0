package org.gemsjax.server.persistence.experiment;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.server.persistence.collaboration.command.CommandImpl;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.experiment.ExperimentInvitation;
import org.gemsjax.shared.user.ExperimentUser;

public class ExperimentGroupImpl implements ExperimentGroup{

	private Integer id;
	
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

	@Override
	public void setExperiment(Experiment e)
	{
		this.experiment = e;
	}
	
	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof ExperimentGroupImpl) ) return false;
		
		final ExperimentGroupImpl that = (ExperimentGroupImpl) other;
		
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
