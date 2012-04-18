package org.gemsjax.server.communication.channel.handler;

import java.util.Set;

import org.gemsjax.server.module.OnlineUser;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableType;

public interface CollaborateableFileChannelHandler {

	public abstract void onCreateNewCollaborateableFile(String referenceId, OnlineUser owner, String name, String keywords,  CollaborateableType type, Collaborateable.Permission permission,  Set<Integer> collaborators);
	/**
	 * Update a {@link Collaborateable} to the following parameters. If a parameter is null, the old parameter value should not be overridden.
	 * @param changeRequester
	 * @param collaborateableId
	 * @param name
	 * @param keywords
	 * @param type
	 * @param _public
	 * @param admins
	 * @param collaborators
	 */
	public abstract void onUpdateCollaborateableFile(String referenceId, OnlineUser changeRequester, int collaborateableId, String name, String keywords, Collaborateable.Permission permission, Set<Integer> addCollaborators, Set<Integer> removeCollaborators);
	
	/**
	 * Get all collaborateableFiles, of a certain user
	 * @param requester
	 */
	public abstract void onGetAllCollaborateableFiles(String referenceId, CollaborateableType type, OnlineUser requester);
	
}
