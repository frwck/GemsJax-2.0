package org.gemsjax.shared.experiment;

import java.util.Date;
import java.util.Set;

import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;
import org.gemsjax.shared.user.ExperimentUser;

public interface ExperimentGroup {
	
	public int getId();

	public Set<ExperimentUser> getParticipants();
	public Set<ExperimentInvitation> getExperimentInvitations();
	
	public Date getStartDate();
	public Date getEndDate();
	
	public void setStartDate(Date d);
	public void setEndDate(Date d);
	
	public Experiment getExperiment();
	public void setExperiment(Experiment e);
	
	public String getName();
	public void setName(String name);
	
	
	public void setMetaModel(MetaModel mm);
	public MetaModel getMetaModel();
	
	public void setModel(Model model);
	public Model getModel();
}
