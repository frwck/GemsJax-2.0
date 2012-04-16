package org.gemsjax.server.module;

import java.io.IOException;
import java.util.Set;

import org.gemsjax.server.communication.channel.handler.CollaborateableFileChannelHandler;
import org.gemsjax.server.persistence.dao.CollaborateableDAO;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateCollaborateableDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileError;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileErrorMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileSuccessfulMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableType;
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
			Collaborateable collaborateable = dao.createCollaborateable((RegisteredUser)owner.getUser(), name, keywords, type, permission, collUsers);
			owner.getCollaborateableFileChannel().send(new CollaborateableFileSuccessfulMessage(referenceId));
		
		} catch (DAOException e) {
			
			try {
				owner.getCollaborateableFileChannel().send(new CollaborateableFileErrorMessage(referenceId, CollaborateableFileError.DATABASE));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO What to do if message can not be sent
			e.printStackTrace();
		}
		
		
	}


	


	@Override
	public void onGetAllCollaborateableFiles(String referenceId, OnlineUser requester) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onUpdateCollaborateableFile(String referenceId, OnlineUser changeRequester,
			int collaborateableId, String name, String keywords,
			Collaborateable.Permission permission, 
			Set<Integer> addCollaborators, Set<Integer> removeCollaborators) {
		// TODO Auto-generated method stub
		
	}

	
	
}
