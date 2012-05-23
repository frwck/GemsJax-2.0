package org.gemsjax.server.communication.channel.handler;

import org.gemsjax.server.module.OnlineUser;
import org.gemsjax.shared.communication.message.experiment.CreateExperimentMessage;

public interface ExperimentChannelHandler {

	public void onGetAllExperiments(String referenceId, OnlineUser user);
	public void onCreateExperiment(CreateExperimentMessage message, OnlineUser user);
	
	
}
