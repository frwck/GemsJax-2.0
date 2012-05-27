package org.gemsjax.server.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.gemsjax.server.communication.MailSender;
import org.gemsjax.server.communication.channel.handler.ExperimentChannelHandler;
import org.gemsjax.server.persistence.dao.ExperimentDAO;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.AlreadyAssignedException;
import org.gemsjax.server.persistence.dao.exception.AlreadyExistsException;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateExperimentDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.server.persistence.experiment.ExperimentGroupImpl;
import org.gemsjax.shared.communication.message.experiment.CreateExperimentMessage;
import org.gemsjax.shared.communication.message.experiment.ExperimentDTO;
import org.gemsjax.shared.communication.message.experiment.ExperimentGroupDTO;
import org.gemsjax.shared.communication.message.experiment.ExperimentSuccessfulMessage;
import org.gemsjax.shared.communication.message.experiment.GetAllExperimentsAnswerMessage;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.experiment.ExperimentInvitation;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;

public class ExperimentModule implements ExperimentChannelHandler{

	private static ExperimentModule INSTANCE = new ExperimentModule();
	private ExperimentDAO dao;
	private UserDAO userDAO;
	
	
	private ExperimentModule(){
		dao = new HibernateExperimentDAO();
		userDAO = new HibernateUserDAO();
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
		
		
		try {
			Experiment experiment = dao.createExperiment(message.getName(), message.getDescription(), (RegisteredUser) user.getUser());
			
			
			if (!message.getGroups().isEmpty())
				dao.addExperimentGroups(experiment, message.getGroups());
			
			
			// send Email invitations
			for (ExperimentGroup g: experiment.getExperimentGroups())
				for (ExperimentInvitation i : g.getExperimentInvitations())
					try {
						MailSender.sendExperimentInvitation(i.getEmail(), i.getVerificationCode());
					} catch (MessagingException e) {
						// TODO inform about problems to deliver email
						e.printStackTrace();
					}
			
			
			Set<User> admins = userDAO.getUserByIds(message.getAdminIds());
			
			if (admins != null)
			for (User u : admins){
				try {
					RequestModule.getInstance().createAdministrateExperimentRequest((RegisteredUser)user.getUser(), (RegisteredUser)u, experiment);
				} catch (AlreadyExistsException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (AlreadyAssignedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			try {
				user.getExperimentChannel().send(new ExperimentSuccessfulMessage(message.getReferenceId()));
			} catch (IOException e1) {
				// What to do if message cant be sent
				e1.printStackTrace();
			}
			
		} catch (ArgumentException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public void addExperimentAdmin(Experiment experiment, RegisteredUser admin) throws AlreadyAssignedException, DAOException{
		
		dao.assignExperimentAdministrator(experiment, admin);
		
	}

}
