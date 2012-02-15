package org.gemsjax.server.persistence.notification;

import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.user.RegisteredUser;

public class ExperimentRequestNotificationImpl extends NotificationImpl {
	
	
	private Experiment experiment;
	private RegisteredUser acceptor;
	private boolean accepted;
	
	
	public ExperimentRequestNotificationImpl()
	{
		
	}


	public Experiment getExperiment() {
		return experiment;
	}


	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}


	public RegisteredUser getAcceptor() {
		return acceptor;
	}


	public void setAcceptor(RegisteredUser acceptor) {
		this.acceptor = acceptor;
	}


	public boolean isAccepted() {
		return accepted;
	}


	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

}
