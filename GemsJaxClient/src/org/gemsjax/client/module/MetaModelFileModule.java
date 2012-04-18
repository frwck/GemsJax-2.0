package org.gemsjax.client.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.CreateMetaModelChannel;
import org.gemsjax.client.communication.channel.handler.CreateCollaborateableChannelHandler;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileError;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableType;
import org.gemsjax.shared.communication.message.collaborateablefile.NewCollaborateableFileMessage;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.metamodel.MetaModel;

/**
 * This module is used to create a new {@link MetaModel}.
 * @author Hannes Dorfmann
 *
 */
public class MetaModelFileModule implements CreateCollaborateableChannelHandler{

	private CreateMetaModelChannel channel;
	
	private int lastRefIdCounter =0;
	
	public MetaModelFileModule(CreateMetaModelChannel c){
		channel = c;
	}
	
	public void createNewMetaModel(String name, String description, Collaborateable.Permission permission, Set<Friend> collaborators) throws IOException{
		
		Set<Integer> collaboratorIds = new LinkedHashSet<Integer>();
		
		for (Friend f: collaborators)
			collaboratorIds.add(f.getId());
		
		String referenceId = generateNextReferenceId();
		channel.send(new NewCollaborateableFileMessage(referenceId, name, CollaborateableType.METAMODEL, collaboratorIds, permission, description));
	
	}
	
	private String generateNextReferenceId(){
		lastRefIdCounter++;
		return "cc"+lastRefIdCounter;
	}

	@Override
	public void onError(String referrenceId, CollaborateableFileError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccessful(String referenceId) {
		// TODO Auto-generated method stub
		
	}
	
	
}
