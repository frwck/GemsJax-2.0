package org.gemsjax.shared.user;

import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;

/**
 * This is a {@link User} that is used for {@link Experiment}s only
 * @author Hannes Dorfmann
 *
 */
public interface ExperimentUser extends User {

	public String getUsername();
	
	public ExperimentGroup getExperimentGroup();
	

	public void setDisplayedName(String displayedName);
	
}
