package org.gemsjax.server.module;

import java.util.Set;

import org.gemsjax.server.communication.channel.handler.CollaborateableFileChannelHandler;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableType;

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
	
	
	private CollaboratableFileModule(){
		
	}


	@Override
	public void onCreateNewCollaborateableFile(OnlineUser owner, String name,
			String keywords, CollaborateableType type, boolean _public,
			Set<Integer> admins, Set<Integer> collaborators) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onGetAllCollaborateableFiles(OnlineUser requester) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onUpdateCollaborateableFile(OnlineUser changeRequester,
			int collaborateableId, String name, String keywords,
			boolean _public, Set<Integer> addAdmins, Set<Integer> removeAdmins,
			Set<Integer> addCollaborators, Set<Integer> removeCollaborators) {
		// TODO Auto-generated method stub
		
	}

	
	
}
