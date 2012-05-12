package org.gemsjax.server.module;

import java.io.IOException;
import java.util.Set;

import org.gemsjax.server.communication.channel.handler.CollaborateableFileChannelHandler;
import org.gemsjax.server.persistence.dao.CollaborateableDAO;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.AlreadyAssignedException;
import org.gemsjax.server.persistence.dao.exception.AlreadyExistsException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateCollaborateableDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileError;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileErrorMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileSuccessfulMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableType;
import org.gemsjax.shared.communication.message.collaborateablefile.GetAllCollaborateablesAnswerMessage;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;

/**
 * This module is responsible for collaborateable file management. That means, that this module is responsible to create, delete
 * or query a list of {@link Collaborateable}s. In addition you can manipulate the {@link Collaborateable}s meta data like name, keywords, co-admins and collaborators.
 * <b>NOTE:</b> This module can not manipulate the content of a {@link Collaborateable} file.
 * 
 * @author Hannes Dorfmann
 *
 */
public class CollaboratableFileModule implements CollaborateableFileChannelHandler {

	private static CollaboratableFileModule INSTANCE = new CollaboratableFileModule();
	
	private CollaborateableDAO dao;
	private UserDAO userDAO;
	
	
	private CollaboratableFileModule(){
		dao = new HibernateCollaborateableDAO();
		userDAO = new HibernateUserDAO();
	}

	
	public static CollaboratableFileModule getInstance(){
		return INSTANCE;
	}
	

	@Override
	public void onCreateNewCollaborateableFile(String referenceId, OnlineUser owner, String name,
			String keywords, CollaborateableType type, Collaborateable.Permission permission,
			Set<Integer> collaborators) {
		
		Set<User> collUsers = null;
		
		if (collaborators!=null && !collaborators.isEmpty())
			collUsers = userDAO.getUserByIds(collaborators);
		
		try {
			Collaborateable collaborateable = dao.createCollaborateable((RegisteredUser)owner.getUser(), name, keywords, type, permission, null);
			
			owner.getCollaborateableFileChannel().send(new CollaborateableFileSuccessfulMessage(referenceId));
			
			for (User u : collUsers){
				// Make a request
				try {
					RequestModule.getInstance().createCollaborationRequest((RegisteredUser)owner.getUser(), (RegisteredUser)u, collaborateable);
				} catch (AlreadyExistsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AlreadyAssignedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (DAOException e) {
			
			try {
				owner.getCollaborateableFileChannel().send(new CollaborateableFileErrorMessage(referenceId, CollaborateableFileError.DATABASE));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO What to do if message can not be sent
			e.printStackTrace();
		}
		
		
	}


	


	@Override// TODO Auto-generated method stub
	
	public void onGetAllCollaborateableFiles(String referenceId, CollaborateableType type, OnlineUser requester) {
		
		
		RegisteredUser user = (RegisteredUser) requester.getUser();
		
		if (type == CollaborateableType.METAMODEL){
			
			Set<Collaborateable> metaModels = dao.getMetaModelsOf(user);
			
			try {
				requester.getStandardOutputChannel().send(new GetAllCollaborateablesAnswerMessage(referenceId, type, metaModels));
			} catch (IOException e) {
				// What to do if message can not be send
				e.printStackTrace();
			}
			
			
		}
		else
		if (type == CollaborateableType.MODEL){
			
			Set<Collaborateable> models = dao.getModelsOf(user);
			
			try {
				requester.getStandardOutputChannel().send(new GetAllCollaborateablesAnswerMessage(referenceId, type, models));
			} catch (IOException e) {
				// What to do if message can not be send
				e.printStackTrace();
			}
			
		}
		
		
		
	}


	@Override
	public void onUpdateCollaborateableFile(String referenceId, OnlineUser changeRequester,
			int collaborateableId, String name, String keywords,
			Collaborateable.Permission permission, 
			Set<Integer> addCollaborators, Set<Integer> removeCollaborators) {
		// TODO Not implemented yet
		
	}
	
	
	
	
	public void addCollaborator(User u, Collaborateable c) throws DAOException{
		dao.addCollaborativeUser(c, u);
	}

	
	
}
