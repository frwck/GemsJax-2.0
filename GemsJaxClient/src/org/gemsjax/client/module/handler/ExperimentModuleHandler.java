package org.gemsjax.client.module.handler;

import java.util.Set;

import org.gemsjax.shared.communication.message.experiment.ExperimentDTO;
import org.gemsjax.shared.communication.message.experiment.ExperimentError;

public interface ExperimentModuleHandler {
	
	public void onCreateNewSuccessful();
	public void onCreateNewFailed(ExperimentError error);
	
	public void onGetAllSuccessful(Set<ExperimentDTO> experiments);
	public void onGetAllFailed(ExperimentError error);
	
}
