package org.gemsjax.client.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.CollaborateableFileChannel;
import org.gemsjax.client.communication.channel.handler.CollaborateableFileChannelHandler;
import org.gemsjax.client.module.handler.CollaborateableFileModuleHandler;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileError;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableType;
import org.gemsjax.shared.communication.message.collaborateablefile.GetAllCollaborateablesMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.NewCollaborateableFileMessage;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;

/**
 * This module is used to create a new {@link MetaModel}.
 * @author Hannes Dorfmann
 *
 */
public class CollaborateableFileModule<T extends Collaborateable> implements CollaborateableFileChannelHandler<T>{

	private CollaborateableFileChannel channel;
	
	private T instanceHelper;
	
	private int lastRefIdCounter =0;
	
	private String lastGetAllReferenceId;
	private Set<String> pendingCreateReferenceIds;
	
	private Set<CollaborateableFileModuleHandler<T>> handlers;

	public CollaborateableFileModule(CollaborateableFileChannel channel){
		handlers = new LinkedHashSet<CollaborateableFileModuleHandler<T>>();
		
		this.channel = channel;
		pendingCreateReferenceIds = new LinkedHashSet<String>();
		channel.addCollaborateableChannelHandler(this);
	}
	
	public void addCollaborateableFileHandler(CollaborateableFileModuleHandler<T> h)
	{
		handlers.add(h);
	}
	
	
	public void removeCollaborateableFileHandler(CollaborateableFileModuleHandler<T> h)
	{
		handlers.remove(h);
	}
	
	
	public void createNew(String name, String description, Collaborateable.Permission permission, Set<Friend> collaborators) throws IOException{
		
		Set<Integer> collaboratorIds = new LinkedHashSet<Integer>();
		
		for (Friend f: collaborators)
			collaboratorIds.add(f.getId());
		
		String referenceId = generateNextReferenceId();
		if (instanceHelper instanceof MetaModel){
			channel.send(new NewCollaborateableFileMessage(referenceId, name, CollaborateableType.METAMODEL, collaboratorIds, permission, description));
		}
		else
		if (instanceHelper instanceof Model){
			channel.send(new NewCollaborateableFileMessage(referenceId, name, CollaborateableType.MODEL, collaboratorIds, permission, description));
		}
		
		pendingCreateReferenceIds.add(referenceId);
	}
	
	private String generateNextReferenceId(){
		lastRefIdCounter++;
		return "cc"+lastRefIdCounter;
	}
	
	public void getAllMetaModels() throws IOException{
	
		lastGetAllReferenceId = generateNextReferenceId();
		
		channel.send(new GetAllCollaborateablesMessage(lastGetAllReferenceId, CollaborateableType.METAMODEL));
		
	}
	
	

	@Override
	public void onError(String referrenceId, CollaborateableFileError error) {
		if (pendingCreateReferenceIds.contains(referrenceId)){
			
			for (CollaborateableFileModuleHandler<T> h: handlers)
				h.onNewCreateError(error);
			
			pendingCreateReferenceIds.remove(referrenceId);
		}
		else
		if (lastGetAllReferenceId.equals(referrenceId)){
			
			for (CollaborateableFileModuleHandler<T> h: handlers)
				h.onGetAllError(error);
			
		}
	}

	@Override
	public void onSuccessful(String referenceId) {
		if (pendingCreateReferenceIds.contains(referenceId)){
			for (CollaborateableFileModuleHandler<T> h: handlers)
				h.onNewCreateSuccessful();
			pendingCreateReferenceIds.remove(referenceId);
		}
	}

	@Override
	public void onGetAllResultReceived(String referenceId,
			Set<T> collaborateables) {
		// Only deliver the latest result to the overlaying layer
		
		if (lastGetAllReferenceId.equals(referenceId))
			for (CollaborateableFileModuleHandler<T> h: handlers)
				h.onGetAllSuccessful(collaborateables);
		
	}
	
	
}
