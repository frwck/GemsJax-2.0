package org.gemsjax.server.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gemsjax.server.communication.channel.handler.ExperimentChannelHandler;
import org.gemsjax.server.persistence.dao.ExperimentDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateExperimentDAO;
import org.gemsjax.shared.communication.message.experiment.CreateExperimentMessage;
import org.gemsjax.shared.communication.message.experiment.ExperimentDTO;
import org.gemsjax.shared.communication.message.experiment.GetAllExperimentsAnswerMessage;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.user.RegisteredUser;

public class ExperimentModule implements ExperimentChannelHandler{

	private static ExperimentModule INSTANCE = new ExperimentModule();
	private ExperimentDAO dao;
	
	
	private ExperimentModule(){
		dao = new HibernateExperimentDAO();
	}
	
	
	public static ExperimentModule getInstance(){
		return INSTANCE;
	}
	
	
	
	
	@Override
	public void onGetAllExperiments(String referenceId, OnlineUser user) {
		
		List<Experiment> exp = dao.getExperimentByOwner((RegisteredUser) user.getUser());
		
		Set<ExperimentDTO> experiments = new LinkedHashSet<ExperimentDTO>();
		
		for (Experiment e : exp){
			ExperimentDTO dto = new ExperimentDTO();
			dto.setId(e.getId());
			dto.setName(e.getName());
			dto.setDesription(e.getDescription());
			experiments.add(dto);
		}
		
		
		try {
			user.getExperimentChannel().send(new GetAllExperimentsAnswerMessage(referenceId, experiments));
		} catch (IOException e1) {
			// TODO what to do if message cant be sent
			e1.printStackTrace();
		}
		
	}

	@Override
	public void onCreateExperiment(CreateExperimentMessage message,
			OnlineUser user) {
		// TODO Auto-generated method stub
		
	}

}
