package org.gemsjax.shared.request;

import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.user.RegisteredUser;

/**
 * This {@link Request} is used to invite another {@link RegisteredUser} to administrate an {@link Experiment}
 * @author Hannes Dorfmann
 *
 */
public interface AdministrateExperimentRequest extends Request {

	public Experiment getExperiment();
	
	public void setExperiment(Experiment experiment);
}
