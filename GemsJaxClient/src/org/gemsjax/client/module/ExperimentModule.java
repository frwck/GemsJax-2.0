package org.gemsjax.client.module;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.gemsjax.client.communication.channel.ExperimentChannel;
import org.gemsjax.client.communication.channel.handler.ExperimentChannelHandler;
import org.gemsjax.client.module.handler.ExperimentModuleHandler;
import org.gemsjax.client.util.Console;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.experiment.CreateExperimentMessage;
import org.gemsjax.shared.communication.message.experiment.ExperimentDTO;
import org.gemsjax.shared.communication.message.experiment.ExperimentError;
import org.gemsjax.shared.communication.message.experiment.ExperimentGroupDTO;
import org.gemsjax.shared.communication.message.experiment.GetAllExperimentsMessage;
import org.gemsjax.shared.communication.message.friend.Friend;

public class ExperimentModule implements ExperimentChannelHandler{

	private ExperimentChannel channel;
	private Set<ExperimentModuleHandler> handlers;
	private static int refIdCounter = 0;
	
	private Map<String, Message> referenceIdsMap;
	private String lastGetAllRefId;
	
	public ExperimentModule(ExperimentChannel channel){
		this.channel = channel;
		channel.addExperilentChannelHandler(this);
		handlers = new LinkedHashSet<ExperimentModuleHandler>();
		this.referenceIdsMap = new LinkedHashMap<String, Message>();
	}
	
	private String nextId(){
		refIdCounter++;
		return "exp"+refIdCounter;
	}
	
	public void addExperimentModuleHandler(ExperimentModuleHandler h){
		handlers.add(h);
	}
	
	
	public void removeExperimentModuleHandler(ExperimentModuleHandler h){
		handlers.remove(h);
	}
	
	
	
	public void createExperiment(String name, String descriptions, Set<ExperimentGroupDTO> groups, Set<Friend> admins) throws IOException{
		
		Set<Integer> ids = new LinkedHashSet<Integer>();
		for (Friend f: admins)
			ids.add(f.getId());
		
		String id = nextId();
		CreateExperimentMessage m = new CreateExperimentMessage(id, name, descriptions, groups, ids);
		this.referenceIdsMap.put(id, m);
		channel.send(m);
	}

	@Override
	public void onSuccessfulMessage(String referenceId) {
		
		//TODO bugfix
		
//		Message m = this.referenceIdsMap.get(referenceId);
//		if (m == null)
//		{
//			Console.log("unknown reference id received");
//			return;
//		}
//		
//		if (m instanceof CreateExperimentMessage){
//		
			for (ExperimentModuleHandler h : handlers)
				h.onCreateNewSuccessful();
//			
//			this.referenceIdsMap.remove(referenceId);
//		}
//		
	}
	
	
	public void getAllExperiments() throws IOException{
		
		lastGetAllRefId = nextId();
		GetAllExperimentsMessage m = new GetAllExperimentsMessage(lastGetAllRefId);
		channel.send(m);
		
	}
	
	

	@Override
	public void onErrorMessage(String referenceId, ExperimentError error) {
		
		
		if (lastGetAllRefId.equals(referenceId))
		{
			for (ExperimentModuleHandler h : handlers)
				h.onGetAllFailed(error);
		}
		
		
		Message m = referenceIdsMap.get(referenceId);
		if (m == null)
		{
			Console.log("unknown reference id received");
			return;
		}
		
		if (m instanceof CreateExperimentMessage){
			for (ExperimentModuleHandler h : handlers)
				h.onCreateNewFailed(error);
			referenceIdsMap.remove(referenceId);
		}
		
	}

	@Override
	public void onGetAllAnswer(String refId, Set<ExperimentDTO> experiments) {
		
		if (lastGetAllRefId.equals(refId))
		{
			for (ExperimentModuleHandler h : handlers)
				h.onGetAllSuccessful(experiments);
		}
		
	}
	
	
	
	
}
