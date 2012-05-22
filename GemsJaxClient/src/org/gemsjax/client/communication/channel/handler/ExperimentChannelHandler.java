package org.gemsjax.client.communication.channel.handler;

import java.util.Set;

import org.gemsjax.shared.communication.message.experiment.ExperimentDTO;
import org.gemsjax.shared.communication.message.experiment.ExperimentError;

public interface ExperimentChannelHandler {
	
	public void onSuccessfulMessage(String referenceId);
	public void onErrorMessage(String referenceId, ExperimentError error);
	public void onGetAllAnswer(String refId, Set<ExperimentDTO> experiments);
	
}
