package org.gemsjax.server.persistence.dao.exception;

import org.gemsjax.shared.experiment.ExperimentInvitation;
import org.gemsjax.shared.user.ExperimentUser;

/**
 * This kind of {@link Exception} is used to inform, that a certain {@link ExperimentInvitation}
 * has already been used to create a new {@link ExperimentUser}
 * @author Hannes Dorfmann
 *
 */
public class InvitationAlreadyUsedException extends Exception {
	
	

}
