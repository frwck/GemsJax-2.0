package org.gemsjax.server.persistence.request;

import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.request.AdministrateExperimentRequest;
import org.gemsjax.shared.request.Request;
import org.gemsjax.shared.user.RegisteredUser;


/**
 * This kind of {@link Request} is used to let another {@link RegisteredUser} administrate a {@link Experiment}
 * @author Hannes Dorfmann
 *
 */
public class AdministrateExperimentRequestImpl extends RequestImpl implements AdministrateExperimentRequest{

	
	private Experiment experiment;
	
	@Override
	public Experiment getExperiment() {
		return experiment;
	}
	
	@Override
	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}
}
