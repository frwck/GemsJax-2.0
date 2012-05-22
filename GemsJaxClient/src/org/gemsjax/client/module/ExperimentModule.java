package org.gemsjax.client.module;

import java.io.IOException;
import java.util.Set;

import org.gemsjax.client.communication.channel.ExperimentChannel;
import org.gemsjax.shared.communication.message.experiment.CreateExperimentMessage;
import org.gemsjax.shared.communication.message.experiment.ExperimentGroupDTO;

public class ExperimentModule {

	private ExperimentChannel channel;
	private static int refIdCounter = 0;
	
	public ExperimentModule(ExperimentChannel channel){
		this.channel = channel;
	}
	
	private String nextId(){
		refIdCounter++;
		return "exp"+refIdCounter;
	}
	
	
	
	public void createExperiment(String name, String descriptions, Set<ExperimentGroupDTO> groups, Set<Integer> adminIds) throws IOException{
		CreateExperimentMessage m = new CreateExperimentMessage(nextId(), name, descriptions, groups, adminIds);
		channel.send(m);
	}
	
	
}
